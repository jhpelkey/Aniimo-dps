package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreTeam(
    @DocumentId val id: String = "",
    val userId: String = "",
    val authorName: String = "",
    val name: String = "",
    val tag: String = "DPS",
    val description: String = "",
    val memberIds: List<String> = emptyList(),
    val membersJson: String = "[]",
    val isPublic: Boolean = true,
    val upvotes: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreDiscussionPost(
    @DocumentId val id: String = "",
    val authorUid: String = "",
    val authorName: String = "",
    val aniimoId: String = "",
    val aniimoName: String = "",
    val title: String = "",
    val body: String = "",
    val tag: String = "Builds",
    val mediaUrl: String = "",
    val mediaType: String = "",
    val upvotes: Int = 0,
    val commentsCount: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestorePostComment(
    @DocumentId val id: String = "",
    val authorUid: String = "",
    val authorName: String = "",
    val content: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreLeaderboardEntry(
    @DocumentId val id: String = "",
    val submitterUid: String = "",
    val runnerName: String = "",
    val dungeonName: String = "",
    val teamName: String = "",
    val aniimoIds: List<String> = emptyList(),
    val clearTimeSeconds: Int = 0,
    val clearTimeFormatted: String = "",
    val videoUrl: String = "",
    val notes: String = "",
    val legitVotes: Int = 0,
    val notLegitVotes: Int = 0,
    val isVerified: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreSubmissionVote(
    @DocumentId val voterUid: String = "",
    val isLegit: Boolean = true,
    val reason: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreLivePatchNote(
    @DocumentId val id: String = "",
    val version: String = "",
    val title: String = "",
    val releaseDate: String = "",
    val sourceUrl: String = "",
    val summary: String = "",
    val skillChangesJson: String = "[]",
    val statChangesJson: String = "[]",
    val newAniimoJson: String = "[]",
    val syncedByUid: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

/**
 * Domain model for a balance change applied to an Aniimo
 */
data class SkillBalanceChange(
    val aniimoId: String,
    val aniimoName: String,
    val skillName: String,
    val oldMultiplier: Double,
    val newMultiplier: Double,
    val notes: String
)

data class StatBalanceChange(
    val aniimoId: String,
    val aniimoName: String,
    val statName: String, // "ATK", "HP", "BREAK", "PDEF", "MDEF", "REGEN"
    val deltaPercent: Double,
    val notes: String
)
