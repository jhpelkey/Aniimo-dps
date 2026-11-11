package com.example.data.model

enum class DiscussionCategory(
    val id: String,
    val displayName: String,
    val badgeColorHex: Long
) {
    ALL("all", "All Topics", 0xFF38BDF8),
    BUILD_IDEA("build_idea", "Build & Item Ideas", 0xFF10B981),
    AWAKENING("awakening", "Awakening & Stats", 0xFFF59E0B),
    TEAM_SYNERGY("team_synergy", "4-Aniimo Synergy", 0xFFA855F7),
    PVP_EGG_HEIST("pvp_egg_heist", "PvP & Egg Heist", 0xFFEF4444),
    SPARKLE_HUNT("sparkle_hunt", "Sparkle Hunting", 0xFFFFD700),
    PRISMANA_SHOWCASE("prismana_showcase", "Prismana & Home Showcase", 0xFFEC4899);

    companion object {
        fun fromId(id: String): DiscussionCategory {
            return values().find { it.id.equals(id, ignoreCase = true) } ?: BUILD_IDEA
        }
    }
}

data class DiscussionReply(
    val id: String,
    val authorName: String,
    val content: String,
    val timestamp: String
)

data class DiscussionPost(
    val id: Int = 0,
    val aniimoId: String,
    val aniimoName: String,
    val aniimoElement: Element,
    val authorName: String,
    val authorBadge: String = "Theorycrafter",
    val category: DiscussionCategory,
    val title: String,
    val content: String,
    val recommendedHeldItem: String = "",
    val recommendedAwakening: String = "",
    val mediaUrl: String = "",
    val mediaType: String = "",
    val upvotes: Int = 1,
    val hasUserUpvoted: Boolean = false,
    val timestamp: String = "Just now",
    val replies: List<DiscussionReply> = emptyList()
)
