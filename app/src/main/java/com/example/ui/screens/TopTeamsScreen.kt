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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import android.content.Intent
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Aniimo
import com.example.data.model.AwakeningStat
import com.example.data.model.TeamMode
import com.example.data.model.TopRatedTeam
import com.example.ui.components.AniimoFaceAvatar
import com.example.ui.components.AniimoFaceAvatarById
import com.example.ui.components.BuildShareCodec
import com.example.ui.components.SubmitShareBuildDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTeamsScreen(
    teams: List<TopRatedTeam>,
    aniimos: List<Aniimo>,
    selectedMode: TeamMode,
    selectedAniimoFilter: String?,
    searchQuery: String,
    onSelectMode: (TeamMode) -> Unit,
    onSelectAniimoFilter: (String?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onToggleUpvote: (String, Boolean) -> Unit,
    onSimulateTeam: (Aniimo, String?) -> Unit,
    onPublishTeam: ((TopRatedTeam) -> Unit)? = null,
    onSubmitSpeedrunForTeam: ((TopRatedTeam) -> Unit)? = null,
    onShowMessage: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isSubmitBuildDialogOpen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF1E293B), Color(0xFF0A0F1D))
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
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Groups,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Top Rated 4-Aniimo Teams",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "4 Aniimo Squads • 1 Held Item Each • Awakening (HP/ATK/BREAK/PDEF/MDEF/REGEN)",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF38BDF8),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF10B981))
                                    .clickable { isSubmitBuildDialogOpen = true }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                    .testTag("top_teams_submit_build_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Submit Build",
                                        tint = Color(0xFF0F172A),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Submit / Share Build",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0F172A))
                                    .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${teams.size} Squads",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Aniimo & Build Search Input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("team_search_input"),
                        placeholder = {
                            Text(
                                "Search Aniimo, Held Item, or Awakening stat (ATK, BREAK, REGEN)...",
                                color = Color(0xFF64748B),
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF94A3B8))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF94A3B8))
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF131D33),
                            unfocusedContainerColor = Color(0xFF131D33),
                            focusedBorderColor = Color(0xFF38BDF8),
                            unfocusedBorderColor = Color(0xFF1E293B)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Team Mode Filter Chips (5 Core Game Modes)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(TeamMode.values()) { mode ->
                            val isSelected = selectedMode == mode
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectMode(mode) },
                                label = { Text(mode.displayName) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(mode.badgeColorHex),
                                    selectedLabelColor = if (mode == TeamMode.ALL) Color(0xFF0F172A) else Color.White,
                                    containerColor = Color(0xFF131D33),
                                    labelColor = Color(0xFF94A3B8)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = Color(0xFF1E293B),
                                    selectedBorderColor = Color(mode.badgeColorHex)
                                ),
                                modifier = Modifier.testTag("filter_team_mode_${mode.id}")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Specific Aniimo Quick-Filter Carousel
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Aniimo:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8),
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (selectedAniimoFilter == null) Color(0xFF38BDF8).copy(alpha = 0.25f) else Color(0xFF131D33)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (selectedAniimoFilter == null) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { onSelectAniimoFilter(null) }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                        .testTag("team_filter_all_aniimos")
                                ) {
                                    Text(
                                        text = "All",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedAniimoFilter == null) Color(0xFF38BDF8) else Color(0xFFCBD5E1)
                                    )
                                }
                            }

                            items(aniimos.take(18)) { aniimo ->
                                val isSelected = selectedAniimoFilter.equals(aniimo.id, ignoreCase = true)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) aniimo.element.primaryColor.copy(alpha = 0.3f) else Color(0xFF131D33)
                                        )
                                        .border(
                                            width = if (isSelected) 1.5.dp else 1.dp,
                                            color = if (isSelected) aniimo.element.primaryColor else Color(0xFF1E293B),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            if (isSelected) onSelectAniimoFilter(null) else onSelectAniimoFilter(aniimo.id)
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                        .testTag("team_filter_aniimo_${aniimo.id}"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        AniimoFaceAvatar(
                                            aniimo = aniimo,
                                            size = 22.dp,
                                            shape = CircleShape
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = aniimo.name,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Teams List
            if (teams.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No 4-Aniimo Teams Found",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Try clearing the search query or changing the mode filter.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("top_rated_teams_list"),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(teams, key = { it.id }) { team ->
                        TopRatedTeamCard(
                            team = team,
                            allAniimos = aniimos,
                            onToggleUpvote = { onToggleUpvote(team.id, team.hasUserUpvoted) },
                            onSimulateTeam = onSimulateTeam,
                            onSubmitSpeedrunForTeam = onSubmitSpeedrunForTeam,
                            onShowMessage = onShowMessage
                        )
                    }
                }
            }
        }

        if (isSubmitBuildDialogOpen && aniimos.isNotEmpty()) {
            val lead = aniimos.find { it.id == selectedAniimoFilter } ?: aniimos.first()
            val partner = aniimos.firstOrNull { it.id != lead.id } ?: lead
            val slot3 = aniimos.getOrElse(2) { lead }
            val slot4 = aniimos.getOrElse(3) { partner }
            SubmitShareBuildDialog(
                mainAniimo = lead,
                mainHeldItem = lead.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)",
                partnerAniimo = partner,
                partnerHeldItem = partner.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)",
                slot3Aniimo = slot3,
                slot3HeldItem = slot3.bestItems.firstOrNull()?.name ?: "Bailite Crest (+15)",
                slot4Aniimo = slot4,
                slot4HeldItem = slot4.bestItems.firstOrNull()?.name ?: "Supernova Shard (+15)",
                awakeningAtk = 20,
                awakeningBreak = 16,
                awakeningRegen = 12,
                awakeningHp = 8,
                awakeningPdef = 4,
                awakeningMdef = 4,
                estimatedDps = lead.maxOptimizedDps,
                initialTab = 0,
                onDismiss = { isSubmitBuildDialogOpen = false },
                onPublishToTopTeams = { newTeam ->
                    onPublishTeam?.invoke(newTeam)
                    isSubmitBuildDialogOpen = false
                },
                onShowMessage = { msg ->
                    onShowMessage?.invoke(msg)
                }
            )
        }
    }
}

@Composable
fun TopRatedTeamCard(
    team: TopRatedTeam,
    allAniimos: List<Aniimo>,
    onToggleUpvote: () -> Unit,
    onSimulateTeam: (Aniimo, String?) -> Unit,
    onSubmitSpeedrunForTeam: ((TopRatedTeam) -> Unit)? = null,
    onShowMessage: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    // Expand both Capability (Awakening) and Point Investment Order by default so users see them immediately
    var expandedCapabilities by remember { mutableStateOf(true) }
    var expandedPointOrder by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("team_card_${team.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // Header: Mode Badge, Title & Upvote Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(team.mode.badgeColorHex))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = team.mode.shortBadge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Column {
                        Text(
                            text = team.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "4-Aniimo Squad • Lead: ${team.primaryAniimoName} • by ${team.author}",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                // Upvote Interactive Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (team.hasUserUpvoted) Color(0xFF0284C7).copy(alpha = 0.35f) else Color(0xFF0F172A)
                        )
                        .border(
                            width = 1.dp,
                            color = if (team.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF1E293B),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onToggleUpvote() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("upvote_team_button_${team.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Upvote",
                            tint = if (team.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${team.upvotes}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (team.hasUserUpvoted) Color(0xFF38BDF8) else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Rating & Metric Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F172A))
                    .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "★ Squad Benchmark:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFFD700)
                    )
                    Text(
                        text = team.estimatedDpsOrWinRate,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4-Aniimo Squad Roster & 1 Held Item Each
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "4-ANIIMO SQUAD (${team.members.size}/4) • 1 HELD ITEM EACH",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF38BDF8),
                    letterSpacing = 0.8.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                team.members.forEachIndexed { idx, member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Slot Number + In-Game Face Avatar
                        Box(contentAlignment = Alignment.BottomEnd) {
                            AniimoFaceAvatarById(
                                aniimoId = member.aniimoId,
                                aniimoName = member.aniimoName,
                                element = member.element,
                                size = 44.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF0F172A))
                                    .border(0.5.dp, Color(0xFF38BDF8), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "#${idx + 1}",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = member.aniimoName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(member.element.primaryColor.copy(alpha = 0.2f))
                                            .border(0.5.dp, member.element.primaryColor, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = member.role,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = member.element.primaryColor
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // 1 Held Item Badge
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF1E293B).copy(alpha = 0.8f))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Held Item",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Held Item: ${member.heldItem}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD700)
                                )
                            }

                            if (member.heldItemEffect.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = member.heldItemEffect,
                                    fontSize = 9.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            // Member's Awakening Stat Priority
                            Text(
                                text = "Awakening Priority: ${member.awakeningOrder}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Capability (Awakening: HP, ATK, BREAK, PDEF, MDEF, REGEN) Section
            val totalAwakeningPoints = team.capabilities.sumOf { it.costPoints }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F172A))
                    .clickable { expandedCapabilities = !expandedCapabilities }
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Capability / Awakening Stats ($totalAwakeningPoints Pts Invested)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "HP • ATK • BREAK • PDEF • MDEF • REGEN",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
                Icon(
                    imageVector = if (expandedCapabilities) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8)
                )
            }

            if (expandedCapabilities) {
                Spacer(modifier = Modifier.height(8.dp))

                // Quick 6-Stat Summary Pills (HP, ATK, BREAK, PDEF, MDEF, REGEN)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AwakeningStat.entries.forEach { stat ->
                        val cap = team.capabilities.find { it.name.equals(stat.code, ignoreCase = true) }
                        val pts = cap?.costPoints ?: 0
                        val statColor = Color(stat.colorHex)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (pts > 0) statColor.copy(alpha = 0.18f) else Color(0xFF0F172A)
                                )
                                .border(
                                    width = if (pts > 0) 1.dp else 0.5.dp,
                                    color = if (pts > 0) statColor else Color(0xFF1E293B),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stat.code,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (pts > 0) statColor else Color(0xFF64748B)
                                )
                                Text(
                                    text = "$pts Pts",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (pts > 0) Color.White else Color(0xFF475569)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Detailed Awakening Stat Rows for Invested Stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    team.capabilities.filter { it.costPoints > 0 }.forEach { cap ->
                        val statEnum = cap.awakeningStat
                        val statColor = Color(statEnum.colorHex)
                        val barFraction = (cap.costPoints / 6f).coerceIn(0f, 1f)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF1E293B).copy(alpha = 0.6f))
                                .padding(8.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(statColor.copy(alpha = 0.25f))
                                                .border(0.5.dp, statColor, RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "${cap.name} (${statEnum.fullName})",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Black,
                                                color = statColor
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = cap.tier,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFFFD700)
                                        )
                                    }

                                    Text(
                                        text = "${cap.costPoints} / 6 Pts",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                // Visual Awakening Point Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(5.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(Color(0xFF0F172A))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(barFraction)
                                            .height(5.dp)
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(statColor)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = cap.effect,
                                    fontSize = 10.sp,
                                    color = Color(0xFFCBD5E1)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Point Investment Order Section (Order Per Point Invested in HP/ATK/BREAK/PDEF/MDEF/REGEN)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F172A))
                    .clickable { expandedPointOrder = !expandedPointOrder }
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Handyman,
                        contentDescription = null,
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Awakening Point Investment Order (${team.pointInvestmentOrder.size} Steps)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Icon(
                    imageVector = if (expandedPointOrder) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8)
                )
            }

            if (expandedPointOrder) {
                Spacer(modifier = Modifier.height(6.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    team.pointInvestmentOrder.forEach { step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF1E293B).copy(alpha = 0.6f))
                                .padding(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF10B981).copy(alpha = 0.2f))
                                    .border(0.5.dp, Color(0xFF10B981), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = step.pointRange,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF10B981)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = step.capabilityOrSkill,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = step.priorityLevel,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                Text(
                                    text = step.rationale,
                                    fontSize = 10.sp,
                                    color = Color(0xFFCBD5E1)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Strategy Guide
            Text(
                text = "Squad Rotation: ${team.strategyGuide}",
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons: Simulate, Share Build, and Submit Speedrun
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF38BDF8).copy(alpha = 0.15f))
                    .border(1.dp, Color(0xFF38BDF8), RoundedCornerShape(8.dp))
                    .clickable {
                        val mainAniimo = allAniimos.find { it.id == team.primaryAniimoId } ?: allAniimos.first()
                        val partnerId = team.members.firstOrNull { it.aniimoId != mainAniimo.id }?.aniimoId
                        onSimulateTeam(mainAniimo, partnerId)
                    }
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Load 4-Aniimo Squad into My Builds",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38BDF8)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Share Build Card & Code Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF10B981).copy(alpha = 0.16f))
                    .border(1.dp, Color(0xFF10B981), RoundedCornerShape(8.dp))
                    .clickable {
                        val shareText = BuildShareCodec.formatTeamShareCard(team)
                        clipboardManager.setText(AnnotatedString(shareText))
                        onShowMessage?.invoke("Copied '${team.title}' 4-Aniimo build card to clipboard!")
                        runCatching {
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Aniimo 4-Unit Team: ${team.title}")
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share 4-Aniimo Build"))
                        }
                    }
                    .padding(vertical = 9.dp)
                    .testTag("share_team_button_${team.id}"),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Build",
                    tint = Color(0xFF34D399),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Share Build",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF34D399)
                )
            }
        }
    }
}
