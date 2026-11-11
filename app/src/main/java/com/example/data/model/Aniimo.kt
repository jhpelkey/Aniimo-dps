package com.example.data.model

enum class EvolutionStage(
    val stageName: String,
    val shortLabel: String,
    val description: String,
    val isPreEvolution: Boolean
) {
    LUMIN(
        stageName = "Lumin",
        shortLabel = "Stage 1 Pre-Evo",
        description = "Base / Baby form with rapid energy recharge, learning foundational skills",
        isPreEvolution = true
    ),
    GAMMA(
        stageName = "Gamma",
        shortLabel = "Stage 2 Pre-Evo",
        description = "Intermediate evolutionary form balancing mobility and offensive stats",
        isPreEvolution = true
    ),
    NOVA(
        stageName = "Nova",
        shortLabel = "Final Form",
        description = "Fully evolved form with maximum stat scaling and Ultimate skill unlocked",
        isPreEvolution = false
    ),
    STANDALONE(
        stageName = "Standalone",
        shortLabel = "Single Stage",
        description = "Unique creature with no evolutionary progression, specialized toolkit",
        isPreEvolution = false
    )
}

enum class GameRegion(
    val id: String,
    val displayName: String,
    val formCountDisplay: String,
    val currentCount: Int,
    val totalCount: Int,
    val description: String
) {
    ALL(
        id = "all",
        displayName = "All Zones",
        formCountDisplay = "237+ Forms",
        currentCount = 237,
        totalCount = 249,
        description = "All discovered zones in the world of Aniimo (195 in Breezy Plains, 42 in Whisperwake Isles)."
    ),
    BREEZY_PLAINS(
        id = "breezy_plains",
        displayName = "Breezy Plains",
        formCountDisplay = "195 Forms",
        currentCount = 195,
        totalCount = 195,
        description = "Verdant open plains, starter ridges, and lush meadows home to 195 creature forms."
    ),
    WHISPERWAKE_ISLES(
        id = "whisperwake_isles",
        displayName = "Whisperwake Isles",
        formCountDisplay = "42 Forms (Expands to 54)",
        currentCount = 42,
        totalCount = 54,
        description = "Tidal archipelago, coral reefs, and abyssal waters currently hosting 42 forms, expanding to 54 in upcoming content."
    )
}

data class Aniimo(
    val id: String,
    val name: String,
    val title: String,
    val element: Element,
    val secondaryElement: Element? = null,
    val role: String,
    val metaTier: String, // "S+", "S", "A", "B"
    val baseAtk: Int,
    val baseDps: Int,
    val maxOptimizedDps: Int,
    val critRate: Float,
    val critDmg: Float,
    val attackSpeed: Float,
    val overview: String,
    val stage: EvolutionStage = EvolutionStage.NOVA,
    val region: GameRegion = GameRegion.BREEZY_PLAINS,
    val aniilogNumber: String = "No. ---",
    val evolvesFromId: String? = null,
    val evolvesToIds: List<String> = emptyList(),
    val evolutionRequirement: String? = null,
    val faceDrawableResName: String? = null,
    val remoteArtworkUrl: String? = null,
    val skills: List<AniimoSkill>,
    val bestPartners: List<PartnerSynergy>,
    val bestItems: List<RecommendedItem>,
    val statPriorities: List<String>,
    val rotationGuide: List<String>
)
