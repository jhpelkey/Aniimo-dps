package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Element
import com.example.data.model.LeaderboardEntry

@Entity(tableName = "leaderboard_entries")
data class LeaderboardEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bossId: String,
    val bossName: String,
    val runnerName: String,
    val mainAniimoId: String,
    val mainAniimoName: String,
    val mainAniimoElement: String,
    val partnerAniimoId: String,
    val partnerAniimoName: String,
    val partnerAniimoElement: String,
    val slot3AniimoId: String = "",
    val slot3AniimoName: String = "",
    val slot3AniimoElement: String = "EARTH",
    val slot4AniimoId: String = "",
    val slot4AniimoName: String = "",
    val slot4AniimoElement: String = "WIND",
    val clearTimeMillis: Long,
    val clearTimeDisplay: String,
    val videoProofUrl: String,
    val videoPlatform: String,
    val weaponEquipped: String,
    val relicSetEquipped: String,
    val estimatedDpsOutput: Int,
    val submittedDate: String,
    val isVerified: Boolean,
    val upvotes: Int,
    val hasUserUpvoted: Boolean,
    val isUserSubmission: Boolean,
    val runnerNotes: String,
    val legitVotes: Int = 1,
    val notLegitVotes: Int = 0,
    val userLegitVote: String? = null,
    val videoDurationSeconds: Int = 45
) {
    fun toDomain(): LeaderboardEntry {
        val mainElem = Element.fromString(mainAniimoElement)
        val partnerElem = Element.fromString(partnerAniimoElement)
        val s3Elem = Element.fromString(slot3AniimoElement)
        val s4Elem = Element.fromString(slot4AniimoElement)
        return LeaderboardEntry(
            id = id,
            bossId = bossId,
            bossName = bossName,
            runnerName = runnerName,
            mainAniimoId = mainAniimoId,
            mainAniimoName = mainAniimoName,
            mainAniimoElement = mainElem,
            partnerAniimoId = partnerAniimoId,
            partnerAniimoName = partnerAniimoName,
            partnerAniimoElement = partnerElem,
            slot3AniimoId = slot3AniimoId,
            slot3AniimoName = slot3AniimoName,
            slot3AniimoElement = s3Elem,
            slot4AniimoId = slot4AniimoId,
            slot4AniimoName = slot4AniimoName,
            slot4AniimoElement = s4Elem,
            clearTimeMillis = clearTimeMillis,
            clearTimeDisplay = clearTimeDisplay,
            videoProofUrl = videoProofUrl,
            videoPlatform = videoPlatform,
            weaponEquipped = weaponEquipped,
            relicSetEquipped = relicSetEquipped,
            estimatedDpsOutput = estimatedDpsOutput,
            submittedDate = submittedDate,
            isVerified = isVerified,
            upvotes = upvotes,
            hasUserUpvoted = hasUserUpvoted,
            isUserSubmission = isUserSubmission,
            runnerNotes = runnerNotes,
            legitVotes = legitVotes,
            notLegitVotes = notLegitVotes,
            userLegitVote = userLegitVote,
            videoDurationSeconds = videoDurationSeconds
        )
    }

    companion object {
        fun fromDomain(entry: LeaderboardEntry): LeaderboardEntryEntity {
            return LeaderboardEntryEntity(
                id = entry.id,
                bossId = entry.bossId,
                bossName = entry.bossName,
                runnerName = entry.runnerName,
                mainAniimoId = entry.mainAniimoId,
                mainAniimoName = entry.mainAniimoName,
                mainAniimoElement = entry.mainAniimoElement.name,
                partnerAniimoId = entry.partnerAniimoId,
                partnerAniimoName = entry.partnerAniimoName,
                partnerAniimoElement = entry.partnerAniimoElement.name,
                slot3AniimoId = entry.slot3AniimoId,
                slot3AniimoName = entry.slot3AniimoName,
                slot3AniimoElement = entry.slot3AniimoElement.name,
                slot4AniimoId = entry.slot4AniimoId,
                slot4AniimoName = entry.slot4AniimoName,
                slot4AniimoElement = entry.slot4AniimoElement.name,
                clearTimeMillis = entry.clearTimeMillis,
                clearTimeDisplay = entry.clearTimeDisplay,
                videoProofUrl = entry.videoProofUrl,
                videoPlatform = entry.videoPlatform,
                weaponEquipped = entry.weaponEquipped,
                relicSetEquipped = entry.relicSetEquipped,
                estimatedDpsOutput = entry.estimatedDpsOutput,
                submittedDate = entry.submittedDate,
                isVerified = entry.isVerified,
                upvotes = entry.upvotes,
                hasUserUpvoted = entry.hasUserUpvoted,
                isUserSubmission = entry.isUserSubmission,
                runnerNotes = entry.runnerNotes,
                legitVotes = entry.legitVotes,
                notLegitVotes = entry.notLegitVotes,
                userLegitVote = entry.userLegitVote,
                videoDurationSeconds = entry.videoDurationSeconds
            )
        }
    }
}
