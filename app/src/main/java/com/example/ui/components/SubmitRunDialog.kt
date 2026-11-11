package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Aniimo
import com.example.data.model.BossEncounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitRunDialog(
    bosses: List<BossEncounter>,
    aniimos: List<Aniimo>,
    onDismiss: () -> Unit,
    onSubmit: (
        runnerName: String,
        bossId: String,
        mainAniimoId: String,
        partnerAniimoId: String,
        slot3AniimoId: String,
        slot4AniimoId: String,
        minutes: Int,
        seconds: Int,
        millis: Int,
        videoUrl: String,
        weapon: String,
        relicSet: String,
        notes: String,
        videoDurationSeconds: Int,
        customBossName: String?,
        customMainAniimoName: String?,
        customPartnerAniimoName: String?,
        customSlot3AniimoName: String?,
        customSlot4AniimoName: String?
    ) -> Boolean,
    initialMainAniimoId: String? = null,
    initialPartnerAniimoId: String? = null,
    initialSlot3AniimoId: String? = null,
    initialSlot4AniimoId: String? = null,
    initialWeaponName: String = "",
    initialRelicSetName: String = ""
) {
    var runnerName by remember { mutableStateOf("") }
    var selectedBoss by remember { mutableStateOf(bosses.first()) }
    var selectedMainAniimo by remember(initialMainAniimoId) {
        mutableStateOf(
            aniimos.find { it.id.equals(initialMainAniimoId, ignoreCase = true) } ?: aniimos.first()
        )
    }
    var selectedPartnerAniimo by remember(initialPartnerAniimoId, selectedMainAniimo.id) {
        mutableStateOf(
            aniimos.find { it.id.equals(initialPartnerAniimoId, ignoreCase = true) }
                ?: aniimos.firstOrNull { it.id != selectedMainAniimo.id }
                ?: aniimos.getOrNull(1)
                ?: aniimos.first()
        )
    }
    var selectedSlot3Aniimo by remember(initialSlot3AniimoId) {
        mutableStateOf(
            aniimos.find { it.id.equals(initialSlot3AniimoId, ignoreCase = true) }
                ?: aniimos.getOrNull(2)
                ?: aniimos.first()
        )
    }
    var selectedSlot4Aniimo by remember(initialSlot4AniimoId) {
        mutableStateOf(
            aniimos.find { it.id.equals(initialSlot4AniimoId, ignoreCase = true) }
                ?: aniimos.getOrNull(3)
                ?: aniimos.last()
        )
    }

    var minutesText by remember { mutableStateOf("0") }
    var secondsText by remember { mutableStateOf("45") }
    var millisText by remember { mutableStateOf("20") }
    var videoUrl by remember { mutableStateOf("") }
    var videoDurationText by remember { mutableStateOf("48") }
    var weaponName by remember(initialWeaponName) { mutableStateOf(initialWeaponName) }
    var relicSetName by remember(initialRelicSetName) { mutableStateOf(initialRelicSetName) }
    var runnerNotes by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var bossDropdownExpanded by remember { mutableStateOf(false) }
    var mainDropdownExpanded by remember { mutableStateOf(false) }
    var partnerDropdownExpanded by remember { mutableStateOf(false) }
    var slot3DropdownExpanded by remember { mutableStateOf(false) }
    var slot4DropdownExpanded by remember { mutableStateOf(false) }

    var customBossName by remember { mutableStateOf(selectedBoss.name) }
    var isCustomBossMode by remember { mutableStateOf(false) }

    var customMainAniimoName by remember { mutableStateOf(selectedMainAniimo.name) }
    var isCustomMainAniimoMode by remember { mutableStateOf(false) }

    var customPartnerAniimoName by remember { mutableStateOf(selectedPartnerAniimo.name) }
    var isCustomPartnerAniimoMode by remember { mutableStateOf(false) }

    var customSlot3AniimoName by remember { mutableStateOf(selectedSlot3Aniimo.name) }
    var isCustomSlot3AniimoMode by remember { mutableStateOf(false) }

    var customSlot4AniimoName by remember { mutableStateOf(selectedSlot4Aniimo.name) }
    var isCustomSlot4AniimoMode by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .testTag("submit_run_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Submit Clear Time",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Submit video proof to claim leaderboard rank",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEF4444).copy(alpha = 0.2f))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFFCA5A5),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Runner Name
                OutlinedTextField(
                    value = runnerName,
                    onValueChange = { runnerName = it },
                    label = { Text("Runner Gamertag / Name") },
                    placeholder = { Text("e.g. SpeedDemon99") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_runner_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Boss Selector Dropdown (Select or Type Custom Boss / Alpha)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomBossMode) "Custom Alpha / Boss Encounter" else "Alpha Encounter",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCustomBossMode) "Switch to List" else "+ Enter Custom Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isCustomBossMode = !isCustomBossMode }
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isCustomBossMode) {
                        OutlinedTextField(
                            value = customBossName,
                            onValueChange = { customBossName = it },
                            label = { Text("Alpha / Boss Name (e.g. Alpha Solarix)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("custom_boss_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    } else {
                        ExposedDropdownMenuBox(
                            expanded = bossDropdownExpanded,
                            onExpandedChange = { bossDropdownExpanded = !bossDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedBoss.name,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bossDropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF334155)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = bossDropdownExpanded,
                                onDismissRequest = { bossDropdownExpanded = false }
                            ) {
                                bosses.forEach { boss ->
                                    DropdownMenuItem(
                                        text = { Text("${boss.name} (${boss.difficulty})") },
                                        onClick = {
                                            selectedBoss = boss
                                            customBossName = boss.name
                                            bossDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Main Aniimo Selector
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomMainAniimoMode) "Custom Main Aniimo" else "Main Striker Aniimo",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCustomMainAniimoMode) "Pick from Catalog" else "+ Custom Aniimo Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isCustomMainAniimoMode = !isCustomMainAniimoMode }
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isCustomMainAniimoMode) {
                        OutlinedTextField(
                            value = customMainAniimoName,
                            onValueChange = { customMainAniimoName = it },
                            label = { Text("Main Striker Name (e.g. Voltclaw)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("custom_main_aniimo_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    } else {
                        ExposedDropdownMenuBox(
                            expanded = mainDropdownExpanded,
                            onExpandedChange = { mainDropdownExpanded = !mainDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = "${selectedMainAniimo.name} (${selectedMainAniimo.element.displayName})",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = mainDropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF334155)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = mainDropdownExpanded,
                                onDismissRequest = { mainDropdownExpanded = false }
                            ) {
                                aniimos.forEach { aniimo ->
                                    DropdownMenuItem(
                                        text = { Text("${aniimo.name} • ${aniimo.element.displayName} (${aniimo.role})") },
                                        onClick = {
                                            selectedMainAniimo = aniimo
                                            customMainAniimoName = aniimo.name
                                            mainDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Partner Aniimo Selector (Catalog or Custom Name)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomPartnerAniimoMode) "Custom Partner Aniimo" else "Partner Aniimo",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCustomPartnerAniimoMode) "Pick from Catalog" else "+ Custom Partner Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isCustomPartnerAniimoMode = !isCustomPartnerAniimoMode }
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isCustomPartnerAniimoMode) {
                        OutlinedTextField(
                            value = customPartnerAniimoName,
                            onValueChange = { customPartnerAniimoName = it },
                            label = { Text("Partner Aniimo Name (e.g. Zephyros)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("custom_partner_aniimo_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    } else {
                        ExposedDropdownMenuBox(
                            expanded = partnerDropdownExpanded,
                            onExpandedChange = { partnerDropdownExpanded = !partnerDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = "${selectedPartnerAniimo.name} (${selectedPartnerAniimo.element.displayName})",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnerDropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF334155)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = partnerDropdownExpanded,
                                onDismissRequest = { partnerDropdownExpanded = false }
                            ) {
                                aniimos.forEach { aniimo ->
                                    DropdownMenuItem(
                                        text = { Text("${aniimo.name} • ${aniimo.element.displayName}") },
                                        onClick = {
                                            selectedPartnerAniimo = aniimo
                                            customPartnerAniimoName = aniimo.name
                                            partnerDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Squad Member #3 Selector (Catalog or Custom Name)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomSlot3AniimoMode) "Slot 3 Custom Aniimo" else "Slot 3 Squad Member",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCustomSlot3AniimoMode) "Pick from Catalog" else "+ Custom Slot 3 Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFB923C),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isCustomSlot3AniimoMode = !isCustomSlot3AniimoMode }
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isCustomSlot3AniimoMode) {
                        OutlinedTextField(
                            value = customSlot3AniimoName,
                            onValueChange = { customSlot3AniimoName = it },
                            label = { Text("Slot 3 Aniimo Name (e.g. Bouldus)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("custom_slot3_aniimo_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFFB923C),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    } else {
                        ExposedDropdownMenuBox(
                            expanded = slot3DropdownExpanded,
                            onExpandedChange = { slot3DropdownExpanded = !slot3DropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = "${selectedSlot3Aniimo.name} (${selectedSlot3Aniimo.element.displayName})",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = slot3DropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFFFB923C),
                                    unfocusedBorderColor = Color(0xFF334155)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = slot3DropdownExpanded,
                                onDismissRequest = { slot3DropdownExpanded = false }
                            ) {
                                aniimos.forEach { aniimo ->
                                    DropdownMenuItem(
                                        text = { Text("${aniimo.name} • ${aniimo.element.displayName}") },
                                        onClick = {
                                            selectedSlot3Aniimo = aniimo
                                            customSlot3AniimoName = aniimo.name
                                            slot3DropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Squad Member #4 Selector (Catalog or Custom Name)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isCustomSlot4AniimoMode) "Slot 4 Custom Aniimo" else "Slot 4 Squad Member",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isCustomSlot4AniimoMode) "Pick from Catalog" else "+ Custom Slot 4 Name",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFA78BFA),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isCustomSlot4AniimoMode = !isCustomSlot4AniimoMode }
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    if (isCustomSlot4AniimoMode) {
                        OutlinedTextField(
                            value = customSlot4AniimoName,
                            onValueChange = { customSlot4AniimoName = it },
                            label = { Text("Slot 4 Aniimo Name (e.g. Luminelle)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("custom_slot4_aniimo_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFA78BFA),
                                unfocusedBorderColor = Color(0xFF334155)
                            )
                        )
                    } else {
                        ExposedDropdownMenuBox(
                            expanded = slot4DropdownExpanded,
                            onExpandedChange = { slot4DropdownExpanded = !slot4DropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = "${selectedSlot4Aniimo.name} (${selectedSlot4Aniimo.element.displayName})",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = slot4DropdownExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFFA78BFA),
                                    unfocusedBorderColor = Color(0xFF334155)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = slot4DropdownExpanded,
                                onDismissRequest = { slot4DropdownExpanded = false }
                            ) {
                                aniimos.forEach { aniimo ->
                                    DropdownMenuItem(
                                        text = { Text("${aniimo.name} • ${aniimo.element.displayName}") },
                                        onClick = {
                                            selectedSlot4Aniimo = aniimo
                                            customSlot4AniimoName = aniimo.name
                                            slot4DropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Clear Time Inputs (MM : SS . MS)
                Text(
                    text = "Clear Time (Minutes : Seconds . Milliseconds)",
                    fontSize = 12.sp,
                    color = Color(0xFF94A3B8),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = minutesText,
                        onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) minutesText = it },
                        label = { Text("Min") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_minutes"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF334155)
                        )
                    )

                    Text(":", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

                    OutlinedTextField(
                        value = secondsText,
                        onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) secondsText = it },
                        label = { Text("Sec") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_seconds"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF334155)
                        )
                    )

                    Text(".", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

                    OutlinedTextField(
                        value = millisText,
                        onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) millisText = it },
                        label = { Text("Ms") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_millis"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF334155)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Video Proof URL & Video Duration Verification
                OutlinedTextField(
                    value = videoUrl,
                    onValueChange = { videoUrl = it },
                    label = { Text("Video Proof URL (Required)") },
                    placeholder = { Text("https://youtube.com/watch?v=...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_video_url_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = videoDurationText,
                    onValueChange = { if (it.length <= 4 && it.all { c -> c.isDigit() }) videoDurationText = it },
                    label = { Text("Video Clip Duration in Seconds (Anti-1s Fake Check)") },
                    placeholder = { Text("48") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_video_duration_sec"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                val previewClaimSec = (minutesText.toIntOrNull() ?: 0) * 60 + (secondsText.toIntOrNull() ?: 0)
                val previewClipSec = videoDurationText.toIntOrNull() ?: 0
                if (previewClipSec in 1..3 || (previewClipSec > 0 && previewClipSec < previewClaimSec - 2)) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "⚠ Warning: A ${previewClipSec}s video clip is shorter than your ${previewClaimSec}s clear time and will be flagged RED (Not Legit) by the verification system!",
                        color = Color(0xFFF87171),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 1 Held Item per Aniimo & Awakening Strategy
                OutlinedTextField(
                    value = weaponName,
                    onValueChange = { weaponName = it },
                    label = { Text("Main Striker's 1 Held Item (e.g. Ferocious Fang +15)") },
                    placeholder = { Text(selectedMainAniimo.bestItems.firstOrNull()?.name ?: "Ferocious Fang (+15)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = relicSetName,
                    onValueChange = { relicSetName = it },
                    label = { Text("Partner's 1 Held Item & Awakening (e.g. ATK > BREAK)") },
                    placeholder = { Text("Partner: Capacitous Battery (+15) • Awakening: ATK > BREAK > REGEN") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = runnerNotes,
                    onValueChange = { runnerNotes = it },
                    label = { Text("Runner Strategy & Rotation Notes") },
                    placeholder = { Text("e.g. Swapped partner at 50% HP stagger for double execute burst.") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF334155)
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Submit Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Cancel", color = Color(0xFF94A3B8))
                    }

                    Button(
                        onClick = {
                            val min = minutesText.toIntOrNull() ?: 0
                            val sec = secondsText.toIntOrNull() ?: 0
                            val ms = millisText.toIntOrNull() ?: 0
                            val clipSec = videoDurationText.toIntOrNull() ?: (min * 60 + sec + 4)
                            if (runnerName.isBlank()) {
                                errorMessage = "Please enter your runner gamertag."
                                return@Button
                            }
                            if (videoUrl.isBlank()) {
                                errorMessage = "A video proof URL is required for verification."
                                return@Button
                            }
                            if (min == 0 && sec == 0 && ms == 0) {
                                errorMessage = "Clear time must be greater than zero."
                                return@Button
                            }
                            val finalCustomBoss = if (isCustomBossMode && customBossName.isNotBlank()) customBossName.trim() else null
                            val finalCustomMain = if (isCustomMainAniimoMode && customMainAniimoName.isNotBlank()) customMainAniimoName.trim() else null
                            val finalCustomPartner = if (isCustomPartnerAniimoMode && customPartnerAniimoName.isNotBlank()) customPartnerAniimoName.trim() else null
                            val finalCustomSlot3 = if (isCustomSlot3AniimoMode && customSlot3AniimoName.isNotBlank()) customSlot3AniimoName.trim() else null
                            val finalCustomSlot4 = if (isCustomSlot4AniimoMode && customSlot4AniimoName.isNotBlank()) customSlot4AniimoName.trim() else null

                            val success = onSubmit(
                                runnerName,
                                selectedBoss.id,
                                selectedMainAniimo.id,
                                selectedPartnerAniimo.id,
                                selectedSlot3Aniimo.id,
                                selectedSlot4Aniimo.id,
                                min,
                                sec,
                                ms,
                                videoUrl,
                                weaponName,
                                relicSetName,
                                runnerNotes,
                                clipSec.coerceAtLeast(1),
                                finalCustomBoss,
                                finalCustomMain,
                                finalCustomPartner,
                                finalCustomSlot3,
                                finalCustomSlot4
                            )
                            if (success) {
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .weight(1.6f)
                            .testTag("submit_run_confirm_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8))
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Submit Run", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }
                }
            }
        }
    }
}
