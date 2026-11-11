package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val title: String, val icon: ImageVector) {
    ROSTER("Aniimos", Icons.Default.Pets),
    TOP_TEAMS("Top Teams", Icons.Default.Groups),
    SPARKLES("Sparkles", Icons.Default.AutoAwesome),
    DPS_CALCULATOR("My Builds", Icons.Default.Calculate),
    DISCUSSION("Board", Icons.Default.Forum),
    LEADERBOARD("Leaderboard", Icons.Default.EmojiEvents),
    SAVED_BUILDS("Saved", Icons.Default.Bookmark),
    ANIIMO_DETAIL("Build Guide", Icons.Default.Pets),
    PATCH_NOTES("Patch Notes", Icons.Default.AutoAwesome),
    SETTINGS("Settings", Icons.Default.Settings)
}
