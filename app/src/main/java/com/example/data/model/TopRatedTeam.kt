package com.example.data.model

/**
 * Team classification categories in the game Aniimo:
 * - DPS / Boss speedrun teams
 * - Campaign / map exploration & mob wiping teams
 * - Egg Heist PvE co-op & extraction teams
 * - PvP 3v3 Arena & team battle compositions (4-Aniimo squad roster)
 * - 1v1 Solo duel & duress teams (4-Aniimo rotation squad)
 */
enum class TeamMode(
    val id: String,
    val displayName: String,
    val shortBadge: String,
    val badgeColorHex: Long,
    val description: String
) {
    ALL(
        id = "all",
        displayName = "All Modes",
        shortBadge = "ALL",
        badgeColorHex = 0xFF38BDF8,
        description = "All 4-Aniimo tactical team compositions across all game modes"
    ),
    DPS(
        id = "dps",
        displayName = "DPS / Boss Run",
        shortBadge = "DPS TEAM",
        badgeColorHex = 0xFFFF453A,
        description = "Max burst damage output, armor-break synergy, and record boss clear times"
    ),
    CAMPAIGN(
        id = "campaign",
        displayName = "Campaign / Map",
        shortBadge = "CAMPAIGN",
        badgeColorHex = 0xFF10B981,
        description = "Sustained mob wave clear, energy regen awakening, and Breezy Plains / Whisperwake exploration"
    ),
    EGG_HEIST_PVE(
        id = "egg_heist",
        displayName = "Egg Heist PvE",
        shortBadge = "EGG HEIST",
        badgeColorHex = 0xFF14B8A6,
        description = "High movement speed, objective stealth, nest distraction, and safe egg extraction"
    ),
    PVP_TEAM(
        id = "pvp_team",
        displayName = "PvP Arena Team",
        shortBadge = "PVP TEAM",
        badgeColorHex = 0xFFA855F7,
        description = "Crowd control chains, elemental cleanses, interrupt timing, and team wipe combos"
    ),
    PVP_SOLO(
        id = "pvp_1v1",
        displayName = "1v1 Duel Team",
        shortBadge = "1V1 DUEL",
        badgeColorHex = 0xFFF59E0B,
        description = "Solo duel survivability, counter-elemental tech, and 1-on-1 arena control"
    )
}

/**
 * The 6 core Awakening (Capability) stats in Aniimo where points are invested:
 * HP, ATK, BREAK, PDEF, MDEF, and REGEN.
 */
enum class AwakeningStat(
    val code: String,
    val fullName: String,
    val colorHex: Long,
    val statDescription: String
) {
    HP("HP", "Max Health", 0xFF10B981, "Increases maximum health pool and shield scaling"),
    ATK("ATK", "Attack Power", 0xFFFF453A, "Increases base skill and critical strike damage"),
    BREAK("BREAK", "Armor Break", 0xFFF59E0B, "Increases boss stagger & armor gauge shred efficiency"),
    PDEF("PDEF", "Physical Def", 0xFF38BDF8, "Reduces incoming physical & melee strike damage"),
    MDEF("MDEF", "Magical Def", 0xFFA855F7, "Reduces incoming elemental & AoE spell damage"),
    REGEN("REGEN", "Energy Regen", 0xFF14B8A6, "Accelerates energy/stamina & HP recovery rate");

    companion object {
        fun fromCode(nameOrCode: String): AwakeningStat {
            val upper = nameOrCode.uppercase()
            return entries.firstOrNull { upper.contains(it.code) } ?: ATK
        }
    }
}

/**
 * Represents an Awakening (Capability) stat allocation on an Aniimo team build.
 * In Aniimo, the Capability / Awakening system allows investing points across:
 * HP, ATK, BREAK, PDEF, MDEF, and REGEN.
 */
data class CapabilityPerk(
    val name: String, // e.g., "ATK", "BREAK", "REGEN", "HP", "PDEF", "MDEF"
    val tier: String = "Max Priority", // e.g., "1st Priority (Max)", "2nd Priority", "3rd Priority"
    val effect: String, // Exact stat bonus from investing points into HP/ATK/BREAK/PDEF/MDEF/REGEN
    val costPoints: Int = 5 // Points invested into this Awakening stat
) {
    val awakeningStat: AwakeningStat
        get() = AwakeningStat.fromCode(name)
}

/**
 * Step-by-step order per point invested into the Awakening / Capability stats
 * (HP, ATK, BREAK, PDEF, MDEF, REGEN).
 */
data class PointInvestmentStep(
    val pointRange: String, // e.g. "Point 1 -> 5", "Point 6 -> 10"
    val capabilityOrSkill: String, // e.g. "ATK (Attack Awakening)"
    val rationale: String, // Why you invest into this Awakening stat at this point milestone
    val priorityLevel: String // "1st Max (Core)", "2nd Max (Synergy)", "3rd (Survivability)"
)

/**
 * A team in Aniimo consists of 4 Aniimo, and each Aniimo equips 1 Held Item
 * plus its Awakening (HP / ATK / BREAK / PDEF / MDEF / REGEN) stat investment order.
 */
data class TeamMemberBuild(
    val aniimoId: String,
    val aniimoName: String,
    val role: String, // e.g. "Slot 1 • Main Burst Carry", "Slot 2 • Break Specialist"
    val element: Element,
    val heldItem: String, // Single Held Item equipped by this Aniimo (e.g., "Pendant (+15 Contracted)")
    val heldItemEffect: String = "", // Passive/stat effect of the single Held Item
    val awakeningOrder: String = "ATK > BREAK > REGEN" // Awakening stat point order for this member
) {
    // Compatibility properties for legacy references
    val weapon: String get() = heldItem
    val relic1: String get() = heldItemEffect.ifBlank { awakeningOrder }
    val relic2: String get() = awakeningOrder
}

data class TopRatedTeam(
    val id: String,
    val title: String,
    val mode: TeamMode,
    val primaryAniimoId: String,
    val primaryAniimoName: String,
    val members: List<TeamMemberBuild>, // Always 4 Aniimo per team, 1 Held Item each
    val capabilities: List<CapabilityPerk>, // Awakening stats: HP, ATK, BREAK, PDEF, MDEF, REGEN
    val pointInvestmentOrder: List<PointInvestmentStep>, // Order per point invested in Awakening
    val strategyGuide: String,
    val estimatedDpsOrWinRate: String,
    val author: String = "PawprintMaster",
    val upvotes: Int = 412,
    val hasUserUpvoted: Boolean = false,
    val isUserCreated: Boolean = false
)
