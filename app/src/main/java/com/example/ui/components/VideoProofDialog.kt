package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.LeaderboardEntry

@Composable
fun VideoProofDialog(
    entry: LeaderboardEntry,
    onDismiss: () -> Unit,
    onVoteLegit: ((LeaderboardEntry, Boolean) -> Unit)? = null
) {
    val context = LocalContext.current
    val isLegitGreen = entry.isLegitCommunityVerified
    val statusColor = if (isLegitGreen) Color(0xFF10B981) else Color(0xFFEF4444)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("video_proof_dialog")
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Video Proof",
                            tint = statusColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Proof Verification",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_proof_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Community Legitimacy & Video Duration Verification Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .border(1.5.dp, statusColor, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isLegitGreen) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isLegitGreen) "VERIFIED LEGIT (GREEN)" else "FLAGGED NOT LEGIT (RED)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = statusColor
                            )
                        }

                        Text(
                            text = "${entry.legitVotes} Legit • ${entry.notLegitVotes} Not Legit",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (entry.isSuspiciousShortVideo) {
                            "⚠ Clip Duration Warning: Submitted video is only ${entry.videoDurationSeconds} sec long, which cannot prove a ${entry.clearTimeDisplay} boss clear!"
                        } else {
                            "✓ Full Run Video Check: Submitted clip duration (${entry.videoDurationSeconds}s) covers the full ${entry.clearTimeDisplay} boss fight."
                        },
                        fontSize = 11.sp,
                        color = if (entry.isSuspiciousShortVideo) Color(0xFFFCA5A5) else Color(0xFFA7F3D0),
                        lineHeight = 15.sp
                    )

                    if (onVoteLegit != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF10B981).copy(alpha = 0.25f))
                                    .border(1.dp, Color(0xFF10B981), RoundedCornerShape(8.dp))
                                    .clickable { onVoteLegit(entry, true) }
                                    .padding(vertical = 8.dp)
                                    .testTag("dialog_vote_legit_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✓ Vote Legit (${entry.legitVotes})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF34D399)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFEF4444).copy(alpha = 0.25f))
                                    .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(8.dp))
                                    .clickable { onVoteLegit(entry, false) }
                                    .padding(vertical = 8.dp)
                                    .testTag("dialog_vote_not_legit_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✕ Vote Not Legit (${entry.notLegitVotes})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF87171)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Highlight Card: Clear Time & Runner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F172A))
                        .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = entry.runnerName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = if (isLegitGreen) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = "Verified Status",
                                    tint = statusColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "Encounter: ${entry.bossName}",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = statusColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = entry.clearTimeDisplay,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = statusColor
                                )
                            }
                            Text(
                                text = if (isLegitGreen) "Community Legit" else "Flagged Not Legit",
                                fontSize = 10.sp,
                                color = statusColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Roster & Build Specs
                Text(
                    text = "Team & Equipment Setup",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE2E8F0)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F172A))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Main Striker: ",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Text(
                            text = "${entry.mainAniimoName} (${entry.mainAniimoElement.displayName})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = entry.mainAniimoElement.primaryColor
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Support Partner: ",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Text(
                            text = "${entry.partnerAniimoName} (${entry.partnerAniimoElement.displayName})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = entry.partnerAniimoElement.primaryColor
                        )
                    }
                    if (entry.slot3AniimoName.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Squad Member 3: ",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = "${entry.slot3AniimoName} (${entry.slot3AniimoElement.displayName})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = entry.slot3AniimoElement.primaryColor
                            )
                        }
                    }
                    if (entry.slot4AniimoName.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Squad Member 4: ",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = "${entry.slot4AniimoName} (${entry.slot4AniimoElement.displayName})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = entry.slot4AniimoElement.primaryColor
                            )
                        }
                    }
                    Row {
                        Text(
                            text = "Lead Held Item (1 Slot): ",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Text(
                            text = entry.weaponEquipped,
                            fontSize = 12.sp,
                            color = Color(0xFFFFD60A),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Text(
                            text = "Partner & Awakening: ",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Text(
                            text = entry.relicSetEquipped,
                            fontSize = 12.sp,
                            color = Color(0xFFF1F5F9),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Runner Notes / Speedrun strategy
                Text(
                    text = "Runner Strategy & Notes",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE2E8F0)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = entry.runnerNotes.ifBlank { "No additional strategy notes provided by runner." },
                    fontSize = 12.sp,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F172A))
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Video URL Link Box
                Text(
                    text = "Video Proof URL (${entry.videoPlatform} • ${entry.videoDurationSeconds}s Clip)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE2E8F0)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = entry.videoProofUrl,
                    fontSize = 11.sp,
                    color = Color(0xFF38BDF8),
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0B1120))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Close", color = Color(0xFF94A3B8))
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.videoProofUrl))
                            runCatching { context.startActivity(intent) }
                        },
                        modifier = Modifier
                            .weight(1.5f)
                            .testTag("open_video_link_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Video Proof", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
