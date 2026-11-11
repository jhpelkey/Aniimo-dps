package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import com.example.data.model.EvolutionStage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Aniimo
import com.example.data.model.PartnerSynergy
import com.example.data.model.SparkleType
import com.example.data.repository.SparkleRepository
import com.example.ui.components.AniimoElementBadge
import com.example.ui.components.AniimoFaceAvatar
import com.example.ui.components.SparkleAniimoAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniimoDetailScreen(
    aniimo: Aniimo,
    allAniimos: List<Aniimo> = emptyList(),
    onBack: () -> Unit,
    onNavigateToSimulator: (Aniimo, String?) -> Unit,
    onOpenSparkleShowcase: ((Aniimo) -> Unit)? = null,
    onSelectAniimo: ((Aniimo) -> Unit)? = null,
    communityVote: com.example.data.local.CommunityTierVoteEntity? = null,
    onVoteTier: ((String) -> Unit)? = null,
    topRatedTeams: List<com.example.data.model.TopRatedTeam> = emptyList(),
    onToggleTeamUpvote: ((String, Boolean) -> Unit)? = null,
    discussionPosts: List<com.example.data.model.DiscussionPost> = emptyList(),
    onSubmitDiscussionPost: ((
        authorName: String,
        category: com.example.data.model.DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String,
        recommendedAwakening: String
    ) -> Unit)? = null,
    onToggleDiscussionUpvote: ((Int) -> Unit)? = null,
    onSubmitDiscussionReply: ((Int, String, String) -> Unit)? = null,
    onPublishBuild: ((com.example.data.model.TopRatedTeam) -> Unit)? = null,
    onSubmitSpeedrun: ((Aniimo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val regionalVariants = remember(aniimo.id) {
        SparkleRepository.getRegionalVariantsForAniimo(aniimo)
    }
    var selectedRegionalVariant by remember(aniimo.id) {
        mutableStateOf(regionalVariants.first())
    }
    var showBuildShareModal by remember { mutableStateOf(false) }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Skills & DPS", "Top Teams", "Best Partners", "1 Held Item", "Awakening & Rotation", "Forms (${regionalVariants.size})", "Discussion")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("back_button_aniimo_detail")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Roster",
                    tint = Color.White
                )
            }
            Text(
                text = "${aniimo.name} Build Guide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onNavigateToSimulator(aniimo, null) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                modifier = Modifier.testTag("simulate_aniimo_header_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color(0xFF0F172A),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("My Builds", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            // Header Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                selectedRegionalVariant.primaryTint.copy(alpha = 0.45f),
                                Color(0xFF0A0F1D)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // High-res Face Model Portrait (with active Regional Variant coat)
                        AniimoFaceAvatar(
                            aniimo = aniimo,
                            size = 72.dp,
                            shape = RoundedCornerShape(16.dp),
                            regionalVariant = selectedRegionalVariant
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (selectedRegionalVariant.isNativeDefault) aniimo.name else selectedRegionalVariant.variantName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                val totalVotes = communityVote?.totalVotes ?: 0
                                val displayTier = if (totalVotes > 0 && communityVote != null) communityVote.communityTier else aniimo.metaTier

                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            when (displayTier) {
                                                "S+" -> Color(0xFFFF453A)
                                                "S" -> Color(0xFFFF9F0A)
                                                "A" -> Color(0xFF10B981)
                                                "B+" -> Color(0xFF3B82F6)
                                                "B" -> Color(0xFF6366F1)
                                                else -> Color(0xFF64748B)
                                            }
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                        .testTag("detail_hero_tier_badge")
                                ) {
                                    Text(
                                        text = if (totalVotes > 0) "★ $displayTier Tier • Community" else "$displayTier Tier",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                            Text(
                                text = "${aniimo.title} • ${selectedRegionalVariant.shortBadge}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF94A3B8)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AniimoElementBadge(element = selectedRegionalVariant.primaryElement, compact = true)
                                selectedRegionalVariant.secondaryElement?.let { secElem ->
                                    if (secElem != selectedRegionalVariant.primaryElement) {
                                        AniimoElementBadge(element = secElem, compact = true)
                                    }
                                }
                                Text(
                                    text = aniimo.role,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = aniimo.overview,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Base Stats & DPS metrics row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF131D33))
                            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatMetric(label = "Base ATK", value = "${aniimo.baseAtk}")
                        StatMetric(label = "Crit Rate", value = "${aniimo.critRate}%", color = Color(0xFFFFD60A))
                        StatMetric(label = "Crit DMG", value = "${aniimo.critDmg}%", color = Color(0xFFFF9F0A))
                        StatMetric(label = "Max DPS", value = "${aniimo.maxOptimizedDps / 1000}k", color = Color(0xFFFF453A))
                    }
                }
            }

            // Interactive Regional Variants (Breezy Plains, Whisperwake Isles, Prismana, Umbral)
            RegionalVariantsSection(
                aniimo = aniimo,
                variants = regionalVariants,
                selectedVariant = selectedRegionalVariant,
                onSelectVariant = { selectedRegionalVariant = it }
            )

            // Interactive Community Tier Voting & Consensus Card
            CommunityTierVotingSection(
                aniimo = aniimo,
                communityVote = communityVote,
                onVoteTier = onVoteTier
            )

            // Interactive Evolution Lineage & Pre-Evolutions Section
            EvolutionLineSection(
                currentAniimo = aniimo,
                allAniimos = allAniimos,
                onSelectAniimo = onSelectAniimo
            )

            // Tab Navigation
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 12.dp,
                containerColor = Color(0xFF0F172A),
                contentColor = Color(0xFF38BDF8),
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                            )
                        },
                        modifier = Modifier.testTag("tab_$index")
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Content
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                when (selectedTabIndex) {
                    0 -> SkillsTab(aniimo)
                    1 -> AniimoDetailTopTeamsTab(
                        aniimo = aniimo,
                        teams = topRatedTeams,
                        allAniimos = allAniimos,
                        onToggleTeamUpvote = onToggleTeamUpvote,
                        onSimulateTeam = onNavigateToSimulator,
                        onOpenSubmitBuildDialog = { showBuildShareModal = true }
                    )
                    2 -> PartnersTab(aniimo, onSimulateWithPartner = { partnerId ->
                        onNavigateToSimulator(aniimo, partnerId)
                    })
                    3 -> ItemsTab(aniimo)
                    4 -> RotationAndStatsTab(aniimo)
                    5 -> FormsDetailTab(
                        aniimo = aniimo,
                        variants = regionalVariants,
                        selectedVariant = selectedRegionalVariant,
                        onSelectVariant = { selectedRegionalVariant = it }
                    )
                    6 -> AniimoDetailDiscussionTab(
                        aniimo = aniimo,
                        allAniimos = allAniimos,
                        posts = discussionPosts.filter { it.aniimoId.equals(aniimo.id, ignoreCase = true) },
                        onSubmitPost = onSubmitDiscussionPost,
                        onToggleUpvote = onToggleDiscussionUpvote,
                        onSubmitReply = onSubmitDiscussionReply
                    )
                }
            }
        }
    }

    if (showBuildShareModal) {
        val partner = allAniimos.firstOrNull { it.id != aniimo.id } ?: aniimo
        val slot3 = allAniimos.getOrElse(2) { aniimo }
        val slot4 = allAniimos.getOrElse(3) { partner }
        com.example.ui.components.SubmitShareBuildDialog(
            mainAniimo = aniimo,
            mainHeldItem = aniimo.bestItems.firstOrNull()?.name ?: "Pendant (+15 Contracted)",
            partnerAniimo = partner,
            partnerHeldItem = partner.bestItems.firstOrNull()?.name ?: "Capacitous Battery (+15)",
            slot3Aniimo = slot3,
            slot3HeldItem = slot3.bestItems.firstOrNull()?.name ?: "Bailite Crest (+15)",
            slot4Aniimo = slot4,
            slot4HeldItem = slot4.bestItems.firstOrNull()?.name ?: "Supernova Shard (+15)",
            awakeningAtk = 20,
            awakeningBreak = 15,
            awakeningRegen = 12,
            awakeningHp = 6,
            awakeningPdef = 4,
            awakeningMdef = 4,
            estimatedDps = aniimo.maxOptimizedDps,
            onDismiss = { showBuildShareModal = false },
            onPublishToTopTeams = { team ->
                onPublishBuild?.invoke(team)
            },
            onPostToDiscussionBoard = { _, author, category, title, content, item, awakening ->
                onSubmitDiscussionPost?.invoke(author, category, title, content, item, awakening)
            }
        )
    }
}

@Composable
private fun StatMetric(
    label: String,
    value: String,
    color: Color = Color.White
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun SkillsTab(aniimo: Aniimo) {
    val highestDpsSkill = aniimo.skills.firstOrNull { it.isHighestDps } ?: aniimo.skills.maxByOrNull { it.dpsPerSec }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Standout Highest DPS Potential Skill Card
        if (highestDpsSkill != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1708)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, Color(0xFFFFD60A), RoundedCornerShape(16.dp))
                    .testTag("highest_dps_banner")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFD60A).copy(alpha = 0.22f),
                                    Color(0xFF131D33)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD60A),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "HIGHEST DPS POTENTIAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFFD60A),
                                letterSpacing = 1.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFFF453A))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${highestDpsSkill.dpsPotentialTier} • ${highestDpsSkill.dpsPerSec / 1000}k DPS/sec",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = highestDpsSkill.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Base Output: ${highestDpsSkill.damageMultiplier} • Cooldown: ${highestDpsSkill.cooldownSec}s",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF38BDF8)
                    )

                    if (highestDpsSkill.enhancedByMaxedItem != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0F172A).copy(alpha = 0.85f))
                                .border(1.dp, Color(0xFFFFD60A).copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricBolt,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD60A),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "+15 CONTRACT ENHANCEMENT",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFFFD60A)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Requires: ${highestDpsSkill.enhancedByMaxedItem}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                highestDpsSkill.enhancedMultiplier?.let {
                                    Text(
                                        text = "Enhanced Scaling: $it",
                                        fontSize = 11.sp,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                highestDpsSkill.enhancedDpsPerSec?.let {
                                    Text(
                                        text = "Peak DPS: ${it / 1000}k DPS/sec",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFF9F0A)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section Overview
        Column {
            Text(
                text = "Skill Arsenal & DPS Tiering",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Aniimos feature standard base skills plus a Core Signature Skill. Maxing your contracted Held Item to +15 unlocks the Core Skill's enhanced DPS multiplier.",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 16.sp
            )
        }

        // Skills List
        aniimo.skills.forEach { skill ->
            val isTop = skill.isHighestDps
            val isCore = skill.isCoreSkill

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isTop) Color(0xFF162038) else Color(0xFF131D33)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (isTop) 1.5.dp else if (isCore) 1.dp else 1.dp,
                        color = if (isTop) Color(0xFFFFD60A).copy(alpha = 0.8f) else if (isCore) Color(0xFF38BDF8).copy(alpha = 0.6f) else Color(0xFF1E293B),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isCore) Color(0xFF38BDF8).copy(alpha = 0.2f)
                                        else Color(0xFF0F172A)
                                    )
                                    .border(
                                        1.dp,
                                        if (isCore) Color(0xFF38BDF8) else Color(0xFF334155),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = skill.type.label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCore) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = skill.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }

                        // DPS Tier & Rate Pill
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isTop) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFFFD60A))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "TOP DPS",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        when (skill.dpsPotentialTier) {
                                            "S-Tier" -> Color(0xFFFF453A).copy(alpha = 0.25f)
                                            "A-Tier" -> Color(0xFFFF9F0A).copy(alpha = 0.25f)
                                            else -> Color(0xFF38BDF8).copy(alpha = 0.2f)
                                        }
                                    )
                                    .border(
                                        1.dp,
                                        when (skill.dpsPotentialTier) {
                                            "S-Tier" -> Color(0xFFFF453A)
                                            "A-Tier" -> Color(0xFFFF9F0A)
                                            else -> Color(0xFF38BDF8)
                                        },
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${skill.dpsPotentialTier} • ${skill.dpsPerSec / 1000}k DPS/sec",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when (skill.dpsPotentialTier) {
                                        "S-Tier" -> Color(0xFFFF6961)
                                        "A-Tier" -> Color(0xFFFFB340)
                                        else -> Color(0xFF7DD3FC)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "Scaling: ${skill.damageMultiplier}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF34D399)
                        )
                        if (skill.cooldownSec > 0) {
                            Text(
                                text = "Cooldown: ${skill.cooldownSec}s",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        } else {
                            Text(
                                text = "Continuous / Passive",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = skill.description,
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 17.sp
                    )

                    // Core Skill Maxed Item Contract Enhancement Box
                    if (skill.isCoreSkill && skill.enhancedByMaxedItem != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0F172A))
                                .border(1.dp, Color(0xFFFFD60A).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Handyman,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD60A),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "CORE SKILL ENHANCEMENT (MAXED ITEM CONTRACT)",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD60A)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Equip & Max: ${skill.enhancedByMaxedItem}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                skill.enhancedMultiplier?.let {
                                    Text(
                                        text = "Enhanced Effect: $it",
                                        fontSize = 11.sp,
                                        color = Color(0xFF38BDF8)
                                    )
                                }
                                skill.enhancedDpsPerSec?.let {
                                    Text(
                                        text = "Enhanced DPS: ${it / 1000}k DPS/sec",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD60A)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Synergy Callout
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = Color(0xFFFFD60A),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = skill.dpsSynergyEffect,
                                fontSize = 11.sp,
                                color = Color(0xFFFFD60A),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartnersTab(
    aniimo: Aniimo,
    onSimulateWithPartner: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Synergistic Partner Aniimos",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Aniimo is designed for paired combat. Combining complementary elements triggers massive reaction damage, stagger amplification, and off-field DPS.",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
        )

        aniimo.bestPartners.forEach { synergy ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = synergy.partnerName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = synergy.partnerElement.primaryColor
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            AniimoElementBadge(element = synergy.partnerElement, compact = true)
                        }

                        // Synergy Tier & DPS Boost
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF10B981).copy(alpha = 0.2f))
                                .border(1.dp, Color(0xFF10B981), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "+${synergy.dpsBoostPercent}% DPS (${synergy.synergyTier})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF34D399)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Resonance: ${synergy.resonanceTitle}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF1F5F9)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = synergy.resonanceEffect,
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Partner Perk: ${synergy.partnerSkillBonus}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF38BDF8)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Rotation Tip
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Combo Tip: ${synergy.rotationTip}",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { onSimulateWithPartner(synergy.partnerAniimoId) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Simulate ${aniimo.name} + ${synergy.partnerName}",
                            color = Color(0xFF38BDF8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemsTab(aniimo: Aniimo) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Best-in-Slot Held Item (1 Equipped Max)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFFFD700).copy(alpha = 0.15f))
                    .border(1.dp, Color(0xFFFFD700), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "1 ITEM SLOT PER ANIIMO",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFFD700)
                )
            }
        }
        Text(
            text = "Each Aniimo can equip 1 Held Item in combat. Choose the Best-in-Slot (BiS) +15 Contracted item below for maximum personal DPS, or equip one of the alternative options if another team member already holds the BiS item.",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8),
            lineHeight = 17.sp
        )

        aniimo.bestItems.forEachIndexed { index, item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (item.isBestInSlot) Color(0xFFFFD700).copy(alpha = 0.5f) else Color(0xFF1E293B),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = when (item.rarity) {
                                        "Legendary" -> Color(0xFFFFD700)
                                        "Epic" -> Color(0xFFA855F7)
                                        else -> Color(0xFF38BDF8)
                                    }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (item.isBestInSlot) Color(0xFFFFD700) else Color(0xFF1E293B))
                                        .padding(horizontal = 5.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (item.isBestInSlot) "CHOICE #1 • BiS" else "ALT CHOICE #${index + 1}",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (item.isBestInSlot) Color(0xFF0F172A) else Color(0xFFCBD5E1)
                                    )
                                }
                            }
                            Text(
                                text = "${item.slot.label} • ${item.rarity}",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        // DPS contribution badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF38BDF8).copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = item.dpsContribution,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF38BDF8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stat Boost: ${item.mainStat}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF34D399)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Core Effect: ${item.coreEffect}",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Drop / Source: ",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = item.sourceLocation,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RotationAndStatsTab(aniimo: Aniimo) {
    val isTankOrBreaker = aniimo.role.contains("Break", ignoreCase = true) || aniimo.role.contains("Tank", ignoreCase = true)
    val isSupport = aniimo.role.contains("Support", ignoreCase = true) || aniimo.role.contains("Healer", ignoreCase = true)
    val awakeningOrder = when {
        isTankOrBreaker -> listOf(
            Triple("1st Priority: BREAK (20 pts)", "+45% Armor Break & Stagger Efficiency", Color(0xFFFB923C)),
            Triple("2nd Priority: ATK (15 pts)", "+28% Skill & Counter Damage", Color(0xFFFF453A)),
            Triple("3rd Priority: HP (12 pts)", "+15,000 Max HP & Shield Scaling", Color(0xFF10B981)),
            Triple("4th Priority: PDEF & MDEF (10 pts)", "+450 Physical & Elemental Defense", Color(0xFF38BDF8)),
            Triple("5th Priority: REGEN (5 pts)", "+15% Energy Recovery", Color(0xFF34D399))
        )
        isSupport -> listOf(
            Triple("1st Priority: REGEN (20 pts)", "+60% Energy Regen & Fast Ultimate Uptime", Color(0xFF34D399)),
            Triple("2nd Priority: HP (15 pts)", "+18,750 Max HP & Team Barrier Scaling", Color(0xFF10B981)),
            Triple("3rd Priority: ATK (12 pts)", "+22% Sub-DPS & Buff Scaling", Color(0xFFFF453A)),
            Triple("4th Priority: BREAK (8 pts)", "+24% Team Stagger Assist", Color(0xFFFB923C)),
            Triple("5th Priority: PDEF & MDEF (5 pts)", "+225 Physical & Elemental Defense", Color(0xFFA78BFA))
        )
        else -> listOf(
            Triple("1st Priority: ATK (20 pts)", "+56% Base, Core Skill & Basic Attack DMG", Color(0xFFFF453A)),
            Triple("2nd Priority: BREAK (15 pts)", "+60% Stagger Speed & +42% Burst Window DMG", Color(0xFFFB923C)),
            Triple("3rd Priority: REGEN (12 pts)", "+36% Energy Regen for Frame-1 Ultimate", Color(0xFF34D399)),
            Triple("4th Priority: HP (6 pts)", "+7,500 Max HP Survivability Cushion", Color(0xFF10B981)),
            Triple("5th Priority: PDEF & MDEF (4 pts)", "+180 Physical & Elemental Mitigation", Color(0xFF38BDF8))
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Awakening (Capability) 6-Stat Investment Priority Card
        Text(
            text = "Awakening (Capability) Point Priority",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Invest your Awakening points across HP, ATK, BREAK, PDEF, MDEF, and REGEN in this exact order:",
            fontSize = 12.sp,
            color = Color(0xFF38BDF8),
            fontWeight = FontWeight.SemiBold
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF131D33))
                .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            awakeningOrder.forEach { (title, effect, color) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = color
                        )
                        Text(
                            text = effect,
                            fontSize = 11.sp,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }
        }

        // Optimal Rotation
        Text(
            text = "Maximum DPS Rotation Guide",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Execute this exact combo sequence during dungeon clears to maximize damage output before the boss stagger gauge recovers.",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF131D33))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            aniimo.rotationGuide.forEach { step ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .padding(top = 6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF38BDF8))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = step,
                        fontSize = 12.sp,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Target Stat Priorities
        Text(
            text = "Combat Stat Benchmarks",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Target these final stat milestones after Held Item (+15) and Awakening allocation:",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF131D33))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            aniimo.statPriorities.forEachIndexed { index, priority ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#${index + 1}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF38BDF8),
                        modifier = Modifier.width(28.dp)
                    )
                    Text(
                        text = priority,
                        fontSize = 13.sp,
                        color = Color(0xFFF1F5F9),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun RegionalVariantsSection(
    aniimo: Aniimo,
    variants: List<com.example.data.model.RegionalVariant>,
    selectedVariant: com.example.data.model.RegionalVariant,
    onSelectVariant: (com.example.data.model.RegionalVariant) -> Unit,
    modifier: Modifier = Modifier
) {
    val formAvailability = remember(aniimo.id) {
        SparkleRepository.getFormAvailability(aniimo)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("regional_variants_section"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
        border = androidx.compose.foundation.BorderStroke(1.dp, selectedVariant.primaryTint.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = selectedVariant.primaryTint,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${aniimo.name} In-Game Forms (${variants.size} Available)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(selectedVariant.primaryTint.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = selectedVariant.shortBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = selectedVariant.accentTint
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Quick Eligibility Pills (Regional / Prismana / Umbral)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (formAvailability.hasRegionalForm) Color(0xFFF59E0B).copy(alpha = 0.18f) else Color(0xFF0F172A))
                        .border(0.5.dp, if (formAvailability.hasRegionalForm) Color(0xFFF59E0B) else Color(0xFF334155), RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (formAvailability.hasRegionalForm) {
                            "✓ ${formAvailability.regionalFormCategories.joinToString("/") { it.shortBadge }}"
                        } else {
                            "Standard Only (No Regional)"
                        },
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (formAvailability.hasRegionalForm) Color(0xFFFDE68A) else Color(0xFF94A3B8)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (formAvailability.hasPrismanaForm) Color(0xFFA855F7).copy(alpha = 0.18f) else Color(0xFF0F172A))
                        .border(0.5.dp, if (formAvailability.hasPrismanaForm) Color(0xFFA855F7) else Color(0xFF334155), RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (formAvailability.hasPrismanaForm) "✓ Prismana Form" else "✗ No Prismana Form",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (formAvailability.hasPrismanaForm) Color(0xFFE9D5FF) else Color(0xFF94A3B8)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (formAvailability.hasUmbralCoatRoll) Color(0xFFF43F5E).copy(alpha = 0.18f) else Color(0xFF0F172A))
                        .border(0.5.dp, if (formAvailability.hasUmbralCoatRoll) Color(0xFFF43F5E) else Color(0xFF334155), RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (formAvailability.hasUmbralCoatRoll) "✓ Umbral Roll" else "Umbral Coat Lost on Evo",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (formAvailability.hasUmbralCoatRoll) Color(0xFFFECDD3) else Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Variant Selector Cards (ONLY forms this species actually possesses!)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                variants.forEach { variant ->
                    val isSelected = variant.id == selectedVariant.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) variant.primaryTint else Color(0xFF334155),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onSelectVariant(variant) }
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .testTag("regional_variant_chip_${variant.id}")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AniimoFaceAvatar(
                                aniimo = aniimo,
                                size = 38.dp,
                                regionalVariant = variant
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = variant.shortBadge,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) variant.accentTint else Color.White
                                )
                                Text(
                                    text = if (variant.secondaryElement != null && variant.secondaryElement != variant.primaryElement) {
                                        "${variant.primaryElement.displayName} / ${variant.secondaryElement.displayName}"
                                    } else {
                                        variant.primaryElement.displayName
                                    },
                                    fontSize = 10.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Selected Form Details & Species Eligibility Notes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0F172A))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedVariant.variantName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = selectedVariant.statModifierSummary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF34D399)
                    )
                }
                Text(
                    text = "Habitat / Source: ${selectedVariant.regionName}",
                    fontSize = 11.sp,
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = selectedVariant.coatPaletteSummary,
                    fontSize = 11.sp,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 15.sp
                )
                Text(
                    text = "Form Mechanics: ${selectedVariant.traitAdaptation}",
                    fontSize = 11.sp,
                    color = Color(0xFFFDE68A),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 15.sp
                )
                Text(
                    text = "Prismana Status: ${formAvailability.prismanaSourceSummary}",
                    fontSize = 10.sp,
                    color = if (formAvailability.hasPrismanaForm) Color(0xFFD8B4FE) else Color(0xFF94A3B8)
                )
                Text(
                    text = "Umbral Rule: ${formAvailability.umbralMechanicsNote}",
                    fontSize = 10.sp,
                    color = Color(0xFFFCA5A5),
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun FormsDetailTab(
    aniimo: Aniimo,
    variants: List<com.example.data.model.RegionalVariant>,
    selectedVariant: com.example.data.model.RegionalVariant,
    onSelectVariant: (com.example.data.model.RegionalVariant) -> Unit
) {
    val formAvailability = remember(aniimo.id) {
        SparkleRepository.getFormAvailability(aniimo)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.testTag("forms_detail_tab")
    ) {
        Text(
            text = "Verified Online In-Game Forms for ${aniimo.name} (${variants.size} Total)",
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Text(
            text = "Each Aniimo species has its own specific regional/weather forms (Highland, Mountain Woods, Snowfield, Flowerfield, Beach, Rainfall, Tempest, Waterfall, Nighttime, Cloudmist, Plateau, Tidal), Prismana eligibility, and Umbral catch rules.",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8),
            lineHeight = 16.sp
        )

        variants.forEach { variant ->
            val isSelected = variant.id == selectedVariant.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectVariant(variant) }
                    .testTag("form_detail_card_${variant.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF1E293B) else Color(0xFF131D33)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) variant.primaryTint else Color(0xFF1E293B)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AniimoFaceAvatar(
                                aniimo = aniimo,
                                size = 44.dp,
                                regionalVariant = variant
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = variant.variantName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Habitat / Unlock: ${variant.regionName}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF38BDF8),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(variant.primaryTint.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = variant.shortBadge,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = variant.accentTint
                            )
                        }
                    }

                    Text(
                        text = variant.coatPaletteSummary,
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 16.sp
                    )
                    Text(
                        text = "${variant.statModifierSummary} • ${variant.traitAdaptation}",
                        fontSize = 11.sp,
                        color = Color(0xFF34D399),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Prismana & Umbral Verification Summary",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = formAvailability.prismanaSourceSummary,
                    fontSize = 11.sp,
                    color = if (formAvailability.hasPrismanaForm) Color(0xFFD8B4FE) else Color(0xFF94A3B8)
                )
                Text(
                    text = formAvailability.umbralMechanicsNote,
                    fontSize = 11.sp,
                    color = Color(0xFFFCA5A5)
                )
            }
        }
    }
}

@Composable
fun EvolutionLineSection(
    currentAniimo: Aniimo,
    allAniimos: List<Aniimo>,
    onSelectAniimo: ((Aniimo) -> Unit)?,
    modifier: Modifier = Modifier
) {
    if (allAniimos.isEmpty()) return

    // Find all related members in the evolutionary family
    val family = remember(currentAniimo, allAniimos) {
        val result = mutableSetOf<Aniimo>()
        result.add(currentAniimo)

        // Traverse upwards (pre-evolutions)
        var currUp: Aniimo? = currentAniimo
        while (currUp != null) {
            val parent = if (currUp.evolvesFromId != null) {
                allAniimos.find { it.id == currUp?.evolvesFromId }
            } else {
                allAniimos.find { it.evolvesToIds.contains(currUp?.id) }
            }
            if (parent != null && result.add(parent)) {
                currUp = parent
            } else {
                currUp = null
            }
        }

        // Traverse downwards (evolutions)
        fun traverseDown(node: Aniimo) {
            val children = allAniimos.filter {
                node.evolvesToIds.contains(it.id) || it.evolvesFromId == node.id
            }
            for (child in children) {
                if (result.add(child)) {
                    traverseDown(child)
                }
            }
        }
        traverseDown(currentAniimo)

        result.sortedBy {
            when (it.stage) {
                EvolutionStage.LUMIN -> 1
                EvolutionStage.GAMMA -> 2
                EvolutionStage.NOVA -> 3
                EvolutionStage.STANDALONE -> 4
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("evolution_line_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (family.size > 1) "Evolution Lineage (${family.size} Stages)" else "Species Classification",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Text(
                    text = currentAniimo.stage.shortLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (currentAniimo.stage) {
                        EvolutionStage.LUMIN -> Color(0xFF5EEAD4)
                        EvolutionStage.GAMMA -> Color(0xFFD8B4FE)
                        EvolutionStage.NOVA -> Color(0xFFFDE68A)
                        EvolutionStage.STANDALONE -> Color(0xFF93C5FD)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (family.size > 1) {
                // Horizontal list of evolutionary nodes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    family.forEachIndexed { index, member ->
                        val isCurrent = member.id == currentAniimo.id

                        // Node
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isCurrent) Color(0xFF1E293B) else Color(0xFF0F172A))
                                .border(
                                    width = if (isCurrent) 2.dp else 1.dp,
                                    color = if (isCurrent) Color(0xFF38BDF8) else Color(0xFF334155),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable(enabled = !isCurrent && onSelectAniimo != null) {
                                    onSelectAniimo?.invoke(member)
                                }
                                .padding(10.dp)
                                .testTag("evolution_node_${member.id}")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AniimoFaceAvatar(aniimo = member, size = 44.dp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = member.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color(0xFF38BDF8) else Color.White
                                )
                                Text(
                                    text = member.stage.stageName,
                                    fontSize = 10.sp,
                                    color = Color(0xFF94A3B8)
                                )
                                if (isCurrent) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(Color(0xFF38BDF8))
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text("Viewing", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                                    }
                                } else {
                                    Text("Tap to view", fontSize = 8.sp, color = Color(0xFF64748B))
                                }
                            }
                        }

                        // Arrow if not last
                        if (index < family.size - 1) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = member.evolutionRequirement ?: "Evolves",
                                    fontSize = 9.sp,
                                    color = Color(0xFF34D399),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text("➔", fontSize = 14.sp, color = Color(0xFF34D399), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "This creature is a standalone species with specialized standalone stats and does not undergo stage evolution.",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            // Explanatory Pre-Evo or Nova Callout
            Spacer(modifier = Modifier.height(10.dp))
            if (currentAniimo.stage.isPreEvolution) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F766E).copy(alpha = 0.25f))
                        .border(1.dp, Color(0xFF0F766E).copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "🌱 Pre-Evolution Form (${currentAniimo.stage.stageName})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5EEAD4)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Builds core skill synergies, energy refund masteries, and base potential. Reaches peak damage output upon evolving into its final form.",
                            fontSize = 10.sp,
                            color = Color(0xFFCCFBF1)
                        )
                    }
                }
            } else if (currentAniimo.stage == EvolutionStage.NOVA && currentAniimo.evolvesFromId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF78350F).copy(alpha = 0.25f))
                        .border(1.dp, Color(0xFF78350F).copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "⭐ Nova Stage Sovereign",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE68A)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Fully evolved form with maximum stat multipliers, enhanced core skills, and access to the Ultimate Burst ability.",
                            fontSize = 10.sp,
                            color = Color(0xFFFEF3C7)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Interactive Community Tier Consensus and Player Voting Card.
 * Allows users to vote on their perceived meta tier (S+, S, A, B+, B, C)
 * and shows live breakdown and community weighted rating.
 */
@Composable
fun CommunityTierVotingSection(
    aniimo: Aniimo,
    communityVote: com.example.data.local.CommunityTierVoteEntity?,
    onVoteTier: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    val totalVotes = communityVote?.totalVotes ?: 0
    val displayTier = if (totalVotes > 0 && communityVote != null) communityVote.communityTier else aniimo.metaTier
    val score = communityVote?.communityScore ?: 3.0f
    val userVote = communityVote?.userVote

    val tierList = listOf("S+", "S", "A", "B+", "B", "C")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("community_tier_voting_section"),
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
            // Header: Title & Consensus Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Community Tier Consensus",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (totalVotes > 0) "$totalVotes Player Votes • Consensus ${"%.2f".format(score)}/5.0" else "No votes recorded yet",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (displayTier) {
                                "S+" -> Color(0xFFFF453A)
                                "S" -> Color(0xFFFF9F0A)
                                "A" -> Color(0xFF10B981)
                                "B+" -> Color(0xFF3B82F6)
                                "B" -> Color(0xFF6366F1)
                                else -> Color(0xFF64748B)
                            }
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "★ $displayTier Tier",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Stacked Vote Distribution Progress Bar
            if (totalVotes > 0) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF0F172A))
                    ) {
                        tierList.forEach { tier ->
                            val pct = communityVote?.getVotePercentageForTier(tier) ?: 0
                            if (pct > 0) {
                                Box(
                                    modifier = Modifier
                                        .weight(pct.toFloat().coerceAtLeast(0.1f))
                                        .fillMaxSize()
                                        .background(
                                            when (tier) {
                                                "S+" -> Color(0xFFFF453A)
                                                "S" -> Color(0xFFFF9F0A)
                                                "A" -> Color(0xFF10B981)
                                                "B+" -> Color(0xFF3B82F6)
                                                "B" -> Color(0xFF6366F1)
                                                else -> Color(0xFF64748B)
                                            }
                                        )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Percentage Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        tierList.forEach { tier ->
                            val pct = communityVote?.getVotePercentageForTier(tier) ?: 0
                            Text(
                                text = "$tier: $pct%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when (tier) {
                                    "S+" -> Color(0xFFFF453A)
                                    "S" -> Color(0xFFFF9F0A)
                                    "A" -> Color(0xFF10B981)
                                    "B+" -> Color(0xFF3B82F6)
                                    "B" -> Color(0xFF6366F1)
                                    else -> Color(0xFF94A3B8)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // User voting prompt
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (userVote != null) "Your Vote: $userVote (Tap to change)" else "Cast Your Community Tier Vote:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (userVote != null) Color(0xFF38BDF8) else Color(0xFFCBD5E1)
                )

                if (userVote != null) {
                    Text(
                        text = "✓ Voted",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Interactive Voting Buttons Row (All 6 tiers)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tierList.forEach { tier ->
                    val isUserVoted = userVote?.uppercase() == tier.uppercase()
                    val tierColor = when (tier) {
                        "S+" -> Color(0xFFFF453A)
                        "S" -> Color(0xFFFF9F0A)
                        "A" -> Color(0xFF10B981)
                        "B+" -> Color(0xFF3B82F6)
                        "B" -> Color(0xFF6366F1)
                        else -> Color(0xFF64748B)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp) // Accessible 48dp minimum touch target
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isUserVoted) tierColor.copy(alpha = 0.3f) else Color(0xFF0F172A)
                            )
                            .border(
                                width = if (isUserVoted) 2.dp else 1.dp,
                                color = if (isUserVoted) tierColor else Color(0xFF1E293B),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                onVoteTier?.invoke(tier)
                            }
                            .testTag("vote_tier_${tier.lowercase().replace("+", "_plus")}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (isUserVoted) "✓ $tier" else tier,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isUserVoted) tierColor else Color.White
                            )
                            val voteCount = communityVote?.getVoteCountForTier(tier) ?: 0
                            Text(
                                text = "$voteCount",
                                fontSize = 9.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Top Rated Teams Tab inside Aniimo Detail Screen,
 * showing community builds specifically built for this Aniimo across all 5 modes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniimoDetailTopTeamsTab(
    aniimo: Aniimo,
    teams: List<com.example.data.model.TopRatedTeam>,
    allAniimos: List<Aniimo>,
    onToggleTeamUpvote: ((String, Boolean) -> Unit)?,
    onSimulateTeam: (Aniimo, String?) -> Unit,
    onOpenSubmitBuildDialog: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedMode by remember { mutableStateOf(com.example.data.model.TeamMode.ALL) }

    val aniimoTeams = teams.filter {
        it.primaryAniimoId.equals(aniimo.id, ignoreCase = true) ||
        it.members.any { m -> m.aniimoId.equals(aniimo.id, ignoreCase = true) }
    }

    val filteredTeams = if (selectedMode == com.example.data.model.TeamMode.ALL) {
        aniimoTeams
    } else {
        aniimoTeams.filter { it.mode == selectedMode }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Submit / Share Build CTA Bar inside Aniimo Detail Top Teams Tab
        if (onOpenSubmitBuildDialog != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF131D33))
                    .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Have a ${aniimo.name} Build?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Submit your 4-Aniimo squad, 1 Held Item each & Awakening priority!",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onOpenSubmitBuildDialog,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("detail_submit_team_build_button")
                ) {
                    Text(
                        text = "+ Submit / Share Build",
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Mode filter row
        androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(com.example.data.model.TeamMode.values()) { mode ->
                FilterChip(
                    selected = selectedMode == mode,
                    onClick = { selectedMode = mode },
                    label = { Text(mode.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(mode.badgeColorHex),
                        selectedLabelColor = if (mode == com.example.data.model.TeamMode.ALL) Color(0xFF0F172A) else Color.White,
                        containerColor = Color(0xFF131D33),
                        labelColor = Color(0xFFCBD5E1)
                    )
                )
            }
        }

        if (filteredTeams.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No ${selectedMode.displayName} builds listed yet for ${aniimo.name}.",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap '+ Submit / Share Build' above to publish the first ${selectedMode.displayName} team!",
                        color = Color(0xFF64748B),
                        fontSize = 11.sp
                    )
                }
            }
        } else {
            filteredTeams.forEach { team ->
                TopRatedTeamCard(
                    team = team,
                    allAniimos = allAniimos,
                    onToggleUpvote = { onToggleTeamUpvote?.invoke(team.id, team.hasUserUpvoted) },
                    onSimulateTeam = onSimulateTeam
                )
            }
        }
    }
}

@Composable
private fun AniimoDetailDiscussionTab(
    aniimo: Aniimo,
    allAniimos: List<Aniimo>,
    posts: List<com.example.data.model.DiscussionPost>,
    onSubmitPost: ((
        authorName: String,
        category: com.example.data.model.DiscussionCategory,
        title: String,
        content: String,
        recommendedHeldItem: String,
        recommendedAwakening: String
    ) -> Unit)?,
    onToggleUpvote: ((Int) -> Unit)?,
    onSubmitReply: ((Int, String, String) -> Unit)?
) {
    var showNewPostModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("detail_discussion_tab"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${aniimo.name} Community Discussion (${posts.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Talk about ${aniimo.name} builds, 1 Held Item setups, Awakening points & strategies.",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { showNewPostModal = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("detail_new_discussion_btn")
            ) {
                Text(
                    text = "+ Start a Post",
                    color = Color(0xFF0F172A),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        if (posts.isEmpty()) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No threads for ${aniimo.name} yet!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap 'Post Idea' above to start the first discussion thread for ${aniimo.name}.",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            posts.forEach { post ->
                DiscussionPostCard(
                    post = post,
                    aniimo = aniimo,
                    onToggleUpvote = { onToggleUpvote?.invoke(post.id) },
                    onSubmitReply = { author, replyText ->
                        onSubmitReply?.invoke(post.id, author, replyText)
                    }
                )
            }
        }
    }

    if (showNewPostModal) {
        NewDiscussionPostDialog(
            aniimos = allAniimos.ifEmpty { listOf(aniimo) },
            initialAniimo = aniimo,
            lockAniimoSelection = true,
            onDismiss = { showNewPostModal = false },
            onSubmit = { _, author, category, title, content, item, awakening, _, _, _ ->
                onSubmitPost?.invoke(author, category, title, content, item, awakening)
                showNewPostModal = false
            }
        )
    }
}


