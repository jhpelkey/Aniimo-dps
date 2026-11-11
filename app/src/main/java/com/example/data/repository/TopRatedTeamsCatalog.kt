package com.example.data.repository

import com.example.data.model.CapabilityPerk
import com.example.data.model.Element
import com.example.data.model.PointInvestmentStep
import com.example.data.model.TeamMemberBuild
import com.example.data.model.TeamMode
import com.example.data.model.TopRatedTeam

/**
 * Canonical Top Rated 4-Aniimo Teams for the game Aniimo (Pawprint Studio).
 *
 * Game Mechanics Enforced:
 * - Every Team consists of 4 Aniimo (Slots 1–4).
 * - Each Aniimo equips 1 Held Item.
 * - Capability = The Awakening feature in Aniimo, where Awakening points are invested across
 *   the 6 core stats: HP, ATK, BREAK, PDEF, MDEF, and REGEN.
 * - Includes the exact Point Investment Order per Awakening point invested across all 5 modes:
 *   1. DPS / Boss Run
 *   2. Campaign / Map Exploration
 *   3. Egg Heist PvE
 *   4. PvP Arena Team
 *   5. 1v1 Solo Duel Team
 */
val initialTopRatedTeams: List<TopRatedTeam> = listOf(
    // ====================================================
    // FULMINTIS TEAMS (All 5 Modes • 4 Aniimo • 1 Held Item Each • Awakening Capabilities)
    // ====================================================
    TopRatedTeam(
        id = "team_fulmintis_dps_boss",
        title = "Overcharge Thunder Sovereign",
        mode = TeamMode.DPS,
        primaryAniimoId = "fulmintis",
        primaryAniimoName = "Fulmintis",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 1 • Main Electro Burst Carry",
                element = Element.LIGHTNING,
                heldItem = "Pendant (+15 Contracted)",
                heldItemEffect = "+35% Core Skill Burst & Instant Electro Reset",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "dazmand",
                aniimoName = "Dazmand",
                role = "Slot 2 • Crit Field & Energy Battery",
                element = Element.LIGHT,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+28% Team Energy Generation & Electro Amp",
                awakeningOrder = "REGEN > ATK > BREAK > HP"
            ),
            TeamMemberBuild(
                aniimoId = "whirlbite",
                aniimoName = "Whirlbite",
                role = "Slot 3 • Hydro Armor Breaker",
                element = Element.WATER,
                heldItem = "Fission Needles (+15)",
                heldItemEffect = "Double Basic Hit Proc & +32% Break Shred",
                awakeningOrder = "BREAK > ATK > REGEN > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "irisalis",
                aniimoName = "Irisalis",
                role = "Slot 4 • Floral Clone & Synergy Buffer",
                element = Element.GRASS,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Party Critical Strike Damage",
                awakeningOrder = "REGEN > HP > MDEF > ATK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (Max)", "+42% Attack Power & +28% Electro Crit Multiplier at max Awakening", 6),
            CapabilityPerk("BREAK", "2nd Priority (High)", "+35% Boss Armor Gauge Shred speed during Hydro-Electro reactions", 4),
            CapabilityPerk("REGEN", "3rd Priority", "+25% Energy & Stamina regeneration per second for non-stop skill chains", 3),
            CapabilityPerk("HP", "4th Priority", "+1,850 Max HP to survive Alpha Nether Colossus phase transitions", 2),
            CapabilityPerk("PDEF", "0 Pts (Skip)", "+0 Physical Defense (Full glass-cannon speedrun optimization)", 0),
            CapabilityPerk("MDEF", "0 Pts (Skip)", "+0 Magical Defense (Dodge i-frames used instead of tanking)", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "ATK (6 Pts • Max First)", "Invest your first 6 Awakening points straight into ATK to hit the 180k burst threshold on Fulmintis.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "BREAK (4 Pts • Second)", "Put points 7 to 10 into BREAK so Whirlbite and Fulmintis shatter boss armor before the first enrage.", "2nd Max (Synergy)"),
            PointInvestmentStep("Point 11 -> 13", "REGEN (3 Pts • Third)", "Invest points 11 to 13 into REGEN to keep energy full between Pendant activations.", "3rd (Rotation)"),
            PointInvestmentStep("Point 14 -> 15", "HP (2 Pts • Final)", "Place the final 2 points into HP to prevent one-shot deaths from unavoidable boss roars.", "4th (Safety)")
        ),
        strategyGuide = "Open with Whirlbite (Fission Needles) to build Hydro Break, drop Irisalis floral clone and Dazmand (Capacitous Battery) crit field, then swap to Fulmintis (Pendant) with maxed ATK & BREAK Awakening to erase the boss during Break.",
        estimatedDpsOrWinRate = "182,000 Peak DPS • 4-Aniimo Boss Speedrun",
        author = "PawprintMaster",
        upvotes = 542
    ),

    TopRatedTeam(
        id = "team_fulmintis_campaign",
        title = "Breezy Plains Storm Sweeper",
        mode = TeamMode.CAMPAIGN,
        primaryAniimoId = "fulmintis",
        primaryAniimoName = "Fulmintis",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 1 • AoE Chain Lightning Carry",
                element = Element.LIGHTNING,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+30% Electro AoE Arc & Energy Refund on Kill",
                awakeningOrder = "ATK > REGEN > HP > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "cornet",
                aniimoName = "Cornet",
                role = "Slot 2 • Wind Vortex Mob Puller",
                element = Element.WIND,
                heldItem = "Wind Feather (+15)",
                heldItemEffect = "+25% Map Movement Speed & Vortex Pull Radius",
                awakeningOrder = "REGEN > ATK > HP > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "fenmane",
                aniimoName = "Fenmane",
                role = "Slot 3 • Primal Roar ATK Buffer",
                element = Element.GRASS,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Crit DMG on Gathered Mob Waves",
                awakeningOrder = "ATK > REGEN > HP > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 4 • Elite Camp Breaker & Tank",
                element = Element.EARTH,
                heldItem = "Bailite Crest (+15)",
                heldItemEffect = "+30% Earth Break & Shield on Swap",
                awakeningOrder = "HP > BREAK > PDEF > MDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (Max)", "+42% Attack Power to one-shot wild mob clusters across Breezy Plains", 6),
            CapabilityPerk("REGEN", "2nd Priority (Max)", "+40% Energy & Stamina Regen for infinite overworld dashing and casting", 5),
            CapabilityPerk("HP", "3rd Priority", "+1,850 Max HP for sustained no-recall map exploration", 2),
            CapabilityPerk("PDEF", "4th Priority", "+14% Physical Defense against wild predator ambushes", 1),
            CapabilityPerk("MDEF", "5th Priority", "+14% Magical Defense against elemental alpha patrols", 1),
            CapabilityPerk("BREAK", "0 Pts (Skip)", "Normal overworld mobs die before Break gauge is needed", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "ATK (6 Pts • Max First)", "Max out ATK first so every Chain Lightning cast clears entire Breezy Plains packs in one hit.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 11", "REGEN (5 Pts • Max Second)", "Invest points 7–11 into REGEN so your stamina and skill energy never run dry while farming.", "2nd Max (Sustain)"),
            PointInvestmentStep("Point 12 -> 13", "HP (2 Pts • Third)", "Add 2 points in HP for comfortable multi-zone farming without healing stations.", "3rd (Sustain)"),
            PointInvestmentStep("Point 14 -> 15", "PDEF (1 Pt) & MDEF (1 Pt)", "Invest 1 point each in PDEF and MDEF for balanced all-terrain exploration mitigation.", "4th (Defense)")
        ),
        strategyGuide = "Use Cornet (Wind Feather) to sprint across Breezy Plains and vacuum mobs together, buff with Fenmane (Ferocious Fang), and wipe the wave instantly with Fulmintis maxed ATK + REGEN Awakening.",
        estimatedDpsOrWinRate = "Sub-12s Wave Clear • 195 Plains Forms Farming",
        author = "PlainsPathfinder",
        upvotes = 389
    ),

    TopRatedTeam(
        id = "team_fulmintis_egg_heist",
        title = "Volt Stride Nest Extractor",
        mode = TeamMode.EGG_HEIST_PVE,
        primaryAniimoId = "fulmintis",
        primaryAniimoName = "Fulmintis",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 1 • Primary Egg Runner & Dasher",
                element = Element.LIGHTNING,
                heldItem = "Wind Feather (+15)",
                heldItemEffect = "+28% Sprint Velocity & -50% Egg Carry Slow",
                awakeningOrder = "REGEN > HP > PDEF > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "skippy",
                aniimoName = "Skippy",
                role = "Slot 2 • Nest Decoy & Trap Disabler",
                element = Element.EARTH,
                heldItem = "Camouflage Leaf (+15)",
                heldItemEffect = "-45% Alpha Detection & Aggro Radius",
                awakeningOrder = "REGEN > HP > PDEF > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 3 • Alpha Guardian Taunt Tank",
                element = Element.EARTH,
                heldItem = "Thick Fur Plate (+15)",
                heldItemEffect = "+2,400 Bonus HP & Taunt Threat Aura",
                awakeningOrder = "HP > PDEF > MDEF > REGEN"
            ),
            TeamMemberBuild(
                aniimoId = "sherro",
                aniimoName = "Sherro",
                role = "Slot 4 • Backup Underground Carrier",
                element = Element.EARTH,
                heldItem = "Swift Boots (+15)",
                heldItemEffect = "+22% Burrow & Extraction Speed",
                awakeningOrder = "REGEN > HP > PDEF > BREAK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("REGEN", "1st Priority (Max)", "+50% Stamina & Energy Regen to sprint non-stop while carrying heavy Golden Eggs", 6),
            CapabilityPerk("HP", "2nd Priority (High)", "+3,600 Max HP to absorb alpha guardian hits during extraction", 4),
            CapabilityPerk("PDEF", "3rd Priority", "+28% Physical Defense against pursuing nest predators", 2),
            CapabilityPerk("MDEF", "4th Priority", "+28% Magical Defense against ranged alpha breath attacks", 2),
            CapabilityPerk("BREAK", "5th Priority", "+12% Break to stun blockading guards at extraction choke points", 1),
            CapabilityPerk("ATK", "0 Pts (Skip)", "Objective extraction build focuses 100% on speed, stamina regen, and survival", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "REGEN (6 Pts • Max First)", "Always max REGEN first in Egg Heist so dash stamina regenerates faster than egg encumbrance drains it.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "HP (4 Pts • Second)", "Invest points 7–10 into HP so the carrier never drops the egg from health threshold staggers.", "2nd Max (Survival)"),
            PointInvestmentStep("Point 11 -> 14", "PDEF (2 Pts) & MDEF (2 Pts)", "Split points 11–14 evenly between PDEF and MDEF to tank both melee bites and elemental projectiles.", "3rd (Mitigation)"),
            PointInvestmentStep("Point 15", "BREAK (1 Pt • Utility)", "Put point 15 into BREAK for quick guard stagger if cornered inside the nest.", "4th (Utility)")
        ),
        strategyGuide = "Grizbo (Thick Fur Plate) taunts the Nest Matriarch, Skippy (Camouflage Leaf) disables spore traps, and Fulmintis (Wind Feather) with maxed REGEN + HP Awakening dashes the Golden Egg straight to extraction.",
        estimatedDpsOrWinRate = "98.4% Egg Extraction Success • Mythic Nests",
        author = "HeistCommander",
        upvotes = 418
    ),

    TopRatedTeam(
        id = "team_fulmintis_pvp_team",
        title = "Conduit Stun-Lock 4-Man Squad",
        mode = TeamMode.PVP_TEAM,
        primaryAniimoId = "fulmintis",
        primaryAniimoName = "Fulmintis",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 1 • Burst Assassin & Finisher",
                element = Element.LIGHTNING,
                heldItem = "Pendant (+15 Contracted)",
                heldItemEffect = "+35% Burst Execute & Skill Reset on Takedown",
                awakeningOrder = "ATK > HP > REGEN > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "malevsera",
                aniimoName = "Malevsera",
                role = "Slot 2 • AoE Silence & Hex Controller",
                element = Element.DARK,
                heldItem = "Void Shard (+15)",
                heldItemEffect = "+35% Silence & Crowd-Control Duration",
                awakeningOrder = "REGEN > HP > MDEF > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "irisalis",
                aniimoName = "Irisalis",
                role = "Slot 3 • Team Cleanse & Sustain Healer",
                element = Element.GRASS,
                heldItem = "Life Blossom (+15)",
                heldItemEffect = "+30% Team Heal Output & Debuff Cleanse",
                awakeningOrder = "HP > REGEN > PDEF > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 4 • Frontline Peel & Shield Breaker",
                element = Element.EARTH,
                heldItem = "Iron Band (+15)",
                heldItemEffect = "+25% Damage Reduction & Poise Immunity",
                awakeningOrder = "HP > PDEF > MDEF > BREAK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (High)", "+36% Attack Power to burst down enemy carries inside a single stun window", 5),
            CapabilityPerk("HP", "2nd Priority (High)", "+3,600 Max HP to survive enemy opening PvP burst combos", 4),
            CapabilityPerk("REGEN", "3rd Priority", "+25% Energy Regen to cycle CC and burst rotations faster than opponents", 3),
            CapabilityPerk("PDEF", "4th Priority", "+20% Physical Defense against Thornblade & Scorchhowl dive comps", 2),
            CapabilityPerk("MDEF", "5th Priority", "+14% Magical Defense against Stellarys & Malevsera AoE comps", 1),
            CapabilityPerk("BREAK", "0 Pts (Skip)", "Grizbo handles shield breaking while Fulmintis invests in raw ATK & survivability", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 5", "ATK (5 Pts • 1st Priority)", "Invest first 5 points into ATK so Fulmintis can one-rotation enemy squishies.", "1st Max (Burst)"),
            PointInvestmentStep("Point 6 -> 9", "HP (4 Pts • 2nd Priority)", "Put points 6–9 into HP to prevent getting assassinated by enemy mirror Fulmintis teams.", "2nd Max (Anti-Burst)"),
            PointInvestmentStep("Point 10 -> 12", "REGEN (3 Pts • 3rd Priority)", "Invest points 10–12 into REGEN to win the second skill rotation in drawn-out arena fights.", "3rd (Tempo)"),
            PointInvestmentStep("Point 13 -> 15", "PDEF (2 Pts) & MDEF (1 Pt)", "Finish with 2 points in PDEF and 1 point in MDEF to survive physical and elemental arena poke.", "4th (Mitigation)")
        ),
        strategyGuide = "Grizbo (Iron Band) absorbs the opening engage, Malevsera (Void Shard) silences the enemy squad, Irisalis (Life Blossom) cleanses your carry, and Fulmintis (Pendant) executes.",
        estimatedDpsOrWinRate = "90.4% Win Rate • Grandmaster PvP Squad",
        author = "VoltGladiator",
        upvotes = 452
    ),

    TopRatedTeam(
        id = "team_fulmintis_pvp_solo",
        title = "Apex 1v1 Duelist Rotation",
        mode = TeamMode.PVP_SOLO,
        primaryAniimoId = "fulmintis",
        primaryAniimoName = "Fulmintis",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 1 • Primary 1v1 Electro Duelist",
                element = Element.LIGHTNING,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Crit DMG & Duelist Counter-Burst",
                awakeningOrder = "ATK > HP > PDEF > REGEN"
            ),
            TeamMemberBuild(
                aniimoId = "whirlbite",
                aniimoName = "Whirlbite",
                role = "Slot 2 • Anti-Fire / Anti-Tank Counter",
                element = Element.WATER,
                heldItem = "Torrent Ring (+15)",
                heldItemEffect = "+28% Hydro Damage & Shield Puncture",
                awakeningOrder = "ATK > BREAK > HP > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "thornblade",
                aniimoName = "Thornblade",
                role = "Slot 3 • Anti-Water / Melee Parry Duelist",
                element = Element.GRASS,
                heldItem = "Fission Needles (+15)",
                heldItemEffect = "Double Hit Basic Attacks & Parry Lifesteal",
                awakeningOrder = "ATK > PDEF > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 4 • Anti-Lightning Anchor Tank",
                element = Element.EARTH,
                heldItem = "Iron Band (+15)",
                heldItemEffect = "+25% All-Damage Mitigation in 1v1 Overtime",
                awakeningOrder = "HP > PDEF > MDEF > BREAK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (High)", "+36% Attack Power to win short 1v1 trade windows", 5),
            CapabilityPerk("HP", "2nd Priority (High)", "+2,800 Max HP for crucial 1v1 duel trading bulk", 3),
            CapabilityPerk("PDEF", "3rd Priority", "+28% Physical Defense to out-trade melee bruisers", 3),
            CapabilityPerk("MDEF", "4th Priority", "+20% Magical Defense against burst casters", 2),
            CapabilityPerk("REGEN", "5th Priority", "+18% Energy Regen to dash-kite enemy ultimates", 2),
            CapabilityPerk("BREAK", "0 Pts (Skip)", "Whirlbite and Grizbo cover poise break matchups in Slots 2 & 4", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 5", "ATK (5 Pts • 1st Priority)", "Invest points 1–5 into ATK so every counter-hit chunks the opponent's lifebar.", "1st Max (Damage)"),
            PointInvestmentStep("Point 6 -> 8", "HP (3 Pts • 2nd Priority)", "Add 3 points into HP to survive enemy all-in ultimate combos.", "2nd (Bulk)"),
            PointInvestmentStep("Point 9 -> 13", "PDEF (3 Pts) & MDEF (2 Pts)", "Invest points 9–13 into PDEF and MDEF so you win every even damage trade in 1v1.", "3rd (Duelist Def)"),
            PointInvestmentStep("Point 14 -> 15", "REGEN (2 Pts • Final)", "Put final 2 points into REGEN for extra dodge stamina in duel overtime.", "4th (Stamina)")
        ),
        strategyGuide = "Bring all 4 elemental counters in your 4-slot 1v1 roster. Lead with Fulmintis (Ferocious Fang) using balanced ATK/HP/PDEF Awakening to win neutral trades, and counter-pick with Whirlbite, Thornblade, or Grizbo.",
        estimatedDpsOrWinRate = "92.1% 1v1 Duel Win Rate • 4-Slot Counter Roster",
        author = "SoloSovereign",
        upvotes = 364
    ),

    // ====================================================
    // SCORCHHOWL TEAMS (4 Aniimo • 1 Held Item Each • Awakening Capabilities)
    // ====================================================
    TopRatedTeam(
        id = "team_scorchhowl_dps_boss",
        title = "Pyroclasm Meltdown 4-Stack",
        mode = TeamMode.DPS,
        primaryAniimoId = "scorchhowl",
        primaryAniimoName = "Scorchhowl",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "scorchhowl",
                aniimoName = "Scorchhowl",
                role = "Slot 1 • Main Pyre Stacking Carry",
                element = Element.FIRE,
                heldItem = "Fission Needles (+15)",
                heldItemEffect = "Builds 2x Pyre Stacks per Basic Attack",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "infergon",
                aniimoName = "Infergon",
                role = "Slot 2 • Fire Vulnerability Amplifier",
                element = Element.FIRE,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Critical Strike DMG on Burning Bosses",
                awakeningOrder = "ATK > REGEN > BREAK > HP"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 3 • Heavy Earth Armor Breaker",
                element = Element.EARTH,
                heldItem = "Bailite Crest (+15)",
                heldItemEffect = "+35% Boss Break Gauge Shred on Slam",
                awakeningOrder = "BREAK > HP > PDEF > ATK"
            ),
            TeamMemberBuild(
                aniimoId = "cornet",
                aniimoName = "Cornet",
                role = "Slot 4 • Fire-Swirl Attack Speed Buffer",
                element = Element.WIND,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+25% Team Energy & Attack Speed Haste",
                awakeningOrder = "REGEN > HP > MDEF > ATK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (Max)", "+42% Attack Power & Pyre Detonation scaling", 6),
            CapabilityPerk("BREAK", "2nd Priority (High)", "+35% Permafrost & Boss Shield Break efficiency", 4),
            CapabilityPerk("REGEN", "3rd Priority", "+25% Energy & Stamina Regen for rapid Cinder Pounce loops", 3),
            CapabilityPerk("HP", "4th Priority", "+1,850 Max HP for melee boss proximity uptime", 2),
            CapabilityPerk("PDEF", "0 Pts (Skip)", "Maximizes offensive speedrun clear timer", 0),
            CapabilityPerk("MDEF", "0 Pts (Skip)", "Maximizes offensive speedrun clear timer", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "ATK (6 Pts • Max First)", "Max ATK immediately to maximize the 8-stack Pyre detonation burst on Scorchhowl.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "BREAK (4 Pts • Second)", "Invest points 7–10 into BREAK to melt Frost Titan Glacis ice shields in one rotation.", "2nd Max (Shred)"),
            PointInvestmentStep("Point 11 -> 13", "REGEN (3 Pts • Third)", "Invest points 11–13 into REGEN to maintain 100% Cinder Pounce uptime.", "3rd (Uptime)"),
            PointInvestmentStep("Point 14 -> 15", "HP (2 Pts • Final)", "Invest final 2 points into HP to survive melee frost aura ticks.", "4th (Safety)")
        ),
        strategyGuide = "Grizbo (Bailite Crest) shatters boss armor, Cornet (Capacitous Battery) buffs attack speed, Infergon (Ferocious Fang) applies Fire Vulnerability, and Scorchhowl (Fission Needles) rapidly stacks 8 Pyre to detonate.",
        estimatedDpsOrWinRate = "171,000 DPS • 00:44 Frost Titan Glacis Clear",
        author = "InfernalHound",
        upvotes = 478
    ),

    TopRatedTeam(
        id = "team_scorchhowl_egg_heist",
        title = "Caldera Smoke Heist Crew",
        mode = TeamMode.EGG_HEIST_PVE,
        primaryAniimoId = "scorchhowl",
        primaryAniimoName = "Scorchhowl",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "scorchhowl",
                aniimoName = "Scorchhowl",
                role = "Slot 1 • Flame Dash Egg Carrier",
                element = Element.FIRE,
                heldItem = "Wind Feather (+15)",
                heldItemEffect = "+28% Sprint Speed & Reduced Egg Weight",
                awakeningOrder = "REGEN > HP > PDEF > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "sherro",
                aniimoName = "Sherro",
                role = "Slot 2 • Dust Screen & Burrow Escort",
                element = Element.EARTH,
                heldItem = "Camouflage Leaf (+15)",
                heldItemEffect = "-45% Nest Threat & Smoke Concealment",
                awakeningOrder = "REGEN > HP > PDEF > BREAK"
            ),
            TeamMemberBuild(
                aniimoId = "cornet",
                aniimoName = "Cornet",
                role = "Slot 3 • Tailwind Speed Booster",
                element = Element.WIND,
                heldItem = "Swift Boots (+15)",
                heldItemEffect = "+22% Party Extraction Movement Speed",
                awakeningOrder = "REGEN > HP > MDEF > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 4 • Nest Boss Blocker",
                element = Element.EARTH,
                heldItem = "Thick Fur Plate (+15)",
                heldItemEffect = "+2,400 HP & Alpha Aggro Lock",
                awakeningOrder = "HP > PDEF > MDEF > BREAK"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("REGEN", "1st Priority (Max)", "+50% Stamina Regen for continuous Flame Dashes with an Egg", 6),
            CapabilityPerk("HP", "2nd Priority (High)", "+3,600 Max HP to prevent egg-drop stagger", 4),
            CapabilityPerk("MDEF", "3rd Priority", "+28% Magical Defense against volcanic fireballs", 3),
            CapabilityPerk("PDEF", "4th Priority", "+20% Physical Defense against caldera raptors", 2),
            CapabilityPerk("ATK", "0 Pts (Skip)", "Pure extraction speed and defense Awakening setup", 0),
            CapabilityPerk("BREAK", "0 Pts (Skip)", "Bypass guards using smoke and Tailwind sprint", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "REGEN (6 Pts • Max First)", "Max REGEN first so Scorchhowl can chain Flame Dashes across the caldera without stamina depletion.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "HP (4 Pts • Second)", "Invest points 7–10 into HP to prevent egg-drop thresholds.", "2nd Max (Bulk)"),
            PointInvestmentStep("Point 11 -> 13", "MDEF (3 Pts • Third)", "Add 3 points into MDEF to shrug off Magma Wyrm lava breath during extraction.", "3rd (MDEF)"),
            PointInvestmentStep("Point 14 -> 15", "PDEF (2 Pts • Final)", "Invest last 2 points into PDEF against pursuing pack mobs.", "4th (PDEF)")
        ),
        strategyGuide = "Grizbo (Thick Fur Plate) holds the boss, Sherro (Camouflage Leaf) drops dust cover, Cornet (Swift Boots) pops Tailwind, and Scorchhowl (Wind Feather) sprints out with maxed REGEN Awakening.",
        estimatedDpsOrWinRate = "97.2% Extraction Success • Volcanic Caldera",
        author = "HeistCommander",
        upvotes = 345
    ),

    // ====================================================
    // WHIRLBITE TEAMS (Whisperwake Isles • 4 Aniimo • 1 Held Item Each)
    // ====================================================
    TopRatedTeam(
        id = "team_whirlbite_dps_boss",
        title = "Whisperwake Tidal Break Quartet",
        mode = TeamMode.DPS,
        primaryAniimoId = "whirlbite",
        primaryAniimoName = "Whirlbite",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "whirlbite",
                aniimoName = "Whirlbite",
                role = "Slot 1 • Main Hydro Break & Burst Carry",
                element = Element.WATER,
                heldItem = "Fission Needles (+15)",
                heldItemEffect = "2x Bite Hits & +35% Armor Shred per Strike",
                awakeningOrder = "BREAK > ATK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "gachapus",
                aniimoName = "Gachapus",
                role = "Slot 2 • Hydro Resonance Sub-DPS",
                element = Element.WATER,
                heldItem = "Torrent Ring (+15)",
                heldItemEffect = "+28% Hydro Damage & Whirlpool Amp",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 3 • Electro-Charged Burst Finisher",
                element = Element.LIGHTNING,
                heldItem = "Pendant (+15 Contracted)",
                heldItemEffect = "+35% Electro Burst on Broken Targets",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "reefish",
                aniimoName = "Reefish",
                role = "Slot 4 • Whisperwake Aquatic Buffer",
                element = Element.WATER,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+28% Party Energy Recharge",
                awakeningOrder = "REGEN > HP > MDEF > PDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("BREAK", "1st Priority (Max)", "+48% Armor & Stagger Break efficiency to instantly collapse boss shields", 6),
            CapabilityPerk("ATK", "2nd Priority (High)", "+36% Attack Power for massive Rip Tide Jaws crits", 5),
            CapabilityPerk("REGEN", "3rd Priority", "+25% Energy Regen to keep Whirlpool active 100% of the fight", 3),
            CapabilityPerk("HP", "4th Priority", "+950 Max HP for close-range jaw combos", 1),
            CapabilityPerk("PDEF", "0 Pts (Skip)", "Offensive boss speedrun allocation", 0),
            CapabilityPerk("MDEF", "0 Pts (Skip)", "Offensive boss speedrun allocation", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "BREAK (6 Pts • Max First)", "Max BREAK first on Whirlbite so Rip Tide Jaws shatters Alpha Magma Wyrm and Kraken armor in 3.5 seconds.", "1st Max (Break)"),
            PointInvestmentStep("Point 7 -> 11", "ATK (5 Pts • Second)", "Invest points 7–11 into ATK to capitalize on the -50% defense Broken state.", "2nd Max (Damage)"),
            PointInvestmentStep("Point 12 -> 14", "REGEN (3 Pts • Third)", "Put points 12–14 into REGEN for back-to-back Whirlpool casts.", "3rd (Energy)"),
            PointInvestmentStep("Point 15", "HP (1 Pt • Final)", "Place the 15th point into HP for safety against boss tail sweeps.", "4th (Safety)")
        ),
        strategyGuide = "Reefish (Capacitous Battery) fuels party energy, Whirlbite (Fission Needles) with maxed BREAK Awakening collapses the boss armor bar, and Gachapus (Torrent Ring) + Fulmintis (Pendant) unload into the broken boss.",
        estimatedDpsOrWinRate = "176,500 DPS • 00:39 Whisperwake Record",
        author = "WhisperwakeKing",
        upvotes = 519
    ),

    // ====================================================
    // GRIZBO TEAMS (Breezy Plains Highlands • 4 Aniimo • 1 Held Item Each)
    // ====================================================
    TopRatedTeam(
        id = "team_grizbo_dps_boss",
        title = "Seismic Colossus Shatter Squad",
        mode = TeamMode.DPS,
        primaryAniimoId = "grizbo",
        primaryAniimoName = "Grizbo",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 1 • Earth Break & HP-Scaling Carry",
                element = Element.EARTH,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Crit DMG on Tectonic Boulder Slams",
                awakeningOrder = "BREAK > ATK > HP > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "bailite",
                aniimoName = "Bailite",
                role = "Slot 2 • Sub-DPS Rock Breaker",
                element = Element.EARTH,
                heldItem = "Bailite Crest (+15)",
                heldItemEffect = "+30% Earth Damage & Resonance Stagger",
                awakeningOrder = "BREAK > ATK > PDEF > HP"
            ),
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 3 • Conductive Burst Finisher",
                element = Element.LIGHTNING,
                heldItem = "Pendant (+15 Contracted)",
                heldItemEffect = "+35% Burst Skill Damage during Knockdown",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "fenmane",
                aniimoName = "Fenmane",
                role = "Slot 4 • Team Attack & Crit Roar Buffer",
                element = Element.GRASS,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+28% Team Energy Generation",
                awakeningOrder = "REGEN > HP > PDEF > MDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("BREAK", "1st Priority (Max)", "+48% Earth Break efficiency to knock down Alpha Thunder Sovereign", 5),
            CapabilityPerk("ATK", "2nd Priority (High)", "+36% Attack Power on Boulder Crash slams", 5),
            CapabilityPerk("HP", "3rd Priority", "+2,800 Max HP (scales Grizbo's Granite Bulwark passive damage)", 3),
            CapabilityPerk("PDEF", "4th Priority", "+20% Physical Defense for uninterruptible slam charging", 2),
            CapabilityPerk("MDEF", "0 Pts (Skip)", "Earth elemental shield absorbs lightning strikes", 0),
            CapabilityPerk("REGEN", "0 Pts (Skip)", "Fenmane with Capacitous Battery supplies team energy", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 5", "BREAK (5 Pts • 1st Priority)", "Invest points 1–5 into BREAK so Grizbo and Bailite trigger double boss knockdowns.", "1st Max (Break)"),
            PointInvestmentStep("Point 6 -> 10", "ATK (5 Pts • 2nd Priority)", "Invest points 6–10 into ATK for massive Ferocious Fang critical slams.", "2nd Max (Damage)"),
            PointInvestmentStep("Point 11 -> 13", "HP (3 Pts • 3rd Priority)", "Put points 11–13 into HP because Grizbo converts bonus Max HP directly into bonus Earth damage.", "3rd (HP Scaling)"),
            PointInvestmentStep("Point 14 -> 15", "PDEF (2 Pts • Final)", "Invest final 2 points into PDEF to face-tank boss slams without losing charge posture.", "4th (Poise)")
        ),
        strategyGuide = "Fenmane (Capacitous Battery) roars to buff team stats, Grizbo (Ferocious Fang) and Bailite (Bailite Crest) with maxed BREAK + ATK + HP Awakening shatter the boss bar, and Fulmintis (Pendant) finishes.",
        estimatedDpsOrWinRate = "162,000 DPS • 00:40 Thunder Sovereign Clear",
        author = "EarthShaker",
        upvotes = 465
    ),

    // ====================================================
    // THORNBLADE TEAMS (4 Aniimo • 1 Held Item Each • Awakening Capabilities)
    // ====================================================
    TopRatedTeam(
        id = "team_thornblade_dps_boss",
        title = "Sword Dance Floral Flurry",
        mode = TeamMode.DPS,
        primaryAniimoId = "thornblade",
        primaryAniimoName = "Thornblade",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "thornblade",
                aniimoName = "Thornblade",
                role = "Slot 1 • Main Sword Dance Carry",
                element = Element.GRASS,
                heldItem = "Fission Needles (+15)",
                heldItemEffect = "Basic Attacks Hit Twice at +75% Sword Dance ATK",
                awakeningOrder = "ATK > REGEN > BREAK > HP"
            ),
            TeamMemberBuild(
                aniimoId = "irisalis",
                aniimoName = "Irisalis",
                role = "Slot 2 • Floral Clone Dual-Hit Buffer",
                element = Element.GRASS,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Critical Strike Damage Aura",
                awakeningOrder = "REGEN > ATK > HP > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "bailite",
                aniimoName = "Bailite",
                role = "Slot 3 • Armor Shred & Break Support",
                element = Element.EARTH,
                heldItem = "Bailite Crest (+15)",
                heldItemEffect = "+30% Boss Break Gauge Efficiency",
                awakeningOrder = "BREAK > HP > PDEF > ATK"
            ),
            TeamMemberBuild(
                aniimoId = "fenmane",
                aniimoName = "Fenmane",
                role = "Slot 4 • Grass Synergy & Energy Battery",
                element = Element.GRASS,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "+28% Team Energy Recharge",
                awakeningOrder = "REGEN > HP > ATK > PDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (Max)", "+42% Attack Power multiplying all 5 Sword Dance stacks", 6),
            CapabilityPerk("REGEN", "2nd Priority (High)", "+32% Energy & Stamina Regen to maintain 5 Sword Dance stacks permanently", 4),
            CapabilityPerk("BREAK", "3rd Priority", "+26% Break efficiency on rapid dual-hit basic slashes", 3),
            CapabilityPerk("HP", "4th Priority", "+1,850 Max HP for melee pocket survival", 2),
            CapabilityPerk("PDEF", "0 Pts (Skip)", "Relies on Parry frames and floral clones", 0),
            CapabilityPerk("MDEF", "0 Pts (Skip)", "Relies on Parry frames and floral clones", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "ATK (6 Pts • Max First)", "Max ATK first so every Fission Needles double-slash hits for maximum critical damage.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "REGEN (4 Pts • Second)", "Invest points 7–10 into REGEN so Thornblade never drops its 5 Sword Dance stacks.", "2nd Max (Stacks)"),
            PointInvestmentStep("Point 11 -> 13", "BREAK (3 Pts • Third)", "Add 3 points into BREAK because Fission Needles double-hits apply Break twice per swing.", "3rd (Break)"),
            PointInvestmentStep("Point 14 -> 15", "HP (2 Pts • Final)", "Invest final 2 points into HP for melee safety.", "4th (Safety)")
        ),
        strategyGuide = "Fenmane (Capacitous Battery) and Irisalis (Ferocious Fang) set up floral clones, Bailite (Bailite Crest) breaks boss poise, and Thornblade (Fission Needles) unleashes 5-stack Sword Dance flurries.",
        estimatedDpsOrWinRate = "166,000 DPS • 00:46 Nether Colossus Clear",
        author = "FloralBladeworks",
        upvotes = 431
    ),

    // ====================================================
    // MALEVSERA TEAMS (4 Aniimo • 1 Held Item Each • Awakening Capabilities)
    // ====================================================
    TopRatedTeam(
        id = "team_malevsera_pvp_team",
        title = "Abyssal Hex 4-Man Annihilation",
        mode = TeamMode.PVP_TEAM,
        primaryAniimoId = "malevsera",
        primaryAniimoName = "Malevsera",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "malevsera",
                aniimoName = "Malevsera",
                role = "Slot 1 • Shadow Hex & Silence Controller",
                element = Element.DARK,
                heldItem = "Void Shard (+15)",
                heldItemEffect = "+35% AoE Silence Duration & Hex Vulnerability",
                awakeningOrder = "REGEN > ATK > HP > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "glynsera",
                aniimoName = "Glynsera",
                role = "Slot 2 • Phantasm Fear & Dark Sub-DPS",
                element = Element.DARK,
                heldItem = "Pendant (+15 Contracted)",
                heldItemEffect = "+35% Burst Damage on Hexed Targets",
                awakeningOrder = "ATK > REGEN > HP > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "fulmintis",
                aniimoName = "Fulmintis",
                role = "Slot 3 • Electro Cleanup Assassin",
                element = Element.LIGHTNING,
                heldItem = "Ferocious Fang (+15)",
                heldItemEffect = "+35% Crit DMG Finisher",
                awakeningOrder = "ATK > HP > REGEN > PDEF"
            ),
            TeamMemberBuild(
                aniimoId = "irisalis",
                aniimoName = "Irisalis",
                role = "Slot 4 • Cleanse & Sustain Support",
                element = Element.GRASS,
                heldItem = "Life Blossom (+15)",
                heldItemEffect = "+30% Party Healing & CC Removal",
                awakeningOrder = "HP > REGEN > PDEF > MDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("REGEN", "1st Priority (Max)", "+42% Energy Regen to cast Abyssal Silence before the enemy team can act", 5),
            CapabilityPerk("ATK", "2nd Priority (High)", "+36% Attack Power to amplify Void Singularity burst", 4),
            CapabilityPerk("HP", "3rd Priority", "+2,800 Max HP to survive enemy dive assassins", 3),
            CapabilityPerk("MDEF", "4th Priority", "+20% Magical Defense against enemy mirror casters", 2),
            CapabilityPerk("PDEF", "5th Priority", "+14% Physical Defense against physical flankers", 1),
            CapabilityPerk("BREAK", "0 Pts (Skip)", "PvP hex control team relies on Silence locks rather than boss break bars", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 5", "REGEN (5 Pts • 1st Priority)", "Max REGEN first so Malevsera always wins the opening AoE Silence speed tie in PvP.", "1st Max (Speed)"),
            PointInvestmentStep("Point 6 -> 9", "ATK (4 Pts • 2nd Priority)", "Invest points 6–9 into ATK so Void Singularity + Glynsera Pendant deletes hexed targets.", "2nd Max (Burst)"),
            PointInvestmentStep("Point 10 -> 12", "HP (3 Pts • 3rd Priority)", "Put points 10–12 into HP to survive enemy burst counters.", "3rd (Bulk)"),
            PointInvestmentStep("Point 13 -> 15", "MDEF (2 Pts) & PDEF (1 Pt)", "Round out points 13–15 with MDEF and PDEF for arena durability.", "4th (Defense)")
        ),
        strategyGuide = "Malevsera (Void Shard) with maxed REGEN Awakening opens with instant AoE Silence, Glynsera (Pendant) chains Phantasm Fear, Fulmintis (Ferocious Fang) cleans up, and Irisalis (Life Blossom) sustains.",
        estimatedDpsOrWinRate = "92.4% Win Rate • Legend Arena 4-Squad",
        author = "AbyssEmperor",
        upvotes = 504
    ),

    // ====================================================
    // STELLARYS TEAMS (4 Aniimo • 1 Held Item Each • Awakening Capabilities)
    // ====================================================
    TopRatedTeam(
        id = "team_stellarys_dps_boss",
        title = "Supernova Astral 4-Man Hypercarry",
        mode = TeamMode.DPS,
        primaryAniimoId = "stellarys",
        primaryAniimoName = "Stellarys",
        members = listOf(
            TeamMemberBuild(
                aniimoId = "stellarys",
                aniimoName = "Stellarys",
                role = "Slot 1 • Main Cosmic Burst Carry",
                element = Element.LIGHT,
                heldItem = "Supernova Shard (+15)",
                heldItemEffect = "+38% Astral Beam Crit DMG during Boss Break",
                awakeningOrder = "ATK > BREAK > REGEN > HP"
            ),
            TeamMemberBuild(
                aniimoId = "dazmand",
                aniimoName = "Dazmand",
                role = "Slot 2 • Light Prism Crit Buffer",
                element = Element.LIGHT,
                heldItem = "Light Prism (+15)",
                heldItemEffect = "+25% Party Light Damage & +20% Crit Rate",
                awakeningOrder = "REGEN > ATK > HP > MDEF"
            ),
            TeamMemberBuild(
                aniimoId = "grizbo",
                aniimoName = "Grizbo",
                role = "Slot 3 • Heavy Poise & Shield Breaker",
                element = Element.EARTH,
                heldItem = "Bailite Crest (+15)",
                heldItemEffect = "+35% Boss Armor Break Speed",
                awakeningOrder = "BREAK > HP > PDEF > ATK"
            ),
            TeamMemberBuild(
                aniimoId = "irisalis",
                aniimoName = "Irisalis",
                role = "Slot 4 • Energy Well & Clone Buffer",
                element = Element.GRASS,
                heldItem = "Capacitous Battery (+15)",
                heldItemEffect = "Instant Team Energy Refill on Boss Break",
                awakeningOrder = "REGEN > HP > MDEF > PDEF"
            )
        ),
        capabilities = listOf(
            CapabilityPerk("ATK", "1st Priority (Max)", "+42% Attack Power for 200k+ Cosmic Singularity beams", 6),
            CapabilityPerk("BREAK", "2nd Priority (High)", "+35% Break efficiency against Abyssal Void shields", 4),
            CapabilityPerk("REGEN", "3rd Priority", "+25% Energy Regen to fire double Supernova beams per break", 3),
            CapabilityPerk("HP", "4th Priority", "+950 Max HP for raid boss survivability", 1),
            CapabilityPerk("MDEF", "5th Priority", "+14% Magical Defense against Nether Colossus lasers", 1),
            CapabilityPerk("PDEF", "0 Pts (Skip)", "Ranged cosmic positioning avoids physical cleaves", 0)
        ),
        pointInvestmentOrder = listOf(
            PointInvestmentStep("Point 1 -> 6", "ATK (6 Pts • Max First)", "Put all first 6 points into ATK to maximize Stellarys Supernova Shard scaling.", "1st Max (Core)"),
            PointInvestmentStep("Point 7 -> 10", "BREAK (4 Pts • Second)", "Invest points 7–10 into BREAK to shred dark shields alongside Grizbo.", "2nd Max (Break)"),
            PointInvestmentStep("Point 11 -> 13", "REGEN (3 Pts • Third)", "Invest points 11–13 into REGEN to recharge Cosmic Singularity before Break ends.", "3rd (Energy)"),
            PointInvestmentStep("Point 14 -> 15", "HP (1 Pt) & MDEF (1 Pt)", "Put final 2 points into HP and MDEF to survive room-wide void pulses.", "4th (Safety)")
        ),
        strategyGuide = "Grizbo (Bailite Crest) breaks the boss, Irisalis (Capacitous Battery) refills energy, Dazmand (Light Prism) buffs Light Crit, and Stellarys (Supernova Shard) fires back-to-back Cosmic Beams.",
        estimatedDpsOrWinRate = "188,000 DPS • 00:36 Abyss Colossus Clear",
        author = "AstralSovereign",
        upvotes = 567
    )
)
