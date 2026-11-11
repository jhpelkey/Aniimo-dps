package com.example.ui.screens

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BossEncounter
import com.example.data.model.LeaderboardEntry
import com.example.ui.components.LeaderboardCard
import com.example.viewmodel.LeaderboardSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    bosses: List<BossEncounter>,
    selectedBossId: String?,
    entries: List<LeaderboardEntry>,
    currentSort: LeaderboardSort,
    onSelectBoss: (String?) -> Unit,
    onSelectSort: (LeaderboardSort) -> Unit,
    onWatchProof: (LeaderboardEntry) -> Unit,
    onUpvote: (LeaderboardEntry) -> Unit,
    onSubmitClick: () -> Unit,
    showOnlyLegitEntries: Boolean = false,
    onToggleShowOnlyLegit: (Boolean) -> Unit = {},
    onVoteLegit: ((LeaderboardEntry, Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredEntries = remember(entries, searchQuery) {
        if (searchQuery.isBlank()) {
            entries
        } else {
            val q = searchQuery.trim().lowercase()
            entries.filter { entry ->
                entry.runnerName.lowercase().contains(q) ||
                entry.bossName.lowercase().contains(q) ||
                entry.mainAniimoName.lowercase().contains(q) ||
                entry.partnerAniimoName.lowercase().contains(q) ||
                entry.slot3AniimoName.lowercase().contains(q) ||
                entry.slot4AniimoName.lowercase().contains(q) ||
                entry.runnerNotes.lowercase().contains(q) ||
                entry.weaponEquipped.lowercase().contains(q) ||
                entry.relicSetEquipped.lowercase().contains(q)
            }
        }
    }

    val legitCount = filteredEntries.count { it.isLegitCommunityVerified }
    val flaggedCount = filteredEntries.count { !it.isLegitCommunityVerified }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
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
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(26.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Clear Time Records",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "Community Proof Verification • Legit (Green) vs Not Legit (Red)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF38BDF8),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Show Only Legit Entries Filter Toggle Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (showOnlyLegitEntries) Color(0xFF10B981).copy(alpha = 0.18f)
                                else Color(0xFF131D33)
                            )
                            .border(
                                width = 1.dp,
                                color = if (showOnlyLegitEntries) Color(0xFF10B981) else Color(0xFF1E293B),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onToggleShowOnlyLegit(!showOnlyLegitEntries) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("filter_only_legit_entries"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Show Only Legit Entries",
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Show Only Legit Entries",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (showOnlyLegitEntries) Color(0xFF34D399) else Color.White
                                )
                                Text(
                                    text = if (showOnlyLegitEntries) {
                                        "Hiding suspicious 1s clips & red-flagged claims ($legitCount Legit shown)"
                                    } else {
                                        "$legitCount Green (Legit) • $flaggedCount Red (Flagged / 1s Video)"
                                    },
                                    fontSize = 10.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }

                        Switch(
                            checked = showOnlyLegitEntries,
                            onCheckedChange = { onToggleShowOnlyLegit(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF0F172A),
                                checkedTrackColor = Color(0xFF10B981),
                                uncheckedThumbColor = Color(0xFF94A3B8),
                                uncheckedTrackColor = Color(0xFF0F172A)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Leaderboard Search Bar (Search by runner name, Alpha boss, or Aniimo)
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("leaderboard_search_input"),
                        placeholder = {
                            Text(
                                text = "Search runner name, Alpha boss, or Aniimo...",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Runs",
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear search",
                                        tint = Color(0xFF94A3B8),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF1E293B),
                            focusedContainerColor = Color(0xFF0F172A),
                            unfocusedContainerColor = Color(0xFF0F172A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Boss Filter Chips
                    Text(
                        text = "Filter by Boss Raid / Dungeon",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            FilterChip(
                                selected = selectedBossId == null,
                                onClick = { onSelectBoss(null) },
                                label = { Text("All Bosses") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF38BDF8),
                                    selectedLabelColor = Color(0xFF0F172A),
                                    containerColor = Color(0xFF131D33),
                                    labelColor = Color(0xFF94A3B8)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedBossId == null,
                                    borderColor = Color(0xFF1E293B),
                                    selectedBorderColor = Color(0xFF38BDF8)
                                ),
                                modifier = Modifier.testTag("boss_filter_all")
                            )
                        }

                        items(bosses) { boss ->
                            FilterChip(
                                selected = selectedBossId == boss.id,
                                onClick = { onSelectBoss(boss.id) },
                                label = { Text("${boss.name.take(15)}...") },
                                leadingIcon = {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(boss.elementWeakness.primaryColor)
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF1E293B),
                                    selectedLabelColor = Color(0xFF38BDF8),
                                    containerColor = Color(0xFF131D33),
                                    labelColor = Color(0xFF94A3B8)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedBossId == boss.id,
                                    borderColor = Color(0xFF1E293B),
                                    selectedBorderColor = Color(0xFF38BDF8)
                                ),
                                modifier = Modifier.testTag("boss_filter_${boss.id}")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sort Tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${filteredEntries.size} Displayed Runs",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.Medium
                            )
                            if (searchQuery.isNotBlank()) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFF38BDF8).copy(alpha = 0.2f))
                                        .border(1.dp, Color(0xFF38BDF8), RoundedCornerShape(6.dp))
                                        .clickable { searchQuery = "" }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Filter: '$searchQuery' ✕",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            LeaderboardSort.values().forEach { sort ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            if (currentSort == sort) Color(0xFF38BDF8).copy(alpha = 0.2f)
                                            else Color(0xFF131D33)
                                        )
                                        .border(
                                            1.dp,
                                            if (currentSort == sort) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { onSelectSort(sort) }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                        .testTag("sort_${sort.name.lowercase()}")
                                ) {
                                    Text(
                                        text = sort.label,
                                        fontSize = 10.sp,
                                        fontWeight = if (currentSort == sort) FontWeight.Bold else FontWeight.Normal,
                                        color = if (currentSort == sort) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Entries List
            if (filteredEntries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.VideoLibrary,
                            contentDescription = null,
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(52.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (searchQuery.isNotBlank()) "No runs matching \"$searchQuery\"" else "No clear times match your filter",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF94A3B8)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (searchQuery.isNotBlank()) "Try searching for a different runner, boss name, or Aniimo character." else "Submit a full video proof run or adjust your filter.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        if (searchQuery.isNotBlank()) {
                            Button(
                                onClick = { searchQuery = "" },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8))
                            ) {
                                Text("Clear Search", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = onSubmitClick,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8))
                            ) {
                                Text("Submit Your Clear Time", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Dedicated "Create Submission Spot" Banner
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("leaderboard_create_submission_banner"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.45f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                            .size(42.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFF38BDF8).copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.EmojiEvents,
                                            contentDescription = "Submit Run",
                                            tint = Color(0xFF38BDF8),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Submit Speedrun Record",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Video proof required • All Alphas & Custom Aniimos accepted",
                                            fontSize = 11.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = onSubmitClick,
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                                    modifier = Modifier.testTag("create_submission_spot_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = Color(0xFF0F172A),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Submit Run",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                            }
                        }
                    }

                    itemsIndexed(filteredEntries, key = { _, entry -> entry.id }) { index, entry ->
                        LeaderboardCard(
                            rank = index + 1,
                            entry = entry,
                            onWatchProof = onWatchProof,
                            onUpvote = onUpvote,
                            onVoteLegit = onVoteLegit
                        )
                    }
                }
            }
        }

        // Quick Access Floating Action Button for Submissions
        FloatingActionButton(
            onClick = onSubmitClick,
            containerColor = Color(0xFF38BDF8),
            contentColor = Color(0xFF0F172A),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
                .testTag("leaderboard_floating_submit_button")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Run Submission",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Submit Run",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
