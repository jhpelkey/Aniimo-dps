package com.example.data.model

data class LeaderboardEntry(
    val id: Long = 0,
    val bossId: String,
    val bossName: String,
    val runnerName: String,
    val mainAniimoId: String,
    val mainAniimoName: String,
    val mainAniimoElement: Element,
    val partnerAniimoId: String,
    val partnerAniimoName: String,
    val partnerAniimoElement: Element,
    val slot3AniimoId: String = "",
    val slot3AniimoName: String = "",
    val slot3AniimoElement: Element = Element.EARTH,
    val slot4AniimoId: String = "",
    val slot4AniimoName: String = "",
    val slot4AniimoElement: Element = Element.WIND,
    val clearTimeMillis: Long,
    val clearTimeDisplay: String, // e.g., "00:48.72"
    val videoProofUrl: String,
    val videoPlatform: String, // "YouTube", "Twitch", "Bilibili", etc.
    val weaponEquipped: String,
    val relicSetEquipped: String,
    val estimatedDpsOutput: Int,
    val submittedDate: String,
    val isVerified: Boolean,
    val upvotes: Int,
    val hasUserUpvoted: Boolean = false,
    val isUserSubmission: Boolean = false,
    val runnerNotes: String = "",
    val legitVotes: Int = 1,
    val notLegitVotes: Int = 0,
    val userLegitVote: String? = null, // "LEGIT", "NOT_LEGIT", or null
    val videoDurationSeconds: Int = 45
) {
    /**
     * Community legitimacy check:
     * More "Legit" votes than "Not Legit" votes -> Green (Verified Legit).
     * Fewer or equal "Legit" votes than "Not Legit" votes -> Red (Flagged / Not Legit).
     */
    val isLegitCommunityVerified: Boolean
        get() = legitVotes > notLegitVotes

    /**
     * Flags suspicious video clips (e.g., 1-second video proof or video shorter than the run itself).
     */
    val isSuspiciousShortVideo: Boolean
        get() = videoDurationSeconds <= 3 || (videoDurationSeconds * 1000L) < (clearTimeMillis - 2000L)
}
