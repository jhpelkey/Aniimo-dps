package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Aniimo
import com.example.data.model.CapabilityPerk
import com.example.data.model.DiscussionCategory
import com.example.data.model.PointInvestmentStep
import com.example.data.model.TeamMemberBuild
import com.example.data.model.TeamMode
import com.example.data.model.TopRatedTeam

/**
 * Helper object for encoding, decoding, copying, and system-sharing 4-Aniimo builds.
 */
object BuildShareCodec {

    data class DecodedBuildPayload(
        val mainId: String,
        val mainItem: String,
        val partnerId: String,
        val partnerItem: String,
        val slot3Id: String,
        val slot3Item: String,
        val slot4Id: String,
        val slot4Item: String,
        val atk: Int,
        val breakStat: Int,
        val regen: Int,
        val hp: Int,
        val pdef: Int,
        val mdef: Int
    )

    fun encodeBuildCode(
        mainAniimo: Aniimo,
        mainItem: String,
        partnerAniimo: Aniimo,
        partnerItem: String,
        slot3Aniimo: Aniimo,
        slot3Item: String,
        slot4Aniimo: Aniimo,
        slot4Item: String,
        atk: Int,
        breakStat: Int,
        regen: Int,
        hp: Int,
        pdef: Int,
        mdef: Int
    ): String {
        return "ANIIMO-V4|" +
            "${mainAniimo.id}:$mainItem|" +
            "${partnerAniimo.id}:$partnerItem|" +
            "${slot3Aniimo.id}:$slot3Item|" +
            "${slot4Aniimo.id}:$slot4Item|" +
            "ATK=$atk,BREAK=$breakStat,REGEN=$regen,HP=$hp,PDEF=$pdef,MDEF=$mdef"
    }

    fun formatShareableSummary(
        buildTitle: String,
        mode: TeamMode,
        mainAniimo: Aniimo,
        mainItem: String,
        partnerAniimo: Aniimo,
        partnerItem: String,
        slot3Aniimo: Aniimo,
        slot3Item: String,
        slot4Aniimo: Aniimo,
        slot4Item: String,
        atk: Int,
        breakStat: Int,
        regen: Int,
        hp: Int,
        pdef: Int,
        mdef: Int,
        estimatedDps: Int
    ): String {
        val code = encodeBuildCode(
            mainAniimo, mainItem, partnerAniimo, partnerItem,
            slot3Aniimo, slot3Item, slot4Aniimo, slot4Item,
            atk, breakStat, regen, hp, pdef, mdef
        )
        return buildString {
            appendLine("=== ANIIMO 4-UNIT BUILD CARD ===")
            appendLine("Title: ${buildTitle.ifBlank { "${mainAniimo.name} ${mode.displayName} Squad" }}")
            appendLine("Mode: ${mode.displayName} • Est. Output: ${"%,d".format(estimatedDps)} DPS")
            appendLine("Slot 1 (Lead): ${mainAniimo.name} — 1 Held Item: $mainItem")
            appendLine("Slot 2 (Partner): ${partnerAniimo.name} — 1 Held Item: $partnerItem")
            appendLine("Slot 3 (Breaker): ${slot3Aniimo.name} — 1 Held Item: $slot3Item")
            appendLine("Slot 4 (Support): ${slot4Aniimo.name} — 1 Held Item: $slot4Item")
            appendLine("Awakening (Capability): ATK($atk) • BREAK($breakStat) • REGEN($regen) • HP($hp) • PDEF($pdef) • MDEF($mdef)")
            append("Build Code: $code")
        }
    }

    fun decodeBuildCode(rawCode: String): DecodedBuildPayload? {
        val cleaned = rawCode.trim()
            .substringAfter("Build Code:", rawCode.trim())
            .trim()
        if (!cleaned.startsWith("ANIIMO-V4|", ignoreCase = true)) return null
        val parts = cleaned.split("|")
        if (parts.size < 6) return null

        fun parseSlot(part: String): Pair<String, String> {
            val id = part.substringBefore(":").trim().lowercase()
            val item = part.substringAfter(":", "Pendant (+15 Contracted)").trim()
            return id to item
        }

        val (mId, mItem) = parseSlot(parts[1])
        val (pId, pItem) = parseSlot(parts[2])
        val (s3Id, s3Item) = parseSlot(parts[3])
        val (s4Id, s4Item) = parseSlot(parts[4])

        val statsMap = mutableMapOf<String, Int>()
        parts[5].split(",").forEach { kv ->
            val k = kv.substringBefore("=").trim().uppercase()
            val v = kv.substringAfter("=", "0").trim().toIntOrNull() ?: 0
            statsMap[k] = v.coerceIn(0, 20)
        }

        return DecodedBuildPayload(
            mainId = mId,
            mainItem = mItem,
            partnerId = pId,
            partnerItem = pItem,
            slot3Id = s3Id,
            slot3Item = s3Item,
            slot4Id = s4Id,
            slot4Item = s4Item,
            atk = statsMap["ATK"] ?: 20,
            breakStat = statsMap["BREAK"] ?: 15,
            regen = statsMap["REGEN"] ?: 12,
            hp = statsMap["HP"] ?: 6,
            pdef = statsMap["PDEF"] ?: 4,
            mdef = statsMap["MDEF"] ?: 4
        )
    }

    fun formatTeamShareCard(team: TopRatedTeam): String {
        return buildString {
            appendLine("=== ANIIMO 4-UNIT TEAM: ${team.title} ===")
            appendLine("Mode: ${team.mode.displayName} • Benchmark: ${team.estimatedDpsOrWinRate}")
            team.members.forEachIndexed { idx, m ->
                appendLine("Slot ${idx + 1} (${m.role}): ${m.aniimoName} — 1 Held Item: ${m.heldItem} (${m.awakeningOrder})")
            }
            val statsSummary = team.capabilities.joinToString(" • ") { "${it.name}(${it.costPoints})" }
            appendLine("Awakening: $statsSummary")
            append("Rotation: ${team.strategyGuide}")
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    fun shareViaAndroidSheet(context: Context, title: String, shareText: String) {
        try {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            val chooser = Intent.createChooser(sendIntent, "Share Aniimo Build")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (_: Exception) {
            // Safe no-op in headless test environments
        }
    }
}

/**
 * Interactive Modal Dialog for:
 * Tab 0: Publishing a 4-Aniimo Squad Build to Community Top Rated Teams (+ optionally Discussion Board)
 * Tab 1: Sharing / Exporting Build Code & Card (Clipboard Copy + Android System Share)
 * Tab 2: Importing a Friend's Build Code into the Simulator
 */
@Composable
fun SubmitShareBuildDialog(
    mainAniimo: Aniimo,
    mainHeldItem: String,
    partnerAniimo: Aniimo,
    partnerHeldItem: String,
    slot3Aniimo: Aniimo,
    slot3HeldItem: String,
    slot4Aniimo: Aniimo,
    slot4HeldItem: String,
    awakeningAtk: Int,
    awakeningBreak: Int,
    awakeningRegen: Int,
    awakeningHp: Int,
    awakeningPdef: Int,
    awakeningMdef: Int,
    estimatedDps: Int,
    initialTab: Int = 0,
    onDismiss: () -> Unit,
    onPublishToTopTeams: (TopRatedTeam) -> Unit,
    onPostToDiscussionBoard: ((
        aniimo: Aniimo,
        authorName: String,
        category: DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String,
        recommendedAwakening: String
    ) -> Unit)? = null,
    onImportBuildCode: ((BuildShareCodec.DecodedBuildPayload) -> Unit)? = null,
    onShowMessage: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    var activeTab by remember { mutableIntStateOf(initialTab) }

    var authorName by remember { mutableStateOf("") }
    var buildTitle by remember {
        mutableStateOf("${mainAniimo.name} & ${partnerAniimo.name} +15 Awakening Squad")
    }
    var selectedMode by remember { mutableStateOf(TeamMode.DPS) }
    var strategyNotes by remember {
        mutableStateOf(
            "Open with ${slot3Aniimo.name} (${slot3HeldItem}) to shred boss Break bar, buffer ${slot4Aniimo.name} (${slot4HeldItem}) + ${partnerAniimo.name} (${partnerHeldItem}), then burst with ${mainAniimo.name} holding ${mainHeldItem}."
        )
    }
    var importCodeInput by remember { mutableStateOf("") }
    var importError by remember { mutableStateOf<String?>(null) }

    val awakeningOrderString = remember(awakeningAtk, awakeningBreak, awakeningRegen, awakeningHp, awakeningPdef, awakeningMdef) {
        listOf(
            "ATK" to awakeningAtk,
            "BREAK" to awakeningBreak,
            "REGEN" to awakeningRegen,
            "HP" to awakeningHp,
            "PDEF" to awakeningPdef,
            "MDEF" to awakeningMdef
        ).sortedByDescending { it.second }
            .filter { it.second > 0 }
            .joinToString(" > ") { "${it.first} (${it.second})" }
            .ifBlank { "ATK (20) > BREAK (15) > REGEN (12)" }
    }

    val shareCode = remember(
        mainAniimo.id, mainHeldItem, partnerAniimo.id, partnerHeldItem,
        slot3Aniimo.id, slot3HeldItem, slot4Aniimo.id, slot4HeldItem,
        awakeningAtk, awakeningBreak, awakeningRegen, awakeningHp, awakeningPdef, awakeningMdef
    ) {
        BuildShareCodec.encodeBuildCode(
            mainAniimo, mainHeldItem, partnerAniimo, partnerHeldItem,
            slot3Aniimo, slot3HeldItem, slot4Aniimo, slot4HeldItem,
            awakeningAtk, awakeningBreak, awakeningRegen, awakeningHp, awakeningPdef, awakeningMdef
        )
    }

    val shareSummaryText = remember(buildTitle, selectedMode, shareCode, estimatedDps) {
        BuildShareCodec.formatShareableSummary(
            buildTitle = buildTitle,
            mode = selectedMode,
            mainAniimo = mainAniimo,
            mainItem = mainHeldItem,
            partnerAniimo = partnerAniimo,
            partnerItem = partnerHeldItem,
            slot3Aniimo = slot3Aniimo,
            slot3Item = slot3HeldItem,
            slot4Aniimo = slot4Aniimo,
            slot4Item = slot4HeldItem,
            atk = awakeningAtk,
            breakStat = awakeningBreak,
            regen = awakeningRegen,
            hp = awakeningHp,
            pdef = awakeningPdef,
            mdef = awakeningMdef,
            estimatedDps = estimatedDps
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .testTag("submit_share_build_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Submit & Share 4-Aniimo Build",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Publish to Community Top Teams, Copy/Share Build Code, or Import",
                            fontSize = 11.sp,
                            color = Color(0xFF38BDF8)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                // Mode / Action Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F172A))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        0 to "Publish Build",
                        1 to "Share / Copy",
                        2 to "Import Code"
                    ).forEach { (idx, label) ->
                        val selected = activeTab == idx
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) Color(0xFF38BDF8) else Color.Transparent)
                                .clickable { activeTab = idx }
                                .padding(vertical = 8.dp)
                                .testTag("build_dialog_tab_$idx"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected) Color(0xFF0F172A) else Color(0xFFCBD5E1)
                            )
                        }
                    }
                }

                when (activeTab) {
                    0 -> {
                        // TAB 0: PUBLISH BUILD TO COMMUNITY TOP TEAMS
                        OutlinedTextField(
                            value = authorName,
                            onValueChange = { authorName = it },
                            label = { Text("Creator / Author Name") },
                            placeholder = { Text("e.g. TheorycrafterPro") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("publish_build_author_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )

                        OutlinedTextField(
                            value = buildTitle,
                            onValueChange = { buildTitle = it },
                            label = { Text("Build Title") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("publish_build_title_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )

                        Text(
                            text = "Select Build Game Mode:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TeamMode.entries.filter { it != TeamMode.ALL }.forEach { mode ->
                                val isSel = selectedMode == mode
                                FilterChip(
                                    selected = isSel,
                                    onClick = { selectedMode = mode },
                                    label = { Text(mode.displayName, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(mode.badgeColorHex),
                                        selectedLabelColor = Color.White,
                                        containerColor = Color(0xFF0F172A),
                                        labelColor = Color(0xFF94A3B8)
                                    ),
                                    modifier = Modifier.testTag("publish_mode_chip_${mode.id}")
                                )
                            }
                        }

                        // 4-Aniimo Summary Preview Box
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF0F172A))
                                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(10.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "4-ANIIMO SQUAD & 1 HELD ITEM EACH",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFFD60A)
                            )
                            Text("1. ${mainAniimo.name} — $mainHeldItem", fontSize = 11.sp, color = Color.White)
                            Text("2. ${partnerAniimo.name} — $partnerHeldItem", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                            Text("3. ${slot3Aniimo.name} — $slot3HeldItem", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                            Text("4. ${slot4Aniimo.name} — $slot4HeldItem", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                            Text(
                                text = "Awakening Priority: $awakeningOrderString",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                        }

                        OutlinedTextField(
                            value = strategyNotes,
                            onValueChange = { strategyNotes = it },
                            label = { Text("Rotation & Strategy Guide") },
                            maxLines = 3,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("publish_build_strategy_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )

                        Button(
                            onClick = {
                                val capabilities = listOf(
                                    CapabilityPerk("ATK", "1st Priority", "+${(awakeningAtk * 2.8f).toInt()}% Base & Skill DMG", (awakeningAtk / 3).coerceIn(1, 6)),
                                    CapabilityPerk("BREAK", "2nd Priority", "+${awakeningBreak * 4}% Boss Stagger & Break Efficiency", (awakeningBreak / 3).coerceIn(1, 6)),
                                    CapabilityPerk("REGEN", "3rd Priority", "+${awakeningRegen * 3}% Energy Regen & Skill Uptime", (awakeningRegen / 3).coerceIn(1, 6)),
                                    CapabilityPerk("HP", " Survivability", "+${awakeningHp * 1250} Max Health Pool", (awakeningHp / 3).coerceIn(0, 6)),
                                    CapabilityPerk("PDEF", "Mitigation", "+${awakeningPdef * 45} Physical Defense", (awakeningPdef / 3).coerceIn(0, 6)),
                                    CapabilityPerk("MDEF", "Ward", "+${awakeningMdef * 45} Elemental Defense", (awakeningMdef / 3).coerceIn(0, 6))
                                )

                                val steps = listOf(
                                    PointInvestmentStep(
                                        pointRange = "Points 1 ➔ $awakeningAtk",
                                        capabilityOrSkill = "ATK (Attack Awakening)",
                                        rationale = "Maximizes ${mainAniimo.name}'s Core Skill & +15 Held Item scaling first.",
                                        priorityLevel = "1st Max (Core)"
                                    ),
                                    PointInvestmentStep(
                                        pointRange = "Next $awakeningBreak Pts",
                                        capabilityOrSkill = "BREAK (Armor Break Awakening)",
                                        rationale = "Accelerates boss stagger gauge shred for 5s Burst windows.",
                                        priorityLevel = "2nd Max (Synergy)"
                                    ),
                                    PointInvestmentStep(
                                        pointRange = "Next $awakeningRegen Pts",
                                        capabilityOrSkill = "REGEN & Survivability (HP/PDEF/MDEF)",
                                        rationale = "Maintains continuous Ultimate EP rotation and survivability.",
                                        priorityLevel = "3rd (Sustain)"
                                    )
                                )

                                val newTeam = TopRatedTeam(
                                    id = "user_team_${System.currentTimeMillis()}",
                                    title = buildTitle.ifBlank { "${mainAniimo.name} ${selectedMode.displayName} Build" },
                                    mode = selectedMode,
                                    primaryAniimoId = mainAniimo.id,
                                    primaryAniimoName = mainAniimo.name,
                                    members = listOf(
                                        TeamMemberBuild(
                                            aniimoId = mainAniimo.id,
                                            aniimoName = mainAniimo.name,
                                            role = "Slot 1 • Main Carry",
                                            element = mainAniimo.element,
                                            heldItem = mainHeldItem,
                                            heldItemEffect = "Primary +15 Contracted Striker Item",
                                            awakeningOrder = awakeningOrderString
                                        ),
                                        TeamMemberBuild(
                                            aniimoId = partnerAniimo.id,
                                            aniimoName = partnerAniimo.name,
                                            role = "Slot 2 • Synergy Partner",
                                            element = partnerAniimo.element,
                                            heldItem = partnerHeldItem,
                                            heldItemEffect = "Partner Resonance & Energy Support",
                                            awakeningOrder = "REGEN > ATK > BREAK"
                                        ),
                                        TeamMemberBuild(
                                            aniimoId = slot3Aniimo.id,
                                            aniimoName = slot3Aniimo.name,
                                            role = "Slot 3 • Armor Breaker",
                                            element = slot3Aniimo.element,
                                            heldItem = slot3HeldItem,
                                            heldItemEffect = "Boss Poise Shred & Defense Down",
                                            awakeningOrder = "BREAK > HP > PDEF"
                                        ),
                                        TeamMemberBuild(
                                            aniimoId = slot4Aniimo.id,
                                            aniimoName = slot4Aniimo.name,
                                            role = "Slot 4 • Flex Amplifier",
                                            element = slot4Aniimo.element,
                                            heldItem = slot4HeldItem,
                                            heldItemEffect = "Burst Window Buffs & Sustain",
                                            awakeningOrder = "REGEN > HP > MDEF"
                                        )
                                    ),
                                    capabilities = capabilities,
                                    pointInvestmentOrder = steps,
                                    strategyGuide = strategyNotes.ifBlank { "Community submitted 4-Aniimo squad build." },
                                    estimatedDpsOrWinRate = "${"%,d".format(estimatedDps)} DPS • Community Verified",
                                    author = authorName.ifBlank { "Community Builder" },
                                    upvotes = 1,
                                    hasUserUpvoted = true,
                                    isUserCreated = true
                                )
                                onPublishToTopTeams(newTeam)
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("confirm_publish_build_button")
                        ) {
                            Icon(Icons.Default.Publish, contentDescription = null, tint = Color(0xFF0F172A), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Publish to Community Top Teams",
                                color = Color(0xFF0F172A),
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        if (onPostToDiscussionBoard != null) {
                            OutlinedButton(
                                onClick = {
                                    onPostToDiscussionBoard(
                                        mainAniimo,
                                        authorName.ifBlank { "Community Builder" },
                                        DiscussionCategory.BUILD_IDEA,
                                        buildTitle.ifBlank { "${mainAniimo.name} 4-Aniimo Squad Build" },
                                        "$strategyNotes\n\n$shareSummaryText",
                                        mainHeldItem,
                                        awakeningOrderString
                                    )
                                    onDismiss()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("post_build_to_discussion_button")
                            ) {
                                Icon(Icons.Default.Forum, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Also Post Build Thread to Discussion Board", color = Color(0xFF38BDF8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    1 -> {
                        // TAB 1: SHARE & COPY BUILD CODE / CARD
                        Text(
                            text = "Shareable Build Card & Code",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF0F172A))
                                .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                                .testTag("share_build_code_preview")
                        ) {
                            Text(
                                text = shareSummaryText,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = Color(0xFFE2E8F0),
                                lineHeight = 16.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    BuildShareCodec.copyToClipboard(context, "Aniimo Build", shareSummaryText)
                                    onShowMessage?.invoke("Copied Build Card & Code to clipboard!")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("copy_build_code_confirm_btn")
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF0F172A), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Copy Build", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    BuildShareCodec.shareViaAndroidSheet(
                                        context = context,
                                        title = buildTitle.ifBlank { "${mainAniimo.name} Aniimo Build" },
                                        shareText = shareSummaryText
                                    )
                                    onShowMessage?.invoke("Opened Share Menu for ${mainAniimo.name} build!")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA855F7)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("system_share_build_btn")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Share App...", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    2 -> {
                        // TAB 2: IMPORT BUILD CODE
                        Text(
                            text = "Paste an Aniimo Build Code (ANIIMO-V4|...) to load all 4 Aniimo, their 1 Held Item each, and their 6 Awakening stats:",
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1),
                            lineHeight = 16.sp
                        )

                        OutlinedTextField(
                            value = importCodeInput,
                            onValueChange = {
                                importCodeInput = it
                                importError = null
                            },
                            label = { Text("Paste Build Code Here") },
                            placeholder = { Text(shareCode) },
                            maxLines = 4,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("import_build_code_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )

                        if (importError != null) {
                            Text(
                                text = importError!!,
                                fontSize = 11.sp,
                                color = Color(0xFFF87171),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = {
                                val candidate = importCodeInput.ifBlank { shareCode }
                                val decoded = BuildShareCodec.decodeBuildCode(candidate)
                                if (decoded == null) {
                                    importError = "Invalid Build Code format. Expected code starting with ANIIMO-V4|..."
                                } else {
                                    onImportBuildCode?.invoke(decoded)
                                    onShowMessage?.invoke("Imported 4-Aniimo Build Code into My Builds!")
                                    onDismiss()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("confirm_import_build_code_btn")
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, tint = Color(0xFF0F172A), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Load Build Code into Simulator", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
