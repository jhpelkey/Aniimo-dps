package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import android.content.Intent
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Aniimo
import com.example.data.model.CapabilityPerk
import com.example.data.model.DiscussionCategory
import com.example.data.model.Element
import com.example.data.model.PointInvestmentStep
import com.example.data.model.TeamMemberBuild
import com.example.data.model.TeamMode
import com.example.data.model.TopRatedTeam
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Dialog
import com.example.ui.components.AniimoElementBadge
import com.example.ui.components.AniimoFaceAvatar
import com.example.ui.components.BuildShareCodec
import com.example.ui.components.SubmitShareBuildDialog
import com.example.viewmodel.AniimoViewModel
import kotlin.math.roundToInt

data class TacticalRoleOption(
    val name: String,
    val description: String,
    val color: Color
)

val tacticalRoleOptions = listOf(
    TacticalRoleOption("DPS", "Primary offensive burst & sustained output", Color(0xFFEF4444)),
    TacticalRoleOption("Support", "Healing, party shields, energy & stat buffs", Color(0xFFA855F7)),
    TacticalRoleOption("Armor Breaker", "Boss shield fracture & heavy stagger poise", Color(0xFFFB923C)),
    TacticalRoleOption("Synergist", "Squad skill cycling & tactical amplification", Color(0xFF10B981))
)

private data class HeldItemOption(
    val name: String,
    val rarity: String,
    val effectSummary: String
)

private fun getHeldItemsForAniimo(aniimo: Aniimo): List<HeldItemOption> {
    val officialItems = com.example.data.repository.AniimoHeldItemsDatabase.allHeldItems.map { item ->
        HeldItemOption(
            name = "${item.name} (+15)",
            rarity = item.rarity,
            effectSummary = "${item.mainStat} • ${item.coreEffect}"
        )
    }
    val signatureOptions = aniimo.bestItems.map { item ->
        HeldItemOption(
            name = item.name,
            rarity = item.rarity,
            effectSummary = "${item.mainStat} • ${item.coreEffect}"
        )
    }
    val combined = (signatureOptions + officialItems).distinctBy { it.name.lowercase() }
    return combined
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DpsCalculatorScreen(
    aniimos: List<Aniimo>,
    mainAniimo: Aniimo,
    partnerAniimo: Aniimo,
    slot3Aniimo: Aniimo = aniimos.getOrElse(2) { mainAniimo },
    slot4Aniimo: Aniimo = aniimos.getOrElse(3) { partnerAniimo },
    mainHeldItem: String = mainAniimo.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)",
    partnerHeldItem: String = partnerAniimo.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)",
    slot3HeldItem: String = slot3Aniimo.bestItems.firstOrNull()?.name ?: "Bailite Crest (+15)",
    slot4HeldItem: String = slot4Aniimo.bestItems.firstOrNull()?.name ?: "Supernova Shard (+15)",
    itemEnhanceLevel: Int = 15,
    awakeningAtk: Int = 20,
    awakeningBreak: Int = 15,
    awakeningRegen: Int = 12,
    awakeningHp: Int = 6,
    awakeningPdef: Int = 4,
    awakeningMdef: Int = 4,
    // Legacy parameters kept for backward compatibility
    skill1Level: Int = awakeningAtk,
    skill2Level: Int = awakeningBreak,
    ultLevel: Int = awakeningRegen,
    weaponBonus: Int = itemEnhanceLevel,
    relicBonus: Int = awakeningHp,
    calcResult: AniimoViewModel.CalculatedDpsResult,
    onSelectMain: (Aniimo) -> Unit,
    onSelectPartner: (Aniimo) -> Unit,
    onSelectSlot3: (Aniimo) -> Unit = {},
    onSelectSlot4: (Aniimo) -> Unit = {},
    onSelectMainHeldItem: (String) -> Unit = {},
    onSelectPartnerHeldItem: (String) -> Unit = {},
    onSelectSlot3HeldItem: (String) -> Unit = {},
    onSelectSlot4HeldItem: (String) -> Unit = {},
    onItemEnhanceLevelChanged: (Int) -> Unit = {},
    onAwakeningStatsChanged: (Int, Int, Int, Int, Int, Int) -> Unit = { _, _, _, _, _, _ -> },
    onSkillLevelsChanged: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onGearBonusChanged: (Int, Int) -> Unit = { _, _ -> },
    onSaveBuild: (String) -> Unit,
    savedBuilds: List<com.example.data.model.CustomBuild> = emptyList(),
    onDeleteBuild: (Long) -> Unit = {},
    onLoadBuild: (com.example.data.model.CustomBuild) -> Unit = {},
    onPublishBuildToCommunity: ((TopRatedTeam) -> Unit)? = null,
    onPostBuildToDiscussion: ((Aniimo, String, DiscussionCategory, String, String, String, String) -> Unit)? = null,
    onImportBuildCode: ((BuildShareCodec.DecodedBuildPayload) -> Unit)? = null,
    onSubmitSpeedrunWithBuild: (() -> Unit)? = null,
    onShowMessage: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var buildTitle by remember { mutableStateOf("") }
    var slot1Role by remember { mutableStateOf("DPS") }
    var slot2Role by remember { mutableStateOf("Synergist") }
    var slot3Role by remember { mutableStateOf("Armor Breaker") }
    var slot4Role by remember { mutableStateOf("Support") }
    var activeSlotCount by remember { mutableIntStateOf(4) }
    var selectedRelicSet by remember { mutableStateOf("") }
    var isSubmitShareDialogOpen by remember { mutableStateOf(false) }
    var isSaveTeamDialogOpen by remember { mutableStateOf(false) }
    var submitShareInitialTab by remember { mutableStateOf(0) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 96.dp)
    ) {
        // Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E293B),
                            Color(0xFF0A0F1D)
                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "My Builds",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "4-Aniimo Team Builder • 1 Held Item Per Aniimo • 6-Stat Awakening",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF38BDF8),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.15f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "1 ITEM / UNIT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF34D399)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dedicated "Create New Build" Spot
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("create_new_build_spot_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF38BDF8).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Create Build",
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Build Creator",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Start a fresh 4-Aniimo team build with custom roles & items",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                buildTitle = ""
                                slot1Role = "DPS"
                                slot2Role = "Synergist"
                                slot3Role = "Armor Breaker"
                                slot4Role = "Support"
                                activeSlotCount = 4
                                aniimos.firstOrNull()?.let { onSelectMain(it) }
                                aniimos.getOrNull(1)?.let { onSelectPartner(it) }
                                aniimos.getOrNull(2)?.let { onSelectSlot3(it) }
                                aniimos.getOrNull(3)?.let { onSelectSlot4(it) }
                                onSkillLevelsChanged(20, 18, 14)
                                onGearBonusChanged(15, 100)
                                onShowMessage?.invoke("Started a fresh 4-Aniimo build! Configure team roles, items, and stats below.")
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("create_build_spot_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "New Build",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF0F172A)
                            )
                        }

                        Button(
                            onClick = { isSaveTeamDialogOpen = true },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("save_team_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkAdd,
                                contentDescription = null,
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Save Team",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }

            // Live Output HUD Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dps_result_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    mainAniimo.element.glowColor.copy(alpha = 0.35f),
                                    partnerAniimo.element.glowColor.copy(alpha = 0.25f)
                                )
                            )
                        )
                        .border(
                            1.5.dp,
                            Color(0xFF38BDF8).copy(alpha = 0.5f),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LIVE SQUAD TELEMETRY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF94A3B8),
                            letterSpacing = 1.sp
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFFF453A).copy(alpha = 0.2f))
                                .border(1.dp, Color(0xFFFF453A), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = calcResult.tierRating,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFF6961)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Sustained DPS / Sec",
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1)
                            )
                            Text(
                                text = "%,d".format(calcResult.totalDps),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF38BDF8),
                                modifier = Modifier.testTag("calculated_total_dps_text")
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "5s Stagger Burst Window",
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1)
                            )
                            Text(
                                text = "%,d".format(calcResult.burstWindowDps),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9F0A)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 4-Stat Telemetry Bar (Held Item Amp, Break Efficiency, Energy Regen, Survivability EHP)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0A0F1D).copy(alpha = 0.85f))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TelemetryMiniStat(
                            label = "1-Item Amp",
                            value = "+${calcResult.itemBonusPercent}%",
                            color = Color(0xFFFFD60A)
                        )
                        TelemetryMiniStat(
                            label = "Break Speed",
                            value = "${calcResult.breakEfficiencyPercent}%",
                            color = Color(0xFFFB923C)
                        )
                        TelemetryMiniStat(
                            label = "EP Regen",
                            value = "${calcResult.energyRegenBonusPercent}%",
                            color = Color(0xFF34D399)
                        )
                        TelemetryMiniStat(
                            label = "Squad EHP",
                            value = "%,d".format(calcResult.effectiveHpScore),
                            color = Color(0xFFA78BFA)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Active Resonance & Awakening Order Pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A).copy(alpha = 0.85f))
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Synergy: ${calcResult.resonanceTitle}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "+${calcResult.synergyBonusPercent}% Team DMG",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF34D399)
                                )
                            }
                            Text(
                                text = "Awakening Priority: ${calcResult.awakeningSummary} (${calcResult.totalAwakeningPoints} pts invested)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }
                }
            }

            // SECTION 1: Team Squad Slots (Up to 4 Characters)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("team_squad_slots_section")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "1. Team Squad Slots (Up to 4 Characters)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Define your team composition with customizable roles (DPS, Support, Armor Breaker, Synergist)",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF38BDF8).copy(alpha = 0.15f))
                                .border(1.dp, Color(0xFF38BDF8), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "$activeSlotCount / 4 ACTIVE",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }

                    // Team Size Selector Pills (1 to 4 slots)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            1 to "1 Slot",
                            2 to "2 Slots",
                            3 to "3 Slots",
                            4 to "4 Slots (Full)"
                        ).forEach { (count, label) ->
                            val isSelected = activeSlotCount == count
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) Color(0xFF38BDF8) else Color.Transparent)
                                    .clickable { activeSlotCount = count }
                                    .padding(vertical = 6.dp)
                                    .testTag("team_size_$count"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8)
                                )
                            }
                        }
                    }

                    // Slot #1: Always Active
                    SquadMemberItemSlotCard(
                        slotNumber = 1,
                        selectedRole = slot1Role,
                        onSelectRole = { slot1Role = it },
                        accentColor = Color(0xFFEF4444),
                        selectedAniimo = mainAniimo,
                        selectedHeldItem = mainHeldItem,
                        allAniimos = aniimos,
                        onSelectAniimo = onSelectMain,
                        onSelectHeldItem = onSelectMainHeldItem,
                        aniimoDropdownTag = "sim_main_dropdown",
                        itemDropdownTag = "sim_main_item_dropdown",
                        onRemoveSlot = null
                    )

                    // Slot #2 (Active if activeSlotCount >= 2)
                    if (activeSlotCount >= 2) {
                        SquadMemberItemSlotCard(
                            slotNumber = 2,
                            selectedRole = slot2Role,
                            onSelectRole = { slot2Role = it },
                            accentColor = Color(0xFF10B981),
                            selectedAniimo = partnerAniimo,
                            selectedHeldItem = partnerHeldItem,
                            allAniimos = aniimos.filter { it.id != mainAniimo.id },
                            onSelectAniimo = onSelectPartner,
                            onSelectHeldItem = onSelectPartnerHeldItem,
                            aniimoDropdownTag = "sim_partner_dropdown",
                            itemDropdownTag = "sim_partner_item_dropdown",
                            onRemoveSlot = { activeSlotCount = 1 }
                        )
                    }

                    // Slot #3 (Active if activeSlotCount >= 3)
                    if (activeSlotCount >= 3) {
                        SquadMemberItemSlotCard(
                            slotNumber = 3,
                            selectedRole = slot3Role,
                            onSelectRole = { slot3Role = it },
                            accentColor = Color(0xFFFB923C),
                            selectedAniimo = slot3Aniimo,
                            selectedHeldItem = slot3HeldItem,
                            allAniimos = aniimos,
                            onSelectAniimo = onSelectSlot3,
                            onSelectHeldItem = onSelectSlot3HeldItem,
                            aniimoDropdownTag = "sim_slot3_dropdown",
                            itemDropdownTag = "sim_slot3_item_dropdown",
                            onRemoveSlot = { activeSlotCount = 2 }
                        )
                    }

                    // Slot #4 (Active if activeSlotCount >= 4)
                    if (activeSlotCount >= 4) {
                        SquadMemberItemSlotCard(
                            slotNumber = 4,
                            selectedRole = slot4Role,
                            onSelectRole = { slot4Role = it },
                            accentColor = Color(0xFFA855F7),
                            selectedAniimo = slot4Aniimo,
                            selectedHeldItem = slot4HeldItem,
                            allAniimos = aniimos,
                            onSelectAniimo = onSelectSlot4,
                            onSelectHeldItem = onSelectSlot4HeldItem,
                            aniimoDropdownTag = "sim_slot4_dropdown",
                            itemDropdownTag = "sim_slot4_item_dropdown",
                            onRemoveSlot = { activeSlotCount = 3 }
                        )
                    }

                    // Dynamic Add Character Slot button if under 4
                    if (activeSlotCount < 4) {
                        Button(
                            onClick = { activeSlotCount = (activeSlotCount + 1).coerceAtMost(4) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("add_character_slot_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Character Slot",
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+ Add Character Slot (${activeSlotCount + 1}/4)",
                                color = Color(0xFF38BDF8),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // SECTION 2: Save, Publish & Share Your 4-Aniimo Build
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "2. Save, Publish & Share Your 4-Aniimo Build",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Save locally, publish your 4-Aniimo build to Community Top Teams or the Discussion Board, or share/import a Build Code.",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = buildTitle,
                        onValueChange = { buildTitle = it },
                        label = { Text("Custom Build Name (Optional)") },
                        placeholder = { Text("e.g. ${mainAniimo.name} +15 Contract & ATK/BREAK Awakening") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("build_title_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF334155)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { isSaveTeamDialogOpen = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_team_button_secondary")
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = null,
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Team Squad (4 Characters & Roles)",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onSaveBuild(buildTitle)
                            buildTitle = ""
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_build_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = null,
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save to My Builds",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                submitShareInitialTab = 0
                                isSubmitShareDialogOpen = true
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("open_submit_share_build_dialog_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Publish / Share Build",
                                color = Color(0xFF0F172A),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Button(
                            onClick = {
                                submitShareInitialTab = 1
                                isSubmitShareDialogOpen = true
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA855F7)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("open_share_code_tab_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Copy / Import Code",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            // Saved Builds Library Section inside My Builds
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("my_builds_saved_library_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Builds Library (${savedBuilds.size})",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Tap Load to edit in builder",
                            fontSize = 11.sp,
                            color = Color(0xFF38BDF8)
                        )
                    }

                    if (savedBuilds.isEmpty()) {
                        Text(
                            text = "No saved builds yet. Configure your 4-Aniimo team, 1 Held Item per unit, and 6-Stat Awakening points above, then tap 'Save to My Builds'!",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            lineHeight = 17.sp
                        )
                    } else {
                        savedBuilds.forEach { saved ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF0F172A))
                                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = saved.buildTitle,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${saved.calculatedDps / 1000}k DPS",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 13.sp,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                Text(
                                    text = "Lead: ${saved.aniimoName} • Held Item: ${saved.weaponName}",
                                    fontSize = 11.sp,
                                    color = Color(0xFFFFD60A),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Strategy: ${saved.relicSetName}",
                                    fontSize = 11.sp,
                                    color = Color(0xFFCBD5E1)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = {
                                            val shareText = buildString {
                                                appendLine("=== ANIIMO SAVED BUILD: ${saved.buildTitle} ===")
                                                appendLine("Lead: ${saved.aniimoName} | Held Item: ${saved.weaponName}")
                                                appendLine("Squad & Awakening: ${saved.relicSetName}")
                                                appendLine("Benchmark: ${saved.calculatedDps / 1000}k DPS")
                                            }
                                            clipboardManager.setText(AnnotatedString(shareText))
                                            onShowMessage?.invoke("Copied '${saved.buildTitle}' build summary to clipboard!")
                                            runCatching {
                                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                                    type = "text/plain"
                                                    putExtra(Intent.EXTRA_SUBJECT, saved.buildTitle)
                                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                                }
                                                context.startActivity(Intent.createChooser(sendIntent, "Share Saved Build"))
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981).copy(alpha = 0.2f)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("share_saved_build_${saved.id}")
                                    ) {
                                        Text("Share", color = Color(0xFF34D399), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onLoadBuild(saved) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Load", color = Color(0xFF38BDF8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onDeleteBuild(saved.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F1D24)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Delete", color = Color(0xFFFF6961), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (isSubmitShareDialogOpen) {
        SubmitShareBuildDialog(
            mainAniimo = mainAniimo,
            mainHeldItem = mainHeldItem,
            partnerAniimo = partnerAniimo,
            partnerHeldItem = partnerHeldItem,
            slot3Aniimo = slot3Aniimo,
            slot3HeldItem = slot3HeldItem,
            slot4Aniimo = slot4Aniimo,
            slot4HeldItem = slot4HeldItem,
            awakeningAtk = awakeningAtk,
            awakeningBreak = awakeningBreak,
            awakeningRegen = awakeningRegen,
            awakeningHp = awakeningHp,
            awakeningPdef = awakeningPdef,
            awakeningMdef = awakeningMdef,
            estimatedDps = calcResult.totalDps,
            initialTab = submitShareInitialTab,
            onDismiss = { isSubmitShareDialogOpen = false },
            onPublishToTopTeams = { team ->
                onPublishBuildToCommunity?.invoke(team)
                isSubmitShareDialogOpen = false
            },
            onPostToDiscussionBoard = { aniimo, author, category, title, content, item, awakening ->
                onPostBuildToDiscussion?.invoke(aniimo, author, category, title, content, item, awakening)
                isSubmitShareDialogOpen = false
            },
            onImportBuildCode = { payload ->
                onImportBuildCode?.invoke(payload)
            },
            onShowMessage = { msg ->
                onShowMessage?.invoke(msg)
            }
        )
    }

    if (isSaveTeamDialogOpen) {
        SaveTeamDialog(
            mainAniimo = mainAniimo,
            partnerAniimo = partnerAniimo,
            slot3Aniimo = slot3Aniimo,
            slot4Aniimo = slot4Aniimo,
            activeSlotCount = activeSlotCount,
            slot1Role = slot1Role,
            slot2Role = slot2Role,
            slot3Role = slot3Role,
            slot4Role = slot4Role,
            mainHeldItem = mainHeldItem,
            partnerHeldItem = partnerHeldItem,
            slot3HeldItem = slot3HeldItem,
            slot4HeldItem = slot4HeldItem,
            selectedRelicSet = selectedRelicSet,
            awakeningAtk = awakeningAtk,
            awakeningBreak = awakeningBreak,
            awakeningRegen = awakeningRegen,
            awakeningHp = awakeningHp,
            awakeningPdef = awakeningPdef,
            awakeningMdef = awakeningMdef,
            estimatedDps = calcResult.totalDps.toInt(),
            onDismiss = { isSaveTeamDialogOpen = false },
            onConfirmSaveTeam = { newTeam ->
                onPublishBuildToCommunity?.invoke(newTeam)
                onSaveBuild(newTeam.title)
                onShowMessage?.invoke("Team '${newTeam.title}' saved successfully!")
                isSaveTeamDialogOpen = false
            }
        )
    }
}

@Composable
private fun TelemetryMiniStat(
    label: String,
    value: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 9.sp,
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SquadMemberItemSlotCard(
    slotNumber: Int,
    selectedRole: String,
    onSelectRole: (String) -> Unit,
    accentColor: Color,
    selectedAniimo: Aniimo,
    selectedHeldItem: String,
    allAniimos: List<Aniimo>,
    onSelectAniimo: (Aniimo) -> Unit,
    onSelectHeldItem: (String) -> Unit,
    aniimoDropdownTag: String,
    itemDropdownTag: String,
    onRemoveSlot: (() -> Unit)? = null
) {
    var aniimoExpanded by remember { mutableStateOf(false) }
    var itemExpanded by remember { mutableStateOf(false) }
    var roleDropdownExpanded by remember { mutableStateOf(false) }
    val availableItems = remember(selectedAniimo) { getHeldItemsForAniimo(selectedAniimo) }
    val activeItemDetails = availableItems.find { it.name.equals(selectedHeldItem, ignoreCase = true) }
        ?: availableItems.firstOrNull()

    var isCustomAniimoMode by remember(selectedAniimo.id) {
        mutableStateOf(selectedAniimo.id.startsWith("custom_"))
    }
    var customAniimoName by remember(selectedAniimo.name) {
        mutableStateOf(if (selectedAniimo.id.startsWith("custom_")) selectedAniimo.name else "")
    }

    val currentRoleOption = tacticalRoleOptions.find { it.name.equals(selectedRole, ignoreCase = true) }
    val roleColor = currentRoleOption?.color ?: accentColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A))
            .border(1.dp, roleColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Header Row: Slot badge + Interactive Role Dropdown + Element + Remove
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(roleColor.copy(alpha = 0.2f))
                            .border(1.dp, roleColor, RoundedCornerShape(5.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "SLOT #$slotNumber",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = roleColor
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Tactical Role Dropdown (DPS, Support, Armor Breaker, Synergist)
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF1E293B))
                                .border(1.dp, roleColor.copy(alpha = 0.7f), RoundedCornerShape(6.dp))
                                .clickable { roleDropdownExpanded = true }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .testTag("role_dropdown_slot_$slotNumber"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(roleColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = selectedRole,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Change Role for Slot #$slotNumber",
                                tint = roleColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = roleDropdownExpanded,
                            onDismissRequest = { roleDropdownExpanded = false }
                        ) {
                            Text(
                                text = "TACTICAL ROLE SELECTION",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF94A3B8),
                                letterSpacing = 0.8.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                            tacticalRoleOptions.forEach { roleOption ->
                                val isSelected = selectedRole.equals(roleOption.name, ignoreCase = true)
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .clip(CircleShape)
                                                    .background(roleOption.color)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = roleOption.name,
                                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                                    color = if (isSelected) roleOption.color else Color.White,
                                                    fontSize = 13.sp
                                                )
                                                Text(
                                                    text = roleOption.description,
                                                    fontSize = 10.sp,
                                                    color = Color(0xFF94A3B8)
                                                )
                                            }
                                            if (isSelected) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = roleOption.color,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        onSelectRole(roleOption.name)
                                        roleDropdownExpanded = false
                                    },
                                    modifier = Modifier.testTag("role_option_${roleOption.name.lowercase().replace(" ", "_")}_slot_$slotNumber")
                                )
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AniimoElementBadge(element = selectedAniimo.element, compact = true)
                    selectedAniimo.secondaryElement?.let { sec ->
                        AniimoElementBadge(element = sec, compact = true)
                    }
                    if (onRemoveSlot != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFFF453A).copy(alpha = 0.15f))
                                .clickable { onRemoveSlot() }
                                .padding(4.dp)
                                .testTag("remove_slot_${slotNumber}_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove Slot #$slotNumber",
                                tint = Color(0xFFFF453A),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Mode Selector: Catalog vs Custom / Unreleased Aniimo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isCustomAniimoMode) "Mode: Custom / Unreleased Aniimo" else "Mode: Cataloged (${allAniimos.size} Available)",
                    fontSize = 10.sp,
                    color = if (isCustomAniimoMode) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                    fontWeight = FontWeight.SemiBold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (!isCustomAniimoMode) Color(0xFF1E293B) else Color.Transparent)
                            .border(1.dp, if (!isCustomAniimoMode) accentColor else Color(0xFF334155), RoundedCornerShape(6.dp))
                            .clickable { isCustomAniimoMode = false }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Catalog", fontSize = 10.sp, color = if (!isCustomAniimoMode) accentColor else Color(0xFF64748B), fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isCustomAniimoMode) Color(0xFF38BDF8).copy(alpha = 0.2f) else Color.Transparent)
                            .border(1.dp, if (isCustomAniimoMode) Color(0xFF38BDF8) else Color(0xFF334155), RoundedCornerShape(6.dp))
                            .clickable { isCustomAniimoMode = true }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Custom Name", fontSize = 10.sp, color = if (isCustomAniimoMode) Color(0xFF38BDF8) else Color(0xFF64748B), fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Aniimo Selector: Either Catalog Dropdown OR Custom Editable Text Field
            if (!isCustomAniimoMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AniimoFaceAvatar(
                        aniimo = selectedAniimo,
                        size = 44.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    ExposedDropdownMenuBox(
                        expanded = aniimoExpanded,
                        onExpandedChange = { aniimoExpanded = !aniimoExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = "${selectedAniimo.name} (${selectedAniimo.aniilogNumber} • ${selectedAniimo.role})",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Aniimo #${slotNumber}") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = aniimoExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag(aniimoDropdownTag),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = accentColor,
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = aniimoExpanded,
                            onDismissRequest = { aniimoExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text("✨ Type Custom / Unreleased Aniimo...", color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
                                },
                                onClick = {
                                    isCustomAniimoMode = true
                                    aniimoExpanded = false
                                }
                            )
                            allAniimos.forEach { aniimo ->
                                DropdownMenuItem(
                                    text = {
                                        Text("${aniimo.aniilogNumber} ${aniimo.name} • ${aniimo.element.displayName} (${aniimo.metaTier})")
                                    },
                                    onClick = {
                                        onSelectAniimo(aniimo)
                                        aniimoExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                // Custom Name Input and Element Selection
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AniimoFaceAvatar(
                            aniimo = selectedAniimo,
                            size = 44.dp,
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        OutlinedTextField(
                            value = customAniimoName,
                            onValueChange = { newName ->
                                customAniimoName = newName
                                val clean = newName.trim()
                                val updated = selectedAniimo.copy(
                                    id = if (clean.isNotBlank()) "custom_${clean.lowercase().replace(" ", "_")}" else "custom_aniimo_$slotNumber",
                                    name = if (clean.isNotBlank()) clean else "Custom Aniimo",
                                    aniilogNumber = "Custom"
                                )
                                onSelectAniimo(updated)
                            },
                            label = { Text("Type Custom Aniimo / Form Name") },
                            placeholder = { Text("e.g. Zephyrion, Umbral Shadow Fox...") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("custom_aniimo_input_slot_$slotNumber"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF38BDF8),
                                unfocusedTextColor = Color(0xFF38BDF8),
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    }

                    // Element selector for custom Aniimo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Element.values().forEach { elem ->
                            val isSelected = selectedAniimo.element == elem
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) elem.primaryColor.copy(alpha = 0.25f) else Color(0xFF131D33))
                                    .border(1.dp, if (isSelected) elem.primaryColor else Color(0xFF1E293B), RoundedCornerShape(6.dp))
                                    .clickable {
                                        onSelectAniimo(selectedAniimo.copy(element = elem))
                                    }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = elem.displayName,
                                    fontSize = 9.sp,
                                    color = if (isSelected) elem.primaryColor else Color(0xFF94A3B8),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // 1 Held Item Selector for this Aniimo
            ExposedDropdownMenuBox(
                expanded = itemExpanded,
                onExpandedChange = { itemExpanded = !itemExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedHeldItem,
                    onValueChange = { onSelectHeldItem(it) },
                    readOnly = false,
                    label = { Text("${selectedAniimo.name}'s 1 Held Item (Select or Type Custom)") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = itemExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag(itemDropdownTag),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFFFFD60A),
                        unfocusedTextColor = Color(0xFFFFD60A),
                        focusedBorderColor = Color(0xFFFFD60A),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )
                ExposedDropdownMenu(
                    expanded = itemExpanded,
                    onDismissRequest = { itemExpanded = false }
                ) {
                    availableItems.forEach { itemOption ->
                        DropdownMenuItem(
                            text = {
                                Column(modifier = Modifier.padding(vertical = 2.dp)) {
                                    Text(
                                        text = "${itemOption.name} (${itemOption.rarity})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = itemOption.effectSummary,
                                        fontSize = 11.sp,
                                        color = Color(0xFF94A3B8),
                                        maxLines = 1
                                    )
                                }
                            },
                            onClick = {
                                onSelectHeldItem(itemOption.name)
                                itemExpanded = false
                            }
                        )
                    }
                }
            }

            if (activeItemDetails != null) {
                Text(
                    text = "Item Effect: ${activeItemDetails.effectSummary}",
                    fontSize = 11.sp,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 15.sp
                )
            } else if (selectedHeldItem.isNotBlank()) {
                Text(
                    text = "Custom Held Item: '$selectedHeldItem' equipped with +15 max scaling stats.",
                    fontSize = 11.sp,
                    color = Color(0xFF38BDF8),
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
private fun AwakeningPresetChip(
    label: String,
    color: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.16f))
            .border(1.dp, color.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 7.dp)
            .testTag(testTag)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun AwakeningStatSliderRow(
    statCode: String,
    statTitle: String,
    roleBadge: String,
    badgeColor: Color,
    points: Int,
    effectReadout: String,
    description: String,
    onPointsChange: (Int) -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(badgeColor.copy(alpha = 0.2f))
                            .border(1.dp, badgeColor, RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = statCode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = badgeColor
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = statTitle,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = roleBadge,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$points / 20 pts",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = badgeColor
                    )
                    Text(
                        text = effectReadout,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 15.sp
            )

            Slider(
                value = points.toFloat(),
                onValueChange = { onPointsChange(it.roundToInt()) },
                valueRange = 0f..20f,
                steps = 19,
                colors = SliderDefaults.colors(
                    thumbColor = badgeColor,
                    activeTrackColor = badgeColor,
                    inactiveTrackColor = Color(0xFF1E293B)
                ),
                modifier = Modifier.testTag(testTag)
            )
        }
    }
}

@Composable
fun SaveTeamDialog(
    mainAniimo: Aniimo,
    partnerAniimo: Aniimo,
    slot3Aniimo: Aniimo,
    slot4Aniimo: Aniimo,
    activeSlotCount: Int,
    slot1Role: String,
    slot2Role: String,
    slot3Role: String,
    slot4Role: String,
    mainHeldItem: String,
    partnerHeldItem: String,
    slot3HeldItem: String,
    slot4HeldItem: String,
    selectedRelicSet: String,
    awakeningAtk: Int,
    awakeningBreak: Int,
    awakeningRegen: Int,
    awakeningHp: Int,
    awakeningPdef: Int,
    awakeningMdef: Int,
    estimatedDps: Int,
    onDismiss: () -> Unit,
    onConfirmSaveTeam: (TopRatedTeam) -> Unit
) {
    var teamTitle by remember { mutableStateOf("${mainAniimo.name} Squad") }
    var authorName by remember { mutableStateOf("") }
    var strategyNotes by remember {
        mutableStateOf("4-Aniimo synergy team. Primary: $slot1Role, Allies: $slot2Role, $slot3Role, $slot4Role.")
    }
    var selectedMode by remember { mutableStateOf(TeamMode.DPS) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0F172A),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Team Build",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                Text(
                    text = "Save this active team squad with customized roles (DPS, Support, Armor Breaker, Synergist), held items, and awakening priorities.",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )

                // Roster Preview
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF131D33))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "SQUAD MEMBERS ($activeSlotCount HEROES)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )

                    val teamSlots = listOf(
                        Triple(1, mainAniimo, slot1Role),
                        Triple(2, partnerAniimo, slot2Role),
                        Triple(3, slot3Aniimo, slot3Role),
                        Triple(4, slot4Aniimo, slot4Role)
                    ).take(activeSlotCount)

                    teamSlots.forEach { (slotNum, aniimo, role) ->
                        val item = when (slotNum) {
                            1 -> mainHeldItem
                            2 -> partnerHeldItem
                            3 -> slot3HeldItem
                            else -> slot4HeldItem
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AniimoFaceAvatar(aniimo = aniimo, size = 26.dp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Slot #$slotNum: ${aniimo.name}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF1E293B))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = role,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item,
                                    fontSize = 10.sp,
                                    color = Color(0xFFFFD700)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Estimated Benchmark: ${"%,d".format(estimatedDps)} DPS",
                        fontSize = 10.sp,
                        color = Color(0xFF38BDF8)
                    )
                }

                // Team Mode Selector Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TeamMode.values().filter { it != TeamMode.ALL }.forEach { mode ->
                        val isSel = selectedMode == mode
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) Color(0xFF10B981).copy(alpha = 0.25f) else Color(0xFF131D33))
                                .border(
                                    1.dp,
                                    if (isSel) Color(0xFF10B981) else Color(0xFF1E293B),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedMode = mode }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = mode.displayName,
                                fontSize = 11.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSel) Color(0xFF10B981) else Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = teamTitle,
                    onValueChange = { teamTitle = it },
                    label = { Text("Team Squad Name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_team_title_input")
                )

                OutlinedTextField(
                    value = authorName,
                    onValueChange = { authorName = it },
                    label = { Text("Runner / Author Nickname (Optional)") },
                    placeholder = { Text("e.g. MasterAniimo") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_team_author_input")
                )

                OutlinedTextField(
                    value = strategyNotes,
                    onValueChange = { strategyNotes = it },
                    label = { Text("Team Strategy & Synergy Notes") },
                    minLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B981),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_team_description_input")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Cancel", color = Color(0xFF94A3B8))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val awakeningOrderString = "ATK: $awakeningAtk | BRK: $awakeningBreak | REG: $awakeningRegen | HP: $awakeningHp"
                            val members = mutableListOf(
                                TeamMemberBuild(
                                    aniimoId = mainAniimo.id,
                                    aniimoName = mainAniimo.name,
                                    role = slot1Role,
                                    element = mainAniimo.element,
                                    heldItem = mainHeldItem,
                                    heldItemEffect = "Slot 1 Main: +15 Contracted Item",
                                    awakeningOrder = awakeningOrderString
                                )
                            )
                            if (activeSlotCount >= 2) {
                                members.add(
                                    TeamMemberBuild(
                                        aniimoId = partnerAniimo.id,
                                        aniimoName = partnerAniimo.name,
                                        role = slot2Role,
                                        element = partnerAniimo.element,
                                        heldItem = partnerHeldItem,
                                        heldItemEffect = "Slot 2 Ally: +15 Contracted Item",
                                        awakeningOrder = "REGEN > ATK > BREAK"
                                    )
                                )
                            }
                            if (activeSlotCount >= 3) {
                                members.add(
                                    TeamMemberBuild(
                                        aniimoId = slot3Aniimo.id,
                                        aniimoName = slot3Aniimo.name,
                                        role = slot3Role,
                                        element = slot3Aniimo.element,
                                        heldItem = slot3HeldItem,
                                        heldItemEffect = "Slot 3 Ally: Poise Break Support",
                                        awakeningOrder = "BREAK > HP > PDEF"
                                    )
                                )
                            }
                            if (activeSlotCount >= 4) {
                                members.add(
                                    TeamMemberBuild(
                                        aniimoId = slot4Aniimo.id,
                                        aniimoName = slot4Aniimo.name,
                                        role = slot4Role,
                                        element = slot4Aniimo.element,
                                        heldItem = slot4HeldItem,
                                        heldItemEffect = "Slot 4 Ally: Sustain & Buffs",
                                        awakeningOrder = "REGEN > HP > MDEF"
                                    )
                                )
                            }

                            val newTeam = TopRatedTeam(
                                id = "user_team_${System.currentTimeMillis()}",
                                title = teamTitle.ifBlank { "${mainAniimo.name} Squad" },
                                mode = selectedMode,
                                primaryAniimoId = mainAniimo.id,
                                primaryAniimoName = mainAniimo.name,
                                members = members,
                                capabilities = listOf(
                                    CapabilityPerk(
                                        name = "ATK",
                                        tier = "1st Priority (Max)",
                                        effect = "$slot1Role Carry Synergy: Primary damage driver backed by ${members.drop(1).joinToString(", ") { it.role }}.",
                                        costPoints = awakeningAtk
                                    )
                                ),
                                pointInvestmentOrder = listOf(
                                    PointInvestmentStep(
                                        pointRange = "Points 1 -> 20",
                                        capabilityOrSkill = "ATK Focus",
                                        rationale = "Maximizes clear speed and burst multipliers.",
                                        priorityLevel = "1st Priority"
                                    )
                                ),
                                strategyGuide = strategyNotes.ifBlank { "Custom saved 4-character team." },
                                estimatedDpsOrWinRate = "${"%,d".format(estimatedDps)} DPS",
                                author = authorName.ifBlank { "Aniimo Master" },
                                upvotes = 1,
                                hasUserUpvoted = true,
                                isUserCreated = true
                            )
                            onConfirmSaveTeam(newTeam)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        modifier = Modifier.testTag("confirm_save_team_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = null,
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Save Team",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

