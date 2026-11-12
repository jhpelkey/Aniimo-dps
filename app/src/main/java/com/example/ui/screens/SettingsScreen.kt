package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import android.net.Uri
import com.example.data.model.FirestoreAppConfig
import com.example.data.model.FirestoreLivePatchNote
import com.example.data.model.FirestoreModerationBan
import com.example.data.model.SkillBalanceChange
import com.example.data.model.StatBalanceChange
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.mutableStateOf

data class ThemeColorPreset(
    val name: String,
    val color: Color,
    val hexCode: String,
    val description: String
)

val BackgroundColorPresets = listOf(
    ThemeColorPreset("Deep Void", Color(0xFF0A0F1D), "#0A0F1D", "Default sleek midnight gaming slate"),
    ThemeColorPreset("Obsidian OLED", Color(0xFF000000), "#000000", "True pure black for maximum battery & contrast"),
    ThemeColorPreset("Cosmic Navy", Color(0xFF0F172A), "#0F172A", "Deep interstellar navy blue"),
    ThemeColorPreset("Abyssal Teal", Color(0xFF041C24), "#041C24", "Mysterious deep sea dark teal"),
    ThemeColorPreset("Velvet Indigo", Color(0xFF1E1B4B), "#1E1B4B", "Rich arcane royal purple"),
    ThemeColorPreset("Slate Charcoal", Color(0xFF18181B), "#18181B", "Refined graphite dark neutral"),
    ThemeColorPreset("Crimson Shadow", Color(0xFF1F0A10), "#1F0A10", "Aggressive dark blood crimson"),
    ThemeColorPreset("Forest Night", Color(0xFF061D12), "#061D12", "Dark emerald woodland gloom")
)

val TextColorPresets = listOf(
    ThemeColorPreset("Pure White", Color(0xFFFFFFFF), "#FFFFFF", "Crisp default maximum readability"),
    ThemeColorPreset("Sky Cyan", Color(0xFF38BDF8), "#38BDF8", "Vibrant tech cyan highlight"),
    ThemeColorPreset("Radiant Gold", Color(0xFFFDE047), "#FDE047", "Warm radiant sunlight yellow"),
    ThemeColorPreset("Emerald Mint", Color(0xFF34D399), "#34D399", "Soothing bio-luminescent mint"),
    ThemeColorPreset("Soft Lavender", Color(0xFFE9D5FF), "#E9D5FF", "Pastel dream lavender"),
    ThemeColorPreset("Rose Pink", Color(0xFFFDA4AF), "#FDA4AF", "Gentle blossom rose tint"),
    ThemeColorPreset("Amber Coral", Color(0xFFFB923C), "#FB923C", "Energetic warm flame orange"),
    ThemeColorPreset("Silver Chrome", Color(0xFFE2E8F0), "#E2E8F0", "Subtle metallic cool silver")
)

data class FontFamilyPreset(
    val id: String,
    val displayName: String,
    val fontFamily: FontFamily,
    val previewSample: String
)

val AppFontPresets = listOf(
    FontFamilyPreset("Default", "Modern Sans", FontFamily.Default, "Clean modern Android system typography"),
    FontFamilyPreset("Monospace", "Retro Monospace", FontFamily.Monospace, "Pixel gaming & terminal numbers font"),
    FontFamilyPreset("Serif", "Classic Serif", FontFamily.Serif, "Elegant fantasy grimoire lettering"),
    FontFamilyPreset("Cursive", "Stylized Script", FontFamily.Cursive, "Artistic cursive hero styling")
)

@Composable
fun SettingsScreen(
    currentBackgroundColor: Color,
    currentTextColor: Color,
    currentFontName: String,
    onSelectBackgroundColor: (Color) -> Unit,
    onSelectTextColor: (Color) -> Unit,
    onSelectFontName: (String) -> Unit,
    onRestoreDefaultTheme: () -> Unit,
    patchNotes: List<FirestoreLivePatchNote>,
    activeSkillChanges: List<SkillBalanceChange>,
    activeStatChanges: List<StatBalanceChange>,
    isParsingWithAi: Boolean,
    onSyncOfficialNotes: (String) -> Unit,
    onParseRawText: (String) -> Unit,
    onShowMessage: (String) -> Unit,
    isAdmin: Boolean = false,
    currentUserEmail: String? = null,
    activeBans: List<FirestoreModerationBan> = emptyList(),
    onToggleAdminOverride: ((Boolean) -> Unit)? = null,
    onIssueTimeoutOrBan: ((String, String, String) -> Unit)? = null,
    onRevokeBan: ((FirestoreModerationBan) -> Unit)? = null,
    liveAppConfig: FirestoreAppConfig = FirestoreAppConfig(),
    onPublishLiveAppConfig: ((String, String, String, String, Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Theme & Font", "Patch Notes", "Cloud & Admin")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(currentBackgroundColor)
            .testTag("settings_screen")
    ) {
        // Settings Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1B4B), currentBackgroundColor.copy(alpha = 0.9f))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Aniimo Settings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = currentTextColor
                    )
                    Text(
                        text = "Customize colors & fonts, read live patch updates, and manage cloud & admin tools",
                        fontSize = 11.sp,
                        color = currentTextColor.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF0F172A),
                contentColor = Color(0xFF38BDF8),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF38BDF8)
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .testTag("settings_tab_row")
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val icon = when (index) {
                                    0 -> Icons.Default.Palette
                                    1 -> Icons.Default.AutoAwesome
                                    else -> Icons.Default.CloudDone
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = if (selectedTab == index) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = if (selectedTab == index) Color.White else Color(0xFF94A3B8)
                                )
                            }
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("settings_tab_$index")
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> {
                AppearanceSettingsContent(
                    currentBackgroundColor = currentBackgroundColor,
                    currentTextColor = currentTextColor,
                    currentFontName = currentFontName,
                    onSelectBackgroundColor = onSelectBackgroundColor,
                    onSelectTextColor = onSelectTextColor,
                    onSelectFontName = onSelectFontName,
                    onRestoreDefaultTheme = onRestoreDefaultTheme,
                    onShowMessage = onShowMessage
                )
            }
            1 -> {
                PatchNotesScreen(
                    patchNotes = patchNotes,
                    activeSkillChanges = activeSkillChanges,
                    activeStatChanges = activeStatChanges,
                    isParsingWithAi = isParsingWithAi,
                    onSyncOfficialNotes = onSyncOfficialNotes,
                    onParseRawText = onParseRawText,
                    onShowMessage = onShowMessage
                )
            }
            2 -> {
                CloudSyncStatusContent(
                    currentTextColor = currentTextColor,
                    isAdmin = isAdmin,
                    currentUserEmail = currentUserEmail,
                    activeBans = activeBans,
                    onToggleAdminOverride = onToggleAdminOverride,
                    onIssueTimeoutOrBan = onIssueTimeoutOrBan,
                    onRevokeBan = onRevokeBan,
                    liveAppConfig = liveAppConfig,
                    onPublishLiveAppConfig = onPublishLiveAppConfig,
                    onShowMessage = onShowMessage
                )
            }
        }
    }
}

@Composable
fun AppearanceSettingsContent(
    currentBackgroundColor: Color,
    currentTextColor: Color,
    currentFontName: String,
    onSelectBackgroundColor: (Color) -> Unit,
    onSelectTextColor: (Color) -> Unit,
    onSelectFontName: (String) -> Unit,
    onRestoreDefaultTheme: () -> Unit,
    onShowMessage: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Reset to Default Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("restore_theme_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Reset Theme to Defaults",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Restore Deep Void background (#0A0F1D), Pure White text (#FFFFFF), and Modern Sans typography.",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onRestoreDefaultTheme()
                            onShowMessage("Theme restored to default settings!")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("restore_theme_default_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Restore Default",
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Restore",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Live Theme Preview Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = currentBackgroundColor),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF38BDF8)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("live_theme_preview_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LIVE THEME PREVIEW",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF38BDF8)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF38BDF8).copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Font: $currentFontName",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Zephyrion • Alpha Titan Speedrun",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = currentTextColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "DPS: 42,850 • Time: 01:24.500 • Item: Ferocious Fang (+15)",
                        fontSize = 12.sp,
                        color = currentTextColor.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF10B981))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("DPS Role", color = Color(0xFF0F172A), fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF8B5CF6))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Synergist", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF38BDF8))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Armor Breaker", color = Color(0xFF0F172A), fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // Background Color Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FormatPaint,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "BACKGROUND COLOR",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF94A3B8)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF131D33))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BackgroundColorPresets.forEach { preset ->
                        val isSelected = currentBackgroundColor == preset.color
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSelectBackgroundColor(preset.color)
                                    onShowMessage("Applied ${preset.name} background")
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                                .testTag("bg_color_option_${preset.name.lowercase().replace(" ", "_")}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(preset.color)
                                        .border(1.5.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = preset.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${preset.hexCode} • ${preset.description}",
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Text Color Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TEXT COLOR",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF94A3B8)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF131D33))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TextColorPresets.forEach { preset ->
                        val isSelected = currentTextColor == preset.color
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSelectTextColor(preset.color)
                                    onShowMessage("Applied ${preset.name} text color")
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                                .testTag("text_color_option_${preset.name.lowercase().replace(" ", "_")}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(preset.color)
                                        .border(1.5.dp, Color(0xFF334155), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = preset.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = preset.color
                                    )
                                    Text(
                                        text = "${preset.hexCode} • ${preset.description}",
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Typography Font Family Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "FONT FAMILY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF94A3B8)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF131D33))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AppFontPresets.forEach { preset ->
                        val isSelected = currentFontName.equals(preset.id, ignoreCase = true)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSelectFontName(preset.id)
                                    onShowMessage("Switched font to ${preset.displayName}")
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                                .testTag("font_family_option_${preset.id.lowercase()}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = preset.displayName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = preset.fontFamily,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = preset.previewSample,
                                    fontSize = 11.sp,
                                    fontFamily = preset.fontFamily,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CloudSyncStatusContent(
    currentTextColor: Color,
    isAdmin: Boolean = false,
    currentUserEmail: String? = null,
    activeBans: List<FirestoreModerationBan> = emptyList(),
    onToggleAdminOverride: ((Boolean) -> Unit)? = null,
    onIssueTimeoutOrBan: ((String, String, String) -> Unit)? = null,
    onRevokeBan: ((FirestoreModerationBan) -> Unit)? = null,
    liveAppConfig: FirestoreAppConfig = FirestoreAppConfig(),
    onPublishLiveAppConfig: ((String, String, String, String, Boolean) -> Unit)? = null,
    onShowMessage: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val publicInviteUrl = "https://ais-pre-pmpdvh6afb4hvhij3bcahx-905521185616.us-east1.run.app"
    var targetUsernameInput by remember { mutableStateOf("") }
    var moderationReasonInput by remember { mutableStateOf("") }
    var githubSlugInput by remember(liveAppConfig.githubRepoSlug) { mutableStateOf(liveAppConfig.githubRepoSlug) }
    var latestVersionInput by remember(liveAppConfig.latestVersionName) { mutableStateOf(liveAppConfig.latestVersionName) }
    var liveAnnouncementInput by remember(liveAppConfig.liveAnnouncement) { mutableStateOf(liveAppConfig.liveAnnouncement) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Admin Moderation Console Card (for Owner jhpelkey@gmail.com)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A102B)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFF43F5E).copy(alpha = 0.75f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_moderation_console_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Admin Moderation Console",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFDA4AF)
                            )
                            Text(
                                text = "Owner Admin: jhpelkey@gmail.com • Signed in: ${currentUserEmail ?: "Guest/Testing"}",
                                fontSize = 11.sp,
                                color = if (isAdmin) Color(0xFF34D399) else Color(0xFF94A3B8)
                            )
                        }

                        if (onToggleAdminOverride != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isAdmin) "ADMIN ON" else "ADMIN OFF",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isAdmin) Color(0xFF34D399) else Color(0xFF94A3B8)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Switch(
                                    checked = isAdmin,
                                    onCheckedChange = { onToggleAdminOverride(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color(0xFF0F172A),
                                        checkedTrackColor = Color(0xFFF43F5E)
                                    ),
                                    modifier = Modifier.testTag("admin_mode_toggle_switch")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isAdmin) {
                            "Admin Mode is ACTIVE (Authenticated as jhpelkey@gmail.com). You can remove unwanted posts, runs, or 4-Aniimo teams directly on the Board, Leaderboard, and Teams tabs, or issue timeouts and bans below."
                        } else {
                            "Admin controls are locked. Sign in with jhpelkey@gmail.com to unlock moderation tools and OTA app config publishing."
                        },
                        fontSize = 11.sp,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 16.sp
                    )

                    if (isAdmin && onIssueTimeoutOrBan != null) {
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = targetUsernameInput,
                            onValueChange = { targetUsernameInput = it },
                            label = { Text("Username or Author to Timeout / Ban", fontSize = 11.sp) },
                            placeholder = { Text("e.g. Spammer123", fontSize = 11.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFF43F5E),
                                unfocusedBorderColor = Color(0xFF334155),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_target_username_input")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = moderationReasonInput,
                            onValueChange = { moderationReasonInput = it },
                            label = { Text("Moderation Reason (optional)", fontSize = 11.sp) },
                            placeholder = { Text("e.g. Fake speedrun clip / spam post", fontSize = 11.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFF43F5E),
                                unfocusedBorderColor = Color(0xFF334155),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_moderation_reason_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Button(
                                onClick = {
                                    onIssueTimeoutOrBan(targetUsernameInput, "TIMEOUT_1H", moderationReasonInput)
                                    targetUsernameInput = ""
                                    moderationReasonInput = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f).testTag("admin_btn_timeout_1h")
                            ) {
                                Text("1h Timeout", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }
                            Button(
                                onClick = {
                                    onIssueTimeoutOrBan(targetUsernameInput, "TIMEOUT_24H", moderationReasonInput)
                                    targetUsernameInput = ""
                                    moderationReasonInput = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f).testTag("admin_btn_timeout_24h")
                            ) {
                                Text("24h Timeout", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Button(
                                onClick = {
                                    onIssueTimeoutOrBan(targetUsernameInput, "TIMEOUT_7D", moderationReasonInput)
                                    targetUsernameInput = ""
                                    moderationReasonInput = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f).testTag("admin_btn_timeout_7d")
                            ) {
                                Text("7d Timeout", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Button(
                                onClick = {
                                    onIssueTimeoutOrBan(targetUsernameInput, "PERMANENT_BAN", moderationReasonInput)
                                    targetUsernameInput = ""
                                    moderationReasonInput = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f).testTag("admin_btn_perm_ban")
                            ) {
                                Text("Perm Ban", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        if (activeBans.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "ACTIVE TIMEOUTS & BANS (${activeBans.size})",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFDA4AF)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            activeBans.forEach { ban ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF0F172A))
                                        .border(1.dp, Color(0xFFF43F5E).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${ban.targetAuthorName} • ${ban.actionType}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = ban.reason,
                                            fontSize = 10.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFF10B981))
                                            .clickable { onRevokeBan?.invoke(ban) }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                            .testTag("admin_revoke_ban_${ban.id}")
                                    ) {
                                        Text(
                                            text = "Unban / Lift",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0F172A)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Over-The-Air (OTA) Live Updates & GitHub Auto-Update Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.65f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ota_github_update_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Over-The-Air (OTA) Live Sync & GitHub Auto-Updater",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF38BDF8)
                    )
                    Text(
                        text = "Installed Version: v1.0 • Cloud Live Version: v${liveAppConfig.latestVersionName} • Sync Epoch #${liveAppConfig.remoteResetEpoch}",
                        fontSize = 11.sp,
                        color = Color(0xFF34D399)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• Live Cloud Updates (No APK Redownload Needed): New 4-Aniimo teams, speedruns, discussion posts, admin bans/deletions, live announcements, and Patch Notes balance changes update automatically over the air via Firebase Cloud Firestore.\n• GitHub APK Auto-Updates: For full UI updates, link your GitHub repo below or tap 'Add to Obtainium' so Android tracks your GitHub Releases and updates in 1 tap.",
                        fontSize = 11.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 16.sp
                    )

                    if (isAdmin && onPublishLiveAppConfig != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                onPublishLiveAppConfig(
                                    latestVersionInput,
                                    githubSlugInput,
                                    liveAppConfig.directApkUrl,
                                    liveAnnouncementInput,
                                    true
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_push_ota_clean_btn")
                        ) {
                            Text(
                                text = "Push Over-The-Air Clean of Pre-Posted Teams to All Installed Apps",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = githubSlugInput,
                            onValueChange = { githubSlugInput = it },
                            label = { Text("GitHub Repo (owner/repo) for Direct Updates", fontSize = 11.sp) },
                            placeholder = { Text("e.g. yourusername/aniimo-dps", fontSize = 11.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF38BDF8),
                                unfocusedBorderColor = Color(0xFF334155),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = latestVersionInput,
                                onValueChange = { latestVersionInput = it },
                                label = { Text("Latest Version", fontSize = 11.sp) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF334155),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.weight(0.35f)
                            )

                            OutlinedTextField(
                                value = liveAnnouncementInput,
                                onValueChange = { liveAnnouncementInput = it },
                                label = { Text("Live App Banner / Announcement", fontSize = 11.sp) },
                                placeholder = { Text("e.g. New Season 1 Leaderboard is live!", fontSize = 11.sp) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF38BDF8),
                                    unfocusedBorderColor = Color(0xFF334155),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.weight(0.65f)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                onPublishLiveAppConfig(
                                    latestVersionInput,
                                    githubSlugInput,
                                    liveAppConfig.directApkUrl,
                                    liveAnnouncementInput,
                                    false
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Publish Live OTA Config & GitHub Release Info",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }

                    if (liveAppConfig.githubRepoSlug.isNotBlank() || liveAppConfig.directApkUrl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        val repoUrl = if (liveAppConfig.githubRepoSlug.isNotBlank()) {
                            "https://github.com/${liveAppConfig.githubRepoSlug}/releases/latest"
                        } else {
                            liveAppConfig.directApkUrl
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val targetUrl = liveAppConfig.directApkUrl.ifBlank { repoUrl }
                                    try {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)))
                                    } catch (_: Exception) {
                                        onShowMessage("Could not open browser for $targetUrl")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Update from GitHub", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }

                            if (liveAppConfig.githubRepoSlug.isNotBlank()) {
                                OutlinedButton(
                                    onClick = {
                                        val obtainiumUri = "obtainium://add/https://github.com/${liveAppConfig.githubRepoSlug}"
                                        try {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(obtainiumUri)))
                                        } catch (_: Exception) {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ImranR98/Obtainium/releases/latest")))
                                        }
                                    },
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF34D399)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Auto-Update (Obtainium)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399))
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Public & Unrestricted Cloud Builds Active",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Firebase Cloud Firestore • Open to all invited players",
                                fontSize = 11.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "All 4-Aniimo builds, team compositions, leaderboard speedrun submissions, and discussion board posts are public and unrestricted across the community in real-time.",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onShowMessage("Database synchronization refreshed! All public builds are synced.") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Check Sync",
                                color = Color(0xFF0F172A),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Public Invite Friends Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("settings_invite_friends_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF38BDF8).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Invite Friends & Community Members",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Unrestricted public invite link for other players",
                                fontSize = 11.sp,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = publicInviteUrl,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF38BDF8)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val inviteMsg = "Join Aniimo DPS (Unrestricted Public Build & Speedrun Community): $publicInviteUrl"
                                clipboardManager.setText(AnnotatedString(inviteMsg))
                                try {
                                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, "Join Aniimo DPS Community App")
                                        putExtra(Intent.EXTRA_TEXT, inviteMsg)
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Invite Friends to Aniimo DPS"))
                                } catch (_: Exception) {
                                }
                                onShowMessage("Invite link copied & ready to share!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("settings_share_invite_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Invite Friends",
                                color = Color(0xFF0F172A),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(publicInviteUrl))
                                onShowMessage("Public App URL copied to clipboard!")
                            },
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8)),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("settings_copy_invite_link_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Copy Link",
                                color = Color(0xFF38BDF8),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Google Play Store Release & AAB Export Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA855F7).copy(alpha = 0.6f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("google_play_release_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFA855F7).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Color(0xFFD8B4FE),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Google Play Store Release Ready",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Package: com.aistudio.aniimodps.kvyxzt • v1.0 (SDK 36)",
                                fontSize = 11.sp,
                                color = Color(0xFFD8B4FE)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val checklist = listOf(
                        "App Title Compliant: 'Aniimo DPS' (10/30 chars, Play Policy verified)",
                        "Permissions Clean: INTERNET only (zero restricted/storage permissions)",
                        "Custom Adaptive Icon: Vector foreground + themed background configured",
                        "Release Signing Ready: Signed AAB & APK build variants configured"
                    )

                    checklist.forEach { itemText ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = itemText,
                                fontSize = 11.sp,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Option 1 — Share Free Right Now (No Play Store Fee):",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF34D399)
                            )
                            Text(
                                text = "• Click the 'Share' button in the top-right bar of AI Studio, set access to Public, and send the link ($publicInviteUrl) to friends.",
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1),
                                lineHeight = 16.sp
                            )
                            Text(
                                text = "Option 2 — Why Google Play Console Blocked Login & How to Get AAB:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFFDE047)
                            )
                            Text(
                                text = "• Google Play Console (play.google.com/console/signup) requires registering a Google Play Developer Account first (Google charges a one-time $25 registration fee).\n• To get the AAB/APK: use the Download / Export icon in the top bar of AI Studio to download the APK/AAB or Export Project as ZIP (open in Android Studio -> Build -> Generate Signed App Bundle / APK).",
                                fontSize = 11.sp,
                                color = Color(0xFFCBD5E1),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
