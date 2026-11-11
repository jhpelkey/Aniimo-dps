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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.EvolutionStage

@Composable
fun EvolutionStageBadge(
    stage: EvolutionStage,
    modifier: Modifier = Modifier
) {
    val (bg, textColor, text) = when (stage) {
        EvolutionStage.LUMIN -> Triple(Color(0xFF0F766E), Color(0xFF5EEAD4), "LUMIN • PRE-EVO")
        EvolutionStage.GAMMA -> Triple(Color(0xFF581C87), Color(0xFFD8B4FE), "GAMMA • PRE-EVO")
        EvolutionStage.NOVA -> Triple(Color(0xFF78350F), Color(0xFFFDE68A), "NOVA • FINAL")
        EvolutionStage.STANDALONE -> Triple(Color(0xFF1E3A8A), Color(0xFF93C5FD), "STANDALONE")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg.copy(alpha = 0.85f))
            .border(0.5.dp, textColor.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun AniimoCard(
    aniimo: Aniimo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    communityVote: com.example.data.local.CommunityTierVoteEntity? = null
) {
    val displayTier = communityVote?.communityTier ?: aniimo.metaTier
    val totalVotes = communityVote?.totalVotes ?: 0

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("aniimo_card_${aniimo.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF131D33)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            aniimo.element.primaryColor.copy(alpha = 0.5f),
                            Color(0xFF1E293B)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Aniimo Face Model Avatar - Displays real creature portrait picture
                AniimoFaceAvatar(
                    aniimo = aniimo,
                    size = 60.dp,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // Number, Stage Badge & Community Voted Tier
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = aniimo.aniilogNumber,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF64748B)
                        )

                        EvolutionStageBadge(stage = aniimo.stage)

                        // Community Tier Badge
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
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                .testTag("card_tier_badge_${aniimo.id}")
                        ) {
                            Text(
                                text = if (totalVotes > 0) "★ $displayTier ($totalVotes votes)" else "$displayTier Tier",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }

                        if (communityVote?.userVote != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF0284C7).copy(alpha = 0.35f))
                                    .border(0.5.dp, Color(0xFF38BDF8), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Voted ${communityVote.userVote}",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = aniimo.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = aniimo.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AniimoElementBadge(element = aniimo.element, compact = true)
                        aniimo.secondaryElement?.let { sec ->
                            AniimoElementBadge(element = sec, compact = true)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (aniimo.region == com.example.data.model.GameRegion.WHISPERWAKE_ISLES)
                                        Color(0xFF06B6D4).copy(alpha = 0.2f)
                                    else
                                        Color(0xFF10B981).copy(alpha = 0.2f)
                                )
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = if (aniimo.region == com.example.data.model.GameRegion.WHISPERWAKE_ISLES) "🌊 Whisperwake" else "🌿 Breezy Plains",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (aniimo.region == com.example.data.model.GameRegion.WHISPERWAKE_ISLES) Color(0xFF67E8F9) else Color(0xFF6EE7B7)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFA855F7).copy(alpha = 0.2f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "4 Regional Variants",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD8B4FE)
                            )
                        }

                        Text(
                            text = aniimo.role,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }

                    // Evolution requirement hint if present
                    if (aniimo.evolutionRequirement != null) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "➔ ${aniimo.evolutionRequirement}",
                            fontSize = 10.sp,
                            color = Color(0xFF34D399),
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Peak DPS",
                            tint = Color(0xFFFF9F0A),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${aniimo.maxOptimizedDps / 1000}k",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9F0A)
                        )
                    }
                    Text(
                        text = if (aniimo.stage.isPreEvolution) "Base Cap" else "Peak DPS",
                        fontSize = 10.sp,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "View Details",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
