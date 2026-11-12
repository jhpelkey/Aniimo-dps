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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LeaderboardEntry

@Composable
fun LeaderboardCard(
    rank: Int,
    entry: LeaderboardEntry,
    onWatchProof: (LeaderboardEntry) -> Unit,
    onUpvote: (LeaderboardEntry) -> Unit,
    onVoteLegit: ((LeaderboardEntry, Boolean) -> Unit)? = null,
    isAdmin: Boolean = false,
    onAdminDeleteEntry: ((LeaderboardEntry) -> Unit)? = null,
    onAdminTimeoutOrBanRunner: ((String, String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isLegitGreen = entry.isLegitCommunityVerified // legitVotes > notLegitVotes
    val statusAccentColor = if (isLegitGreen) Color(0xFF10B981) else Color(0xFFEF4444)

    val rankBadgeColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color(0xFF475569) // Neutral
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("leaderboard_card_${entry.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLegitGreen) Color(0xFF131D33) else Color(0xFF1F121B)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.5.dp,
                    color = statusAccentColor.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(14.dp)
        ) {
            // Community Legitimacy Status Banner (Green if Legit > Not Legit, Red if Not Legit >= Legit)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusAccentColor.copy(alpha = 0.16f))
                    .border(1.dp, statusAccentColor.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("legit_status_banner_${entry.id}"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isLegitGreen) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = if (isLegitGreen) "Legit Verified" else "Not Legit Flagged",
                        tint = statusAccentColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isLegitGreen) {
                            "LEGIT VERIFIED (${entry.legitVotes} Legit vs ${entry.notLegitVotes} Not Legit)"
                        } else {
                            "NOT LEGIT / FLAGGED (${entry.legitVotes} Legit vs ${entry.notLegitVotes} Not Legit)"
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = statusAccentColor
                    )
                }

                Text(
                    text = "Video: ${entry.videoDurationSeconds}s",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (entry.isSuspiciousShortVideo) Color(0xFFFCA5A5) else Color(0xFFA7F3D0)
                )
            }

            if (entry.isSuspiciousShortVideo) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "⚠ Suspicious Proof Alert: Submitted video is only ${entry.videoDurationSeconds} sec long for a claimed ${entry.clearTimeDisplay} clear!",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFCA5A5)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Header Row: Rank, Runner Name, Verified Badge, Clear Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rank Number Pill
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(rankBadgeColor.copy(alpha = 0.2f))
                        .border(1.dp, rankBadgeColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#$rank",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = rankBadgeColor
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = entry.runnerName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (isLegitGreen) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = if (isLegitGreen) "Verified Speedrun Proof" else "Flagged Submission",
                            tint = statusAccentColor,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = entry.bossName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp
                    )
                }

                // Clear Time Box (prominent gaming timer)
                Column(horizontalAlignment = Alignment.End) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(
                                1.dp,
                                statusAccentColor.copy(alpha = 0.7f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = entry.clearTimeDisplay,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = if (isLegitGreen) Color(0xFF34D399) else Color(0xFFF87171)
                        )
                    }
                    Text(
                        text = entry.submittedDate,
                        fontSize = 9.sp,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 4-Aniimo Roster Combo used in this clear
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0B1120))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Lead Striker
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AniimoFaceAvatarById(
                        aniimoId = entry.mainAniimoId,
                        aniimoName = entry.mainAniimoName,
                        element = entry.mainAniimoElement,
                        size = 26.dp,
                        shape = RoundedCornerShape(6.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Lead",
                            fontSize = 8.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = entry.mainAniimoName.take(8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = entry.mainAniimoElement.primaryColor
                        )
                    }
                }

                // Partner
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AniimoFaceAvatarById(
                        aniimoId = entry.partnerAniimoId,
                        aniimoName = entry.partnerAniimoName,
                        element = entry.partnerAniimoElement,
                        size = 26.dp,
                        shape = RoundedCornerShape(6.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Partner",
                            fontSize = 8.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = entry.partnerAniimoName.take(8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = entry.partnerAniimoElement.primaryColor
                        )
                    }
                }

                // Slot 3
                if (entry.slot3AniimoName.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AniimoFaceAvatarById(
                            aniimoId = entry.slot3AniimoId,
                            aniimoName = entry.slot3AniimoName,
                            element = entry.slot3AniimoElement,
                            size = 26.dp,
                            shape = RoundedCornerShape(6.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text(
                                text = "Slot 3",
                                fontSize = 8.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = entry.slot3AniimoName.take(8),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = entry.slot3AniimoElement.primaryColor
                            )
                        }
                    }
                }

                // Slot 4
                if (entry.slot4AniimoName.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AniimoFaceAvatarById(
                            aniimoId = entry.slot4AniimoId,
                            aniimoName = entry.slot4AniimoName,
                            element = entry.slot4AniimoElement,
                            size = 26.dp,
                            shape = RoundedCornerShape(6.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text(
                                text = "Slot 4",
                                fontSize = 8.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = entry.slot4AniimoName.take(8),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = entry.slot4AniimoElement.primaryColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Equipment & Awakening Summary (1 Held Item per Aniimo)
            Text(
                text = "Lead Held Item: ${entry.weaponEquipped}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFFD60A),
                maxLines = 1
            )
            Text(
                text = entry.relicSetEquipped,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Interactive Community Verification Voting Row ("Legit" vs "Not Legit")
            if (onVoteLegit != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0B1120))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Verify Proof:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF94A3B8)
                    )

                    val userVotedLegit = entry.userLegitVote == "LEGIT"
                    val userVotedNotLegit = entry.userLegitVote == "NOT_LEGIT"

                    // Vote Legit (Green) Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (userVotedLegit) Color(0xFF10B981).copy(alpha = 0.3f)
                                else Color(0xFF10B981).copy(alpha = 0.14f)
                            )
                            .border(
                                width = if (userVotedLegit) 1.5.dp else 1.dp,
                                color = Color(0xFF10B981),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onVoteLegit(entry, true) }
                            .padding(vertical = 6.dp, horizontal = 8.dp)
                            .testTag("vote_legit_button_${entry.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓ Legit (${entry.legitVotes})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF34D399)
                        )
                    }

                    // Vote Not Legit (Red) Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (userVotedNotLegit) Color(0xFFEF4444).copy(alpha = 0.3f)
                                else Color(0xFFEF4444).copy(alpha = 0.14f)
                            )
                            .border(
                                width = if (userVotedNotLegit) 1.5.dp else 1.dp,
                                color = Color(0xFFEF4444),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onVoteLegit(entry, false) }
                            .padding(vertical = 6.dp, horizontal = 8.dp)
                            .testTag("vote_not_legit_button_${entry.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✕ Not Legit (${entry.notLegitVotes})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFF87171)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Footer Actions: Watch Proof (Video) & Upvote
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Watch Proof Button
                Button(
                    onClick = { onWatchProof(entry) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC2626) // YouTube / Video red accent
                    ),
                    modifier = Modifier.testTag("watch_proof_button_${entry.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Watch Video Proof",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Proof (${entry.videoPlatform} • ${entry.videoDurationSeconds}s)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Upvote Commendation
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onUpvote(entry) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("upvote_button_${entry.id}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (entry.hasUserUpvoted) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = "Upvote Run",
                        tint = if (entry.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${entry.upvotes}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (entry.hasUserUpvoted) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                    )
                }
            }

            if (isAdmin) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF3B0764).copy(alpha = 0.45f))
                        .border(1.dp, Color(0xFFF43F5E).copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("admin_mod_bar_run_${entry.id}"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ADMIN MOD:",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFDA4AF)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFEF4444))
                                .clickable { onAdminDeleteEntry?.invoke(entry) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .testTag("admin_delete_run_${entry.id}")
                        ) {
                            Text("Remove Run", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF59E0B))
                                .clickable { onAdminTimeoutOrBanRunner?.invoke(entry.runnerName, "TIMEOUT_24H") }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .testTag("admin_timeout_runner_${entry.id}")
                        ) {
                            Text("Timeout 24h", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF991B1B))
                                .clickable { onAdminTimeoutOrBanRunner?.invoke(entry.runnerName, "PERMANENT_BAN") }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .testTag("admin_ban_runner_${entry.id}")
                        ) {
                            Text("Ban Runner", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
