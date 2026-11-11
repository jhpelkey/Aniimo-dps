package com.example.data.repository

import com.example.data.model.Aniimo
import com.example.data.model.AniimoSkill
import com.example.data.model.Element
import com.example.data.model.EvolutionStage
import com.example.data.model.ItemSlot
import com.example.data.model.PartnerSynergy
import com.example.data.model.RecommendedItem
import com.example.data.model.SkillType

/**
 * Canonical Aniilog Catalog Part 1: No. 001 to No. 025
 * Includes Fire starters, Dark/Celestial, Wind/Aero, Floral/Dance, Water/Tide, and Burrow lines.
 */
val aniimoCatalogPart1: List<Aniimo> = listOf(
    // No. 001: Emberpup (Lumin, Fire)
    Aniimo(
        id = "emberpup",
        name = "Emberpup",
        title = "Cinder Foxling",
        element = Element.FIRE,
        role = "Single-Target Burn & Flank DPS",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 001",
        evolvesToIds = listOf("flameruff"),
        evolutionRequirement = "Reaches Lv. 16",
        baseAtk = 840,
        baseDps = 14200,
        maxOptimizedDps = 58000,
        critRate = 22.0f,
        critDmg = 150.0f,
        attackSpeed = 1.35f,
        overview = "No. 001 in the Aniilog. The energetic Fire-type Lumin stage starter. Playfully follows elder Flameruffs across volcanic ridges, delivering quick fire nips that kindle Pyre Stacks.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "emb_s1",
                name = "Ember Nibble",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Fire Physical",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 52000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Quick fire bite that inflicts Burning on contact for 4 seconds.",
                dpsSynergyEffect = "Builds +2 Pyre Stacks for evolution scaling."
            ),
            AniimoSkill(
                id = "emb_s2",
                name = "Flame Dash Sprint",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Fire Dash",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 58000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +20% Swift Flow & 560% Fire Strike",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Dashes through enemies leaving a searing trail of ash. Enhanced by Maxed Ferocious Fang (+15) for guaranteed critical hits.",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees critical strikes on burning foes."
            ),
            AniimoSkill(
                id = "emb_pass",
                name = "Warm Fur",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Fire Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grants +15% Fire damage while above 70% HP.",
                dpsSynergyEffect = "Transfers into Flameruff's Unbroken Spirit upon evolution."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("flameruff", "Flameruff", Element.FIRE, "S+", 32, "Pack Pyre", "Doubles burn tick rate.", "+18% Fire DMG", "Swap to Flameruff to detonate burns."),
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S", 25, "Radiant Spark", "Increases crit chance by 20%.", "+15% Crit Rate", "Cast Flame Dash inside Dazmand's aura.")
        ),
        bestItems = listOf(
            RecommendedItem("item_emb_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+40% ATK", "Flame Dash Sprint deals +25% burn damage.", "+22% Total DPS", true, "Ridge Alpha Drops")
        ),
        statPriorities = listOf("Fire DMG%", "Crit Rate", "ATK%"),
        rotationGuide = listOf("1. Apply Ember Nibble.", "2. Use Flame Dash Sprint through burning targets.", "3. Swap to pack partner.")
    ),

    // No. 002: Flameruff (Gamma, Fire)
    Aniimo(
        id = "flameruff",
        name = "Flameruff",
        title = "Blazing Ridge Hound",
        element = Element.FIRE,
        role = "Break & Dual-Path Pyro DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 002",
        evolvesFromId = "emberpup",
        evolvesToIds = listOf("scorchhowl", "inferlupa"),
        evolutionRequirement = "Lv. 20 (Day: Scorchhowl / Night + Night Stone: Inferlupa)",
        baseAtk = 1350,
        baseDps = 28500,
        maxOptimizedDps = 112000,
        critRate = 26.0f,
        critDmg = 165.0f,
        attackSpeed = 1.48f,
        overview = "No. 002 in the Aniilog. The Gamma evolution of Emberpup. Possesses fierce fiery mane ruffs and branches into either Scorchhowl (Pure Fire) or Inferlupa (Dark/Fire) based on quest progression.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "fla_s1",
                name = "Searing Bite",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "380% Fire Impact",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 92000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Lunges forward with heated fangs, tearing through 20% enemy defense.",
                dpsSynergyEffect = "Reduces target fire resistance by 15%."
            ),
            AniimoSkill(
                id = "fla_s2",
                name = "Blazing Charge",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "610% Inferno Rush",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 112000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Burst Charge & 760% Fire Strike",
                enhancedDpsPerSec = 135000,
                description = "Core signature skill. Charges forward cloaked in a spiraling firestorm. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Triggers Break stagger."
            ),
            AniimoSkill(
                id = "fla_pass",
                name = "Unbroken Spirit",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% ATK Scaling",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 31000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "When HP drops below 50%, immediately refunds 15 EP and boosts attack speed by 25%.",
                dpsSynergyEffect = "Enables emergency burst rotations during boss encounters."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("scorchhowl", "Scorchhowl", Element.FIRE, "S+", 35, "Alpha Howl", "Multiplies fire damage by 30%.", "+25% Fire Crit", "Cast Blazing Charge then tag Scorchhowl."),
            PartnerSynergy("inferlupa", "Inferlupa", Element.DARK, "S+", 38, "Hellfire Pact", "Infuses burns with shadow curse.", "+30% Break DMG", "Coordinate Break window.")
        ),
        bestItems = listOf(
            RecommendedItem("item_fla_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Blazing Charge deals +30% Break damage.", "+26% Total DPS", true, "Alpha Ridge Drop")
        ),
        statPriorities = listOf("Fire DMG%", "Break Multiplier", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Shred defense with Searing Bite.", "2. Smash through boss armor with Blazing Charge.", "3. Tag into final evolution.")
    ),

    // No. 003: Scorchhowl (Nova, Fire)
    Aniimo(
        id = "scorchhowl",
        name = "Scorchhowl",
        title = "Apex Pyre Monarch",
        element = Element.FIRE,
        role = "Break & Pyro Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 003",
        evolvesFromId = "flameruff",
        baseAtk = 1890,
        baseDps = 44200,
        maxOptimizedDps = 171000,
        critRate = 32.0f,
        critDmg = 190.0f,
        attackSpeed = 1.62f,
        overview = "No. 003 in the Aniilog. The pure Fire-type Nova evolution of Flameruff. Renders battlefield terrain into scorched earth with seismic magma howls and blazing orbital projectiles.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "scorch_s1",
                name = "Molten Swipe",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Fire Physical",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Claws forward launching three waves of molten rock.",
                dpsSynergyEffect = "Generates 2 Pyre Orbs on hit."
            ),
            AniimoSkill(
                id = "scorch_s2",
                name = "Blazing Roar of Ruin",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Magma Eruption",
                cooldownSec = 9,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 171000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Pyro Amp & 820% Cataclysm Howl",
                enhancedDpsPerSec = 205000,
                description = "Core signature skill. Howls furiously to call down volcanic pillars that vaporize enemy poise bars. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Melts boss Break meters."
            ),
            AniimoSkill(
                id = "scorch_ult",
                name = "Inferno Cataclysm",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1620% Fire Deluge",
                cooldownSec = 18,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 158000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Ignites the entire arena in a massive sea of flames.",
                dpsSynergyEffect = "Deals double damage against boss Break states."
            ),
            AniimoSkill(
                id = "scorch_pass",
                name = "Pyre Dominance",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Fire Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "While standing in scorched earth, basic attacks explode for area fire splash.",
                dpsSynergyEffect = "Permits continuous sustained damage."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 38, "Solar Flash", "Multiplies team fire & radiant crit by 40%.", "+30% Crit Multiplier", "Deploy Dazmand aura before Blazing Roar.")
        ),
        bestItems = listOf(
            RecommendedItem("item_scorch_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Blazing Roar deals +35% damage.", "+34% Total DPS", true, "Volcano Alpha")
        ),
        statPriorities = listOf("Fire DMG%", "Crit Rate", "Crit DMG", "Break Multiplier"),
        rotationGuide = listOf("1. Molten Swipe to seed Pyre Orbs.", "2. Blazing Roar of Ruin during boss stagger.", "3. Detonate with Inferno Cataclysm.")
    ),

    // No. 004: Inferlupa (Nova, Dark/Fire)
    Aniimo(
        id = "inferlupa",
        name = "Inferlupa",
        title = "Underworld Hellhound",
        element = Element.DARK,
        secondaryElement = Element.FIRE,
        role = "Break & Hellfire Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 004",
        evolvesFromId = "flameruff",
        baseAtk = 1940,
        baseDps = 45500,
        maxOptimizedDps = 175000,
        critRate = 33.5f,
        critDmg = 192.0f,
        attackSpeed = 1.66f,
        overview = "No. 004 in the Aniilog. The alternative Dark/Fire branch evolution of Flameruff unlocked via the Warrior Journey sidequest. Taps into abyssal nightflames to deliver catastrophic Break damage.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "lupa_s1",
                name = "Cerberus Triple Bite",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Dark/Fire Strike",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 124000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Executes rapid triple phantom bite attacks infusing targets with Abyssal Flame.",
                dpsSynergyEffect = "Shreds 25% Dark and Fire defense for 10 seconds."
            ),
            AniimoSkill(
                id = "lupa_s2",
                name = "Hellfire Pounce",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Nether Flame Blast",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 175000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Dark Flame & 850% Abyssal Strike",
                enhancedDpsPerSec = 210000,
                description = "Core signature skill. Leaps from shadows to detonate all Abyssal Flame stacks. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Deals 1.5x damage against boss Break gauges."
            ),
            AniimoSkill(
                id = "lupa_pass",
                name = "Underworld Dominion",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage vs Debuffed",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases all damage dealt to burned or cursed enemies by 30%.",
                dpsSynergyEffect = "Multiplies total party DPS during boss stagger windows."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 40, "Plasma Abyssal", "Electrified flames trigger continuous explosions.", "+35% Burst DMG", "Alternate between Hellfire Pounce and Light Blade.")
        ),
        bestItems = listOf(
            RecommendedItem("item_lupa_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Hellfire Pounce deals 35% extra damage.", "+36% Total DPS", true, "Night Ridge Alpha")
        ),
        statPriorities = listOf("Dark DMG%", "Fire DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Triple Bite to shred defense.", "2. Hellfire Pounce to detonate stacks.", "3. Cleanse with Underworld Dominion.")
    ),

    // No. 005: Celestis (Lumin, Dark)
    Aniimo(
        id = "celestis",
        name = "Celestis",
        title = "Twilight Stargazer",
        element = Element.DARK,
        role = "Cosmic Dark & Debuff Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 005",
        evolvesToIds = listOf("stellarys"),
        evolutionRequirement = "Lv. 24 Cosmic Resonance",
        baseAtk = 910,
        baseDps = 16200,
        maxOptimizedDps = 64000,
        critRate = 24.0f,
        critDmg = 155.0f,
        attackSpeed = 1.38f,
        overview = "No. 005 in the Aniilog. The Lumin base stage of the Stellarys cosmic line. Gathers fallen star dust at twilight, casting glowing orbs that disrupt enemy movements.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "cel_s1",
                name = "Gleam Ray",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Dark Starlight",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 54000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a concentrated beam of violet astral energy.",
                dpsSynergyEffect = "Slows target movement speed by 25%."
            ),
            AniimoSkill(
                id = "cel_s2",
                name = "Cosmic Burst",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "470% Nova Pulse",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 64000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Pendant (+15 Contract)",
                enhancedMultiplier = "Grants +20% Astral Rate & 590% Dark Burst",
                enhancedDpsPerSec = 76000,
                description = "Core signature skill. Expands a miniature dark constellation that implodes. Enhanced by Maxed Pendant (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Blinds and slows foes."
            ),
            AniimoSkill(
                id = "cel_pass",
                name = "Starlight Aura",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+12% Team Dark DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 25000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases team Dark and Light damage by 12%.",
                dpsSynergyEffect = "Transfers into Stellarys's Supernova."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("stellarys", "Stellarys", Element.DARK, "S+", 34, "Stellar Echo", "Doubles cosmic dust damage.", "+22% Dark DMG", "Build resonance with Celestis before swapping.")
        ),
        bestItems = listOf(
            RecommendedItem("item_cel_pend", "Pendant (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Cosmic Burst expands 30% wider.", "+22% DPS", true, "Astral Sanctum")
        ),
        statPriorities = listOf("Dark DMG%", "Crit Rate", "Energy Regen"),
        rotationGuide = listOf("1. Fire Gleam Ray.", "2. Implode with Cosmic Burst.", "3. Tag into Stellarys.")
    ),

    // No. 006: Stellarys (Nova, Dark)
    Aniimo(
        id = "stellarys",
        name = "Stellarys",
        title = "Nebula Void Shaper",
        element = Element.DARK,
        role = "Cosmic Blackhole & Continuous DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 006",
        evolvesFromId = "celestis",
        baseAtk = 1840,
        baseDps = 41200,
        maxOptimizedDps = 162000,
        critRate = 30.0f,
        critDmg = 182.0f,
        attackSpeed = 1.58f,
        overview = "No. 006 in the Aniilog. The Nova evolution of Celestis. Bends light and shadow into cosmic singularities, sucking enemies into gravitational crushing fields.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "ste_s1",
                name = "Singularity Pulse",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "380% Dark Graviton",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 114000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a graviton projectile that pulls smaller foes together.",
                dpsSynergyEffect = "Applies 15% Vulnerability."
            ),
            AniimoSkill(
                id = "ste_s2",
                name = "Event Horizon Collapse",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "640% Blackhole Tear",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 162000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +25% Dark Surge & 790% Collapse",
                enhancedDpsPerSec = 192000,
                description = "Core signature skill. Tears open a micro-blackhole that continuously deals ticking dark damage. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Ticks 8 times over 4 seconds."
            ),
            AniimoSkill(
                id = "ste_ult",
                name = "Supernova Eclipse",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1560% Astral Burst",
                cooldownSec = 17,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 151000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Detonates the singularity into a blinding cosmic explosion.",
                dpsSynergyEffect = "Cleanses enemy buffs and guarantees crit."
            ),
            AniimoSkill(
                id = "ste_pass",
                name = "Gravitational Maw",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Damage vs Grouped",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 39000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +20% increased damage against targets caught inside vortexes.",
                dpsSynergyEffect = "Maximized by Whirlbite and Somniwing."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Event Storm", "Singularity groups enemies for Lightning cleave.", "+30% Area DMG", "Vortex then lightning slash.")
        ),
        bestItems = listOf(
            RecommendedItem("item_ste_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Event Horizon ticks 2 extra times.", "+32% DPS", true, "Void Domain")
        ),
        statPriorities = listOf("Dark DMG%", "Crit Rate", "Crit DMG", "Cooldown Reduction"),
        rotationGuide = listOf("1. Group with Singularity Pulse.", "2. Cast Event Horizon Collapse.", "3. Detonate with Supernova Eclipse.")
    ),

    // No. 007: Chirpi (Lumin, Wind)
    Aniimo(
        id = "chirpi",
        name = "Chirpi",
        title = "Breeze Whistler",
        element = Element.WIND,
        role = "Wind Speed & Song Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 007",
        evolvesToIds = listOf("tromber", "cornet"),
        evolutionRequirement = "Lv. 18 Wind Feather",
        baseAtk = 810,
        baseDps = 13800,
        maxOptimizedDps = 54000,
        critRate = 21.0f,
        critDmg = 145.0f,
        attackSpeed = 1.45f,
        overview = "No. 007 in the Aniilog. The Lumin songbird starter. Flits gracefully on coastal updrafts, trilling melodic gusts that empower allies with swiftness and haste.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "chi_s1",
                name = "Zephyr Chime",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "270% Wind Song",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 48000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Emits a sharp musical chime that knocks back airborne foes.",
                dpsSynergyEffect = "Grants +10% attack speed to party."
            ),
            AniimoSkill(
                id = "chi_s2",
                name = "Tornado Note",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "420% Aero Whirl",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 54000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Windstride Feather (+15)",
                enhancedMultiplier = "Grants +25% Speed & 520% Gust Strike",
                enhancedDpsPerSec = 66000,
                description = "Core signature skill. Spins rapidly to create a mini cyclone. Enhanced by Maxed Windstride Feather (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Boosts team movement."
            ),
            AniimoSkill(
                id = "chi_pass",
                name = "Wind Cadence",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Haste",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 21000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Reduces team cooldowns by 10% after singing.",
                dpsSynergyEffect = "Transfers into Tromber and Cornet."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("cornet", "Cornet", Element.WIND, "S+", 32, "Wind Choir", "Doubles gust frequency.", "+20% Wind DMG", "Sing then swap to Cornet.")
        ),
        bestItems = listOf(
            RecommendedItem("item_chi_feath", "Windstride Feather (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Tornado Note duration +2s.", "+20% DPS", true, "Gale Ridge")
        ),
        statPriorities = listOf("Wind DMG%", "Attack Speed", "Cooldown Reduction"),
        rotationGuide = listOf("1. Sing Zephyr Chime for haste.", "2. Cast Tornado Note.", "3. Tag into Nova partner.")
    ),

    // No. 008: Tromber (Nova, Wind/Lightning)
    Aniimo(
        id = "tromber",
        name = "Tromber",
        title = "Thunder Gale Striker",
        element = Element.LIGHTNING,
        role = "Thunderstorm & Chain DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 008",
        evolvesFromId = "chirpi",
        baseAtk = 1810,
        baseDps = 40500,
        maxOptimizedDps = 158000,
        critRate = 29.0f,
        critDmg = 178.0f,
        attackSpeed = 1.55f,
        overview = "No. 008 in the Aniilog. The thunderous Nova evolution of Chirpi. Replaces delicate song with thunderous resonance chords that blast static shockwaves across the field.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "tro_s1",
                name = "Resonance Shock",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "360% Chain Lightning",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 108000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires sonic lightning arcs that jump between 4 nearby enemies.",
                dpsSynergyEffect = "Electrifies targets for 6 seconds."
            ),
            AniimoSkill(
                id = "tro_s2",
                name = "Storm Brass Crescendo",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "620% Thunder Sonic",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 158000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Capacitous Battery (+15)",
                enhancedMultiplier = "Grants +25% Static Arc & 760% Sonic Blast",
                enhancedDpsPerSec = 188000,
                description = "Core signature skill. Blasts a focused cone of ultrasonic thunder that stuns and shreds poise. Enhanced by Maxed Capacitous Battery (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees Break interrupts."
            ),
            AniimoSkill(
                id = "tro_ult",
                name = "Thunder Symphony",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1480% Lightning Burst",
                cooldownSec = 16,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 145000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Calls down a symphony of lightning strikes across all electrified foes.",
                dpsSynergyEffect = "Refunds 20 EP upon hitting 3+ targets."
            ),
            AniimoSkill(
                id = "tro_pass",
                name = "Static Resonator",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Lightning DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 38000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Whenever an ally inflicts Lightning damage, Tromber fires an auxiliary sonic arc.",
                dpsSynergyEffect = "Perfect synergy with Fulmintis."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Dual Lightning Harmony", "Synchronizes electric stacks for continuous chain lightning.", "+30% Lightning DMG", "Cast Crescendo then let Fulmintis slash.")
        ),
        bestItems = listOf(
            RecommendedItem("item_tro_batt", "Capacitous Battery (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Crescendo refunds 12 EP on Break.", "+30% DPS", true, "Thunder Sanctum")
        ),
        statPriorities = listOf("Lightning DMG%", "Crit Rate", "Crit DMG", "Break Efficiency"),
        rotationGuide = listOf("1. Electrify with Resonance Shock.", "2. Stagger with Storm Brass Crescendo.", "3. Conclude with Thunder Symphony.")
    ),

    // No. 009: Cornet (Nova, Wind)
    Aniimo(
        id = "cornet",
        name = "Cornet",
        title = "Gale Horn Sovereign",
        element = Element.WIND,
        role = "Aero Sonic & Piercing DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 009",
        evolvesFromId = "chirpi",
        baseAtk = 1860,
        baseDps = 42500,
        maxOptimizedDps = 165000,
        critRate = 31.0f,
        critDmg = 185.0f,
        attackSpeed = 1.60f,
        overview = "No. 009 in the Aniilog. The elegant Wind-type Nova evolution of Chirpi. Channels piercing supersonic wind gusts through spiraled gale horns to slice through heavy boss armor.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "cor_s1",
                name = "Aero Piercer",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "390% Wind Pierce",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 115000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a razor-sharp wind spear that ignores 30% of target defense.",
                dpsSynergyEffect = "Applies Wind Vulnerability."
            ),
            AniimoSkill(
                id = "cor_s2",
                name = "Cyclone Clarion Blast",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "650% Supersonic Gale",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 165000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Pierce & 800% Gale Lance",
                enhancedDpsPerSec = 195000,
                description = "Core signature skill. Blasts a devastating vortex beam directly forward. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Cleaves in a linear 30m path."
            ),
            AniimoSkill(
                id = "cor_ult",
                name = "Grand Tempest Fanfare",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1540% Hurricane Deluge",
                cooldownSec = 17,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 149000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Summons three orbiting tornadoes that lift targets and shred armor.",
                dpsSynergyEffect = "Grants whole team +25% wind damage."
            ),
            AniimoSkill(
                id = "cor_pass",
                name = "Harmonic Draft",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+18% Wind Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 40000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grants +18% damage when attacking from medium-to-long distance.",
                dpsSynergyEffect = "Encourages safe ranged kiting."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("somniwing", "Somniwing", Element.WIND, "S+", 36, "Aero Tempest", "Somniwing EP generation fuels continuous Clarion blasts.", "+28% Wind DPS", "Rotate between Clarion and Gale Battery.")
        ),
        bestItems = listOf(
            RecommendedItem("item_cor_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Clarion Blast deals 35% extra damage.", "+33% Total DPS", true, "Summit Alpha")
        ),
        statPriorities = listOf("Wind DMG%", "Crit Rate", "Crit DMG", "Range Boost"),
        rotationGuide = listOf("1. Cast Aero Piercer to shred defense.", "2. Unleash Cyclone Clarion Blast.", "3. Follow up with Grand Tempest Fanfare.")
    ),

    // No. 010: Tubster (Nova, Wind)
    Aniimo(
        id = "tubster",
        name = "Tubster",
        title = "Gale Barrel Bruiser",
        element = Element.WIND,
        role = "Wind Break & Poise Crusher",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 010",
        baseAtk = 1830,
        baseDps = 41500,
        maxOptimizedDps = 160000,
        critRate = 28.5f,
        critDmg = 175.0f,
        attackSpeed = 1.50f,
        overview = "No. 010 in the Aniilog. The heavy-hitting standalone Wind-element breaker. Uses pressurized air barrels to propel itself into crushing aerial body slams that break Alpha boss super-armor.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "tub_s1",
                name = "Pneumatic Slam",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Wind Impact",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 110000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Pressurizes pneumatic barrels and slams the ground with gale force.",
                dpsSynergyEffect = "Knocks down non-boss enemies."
            ),
            AniimoSkill(
                id = "tub_s2",
                name = "Turbine Corkscrew",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "630% Aero Drill",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 160000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Poise Break & 780% Drill Impact",
                enhancedDpsPerSec = 190000,
                description = "Core signature skill. Launches forward spinning like a jet turbine. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 40% of boss Break bars."
            ),
            AniimoSkill(
                id = "tub_pass",
                name = "Heavy Wind Aerodynamics",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Break Multiplier",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 37000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grants +25% Break efficiency against staggered targets.",
                dpsSynergyEffect = "Sets up rapid speedrun clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 37, "Break & Burst", "Tubster breaks shields, Fulmintis executes.", "+35% Boss Damage", "Swap when boss poise reaches zero.")
        ),
        bestItems = listOf(
            RecommendedItem("item_tub_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Turbine Corkscrew deals +30% Break damage.", "+31% Total DPS", true, "Plateau Alpha")
        ),
        statPriorities = listOf("Break Efficiency", "Wind DMG%", "ATK%", "Crit Rate"),
        rotationGuide = listOf("1. Pressurize with Pneumatic Slam.", "2. Break boss with Turbine Corkscrew.", "3. Tag into burst DPS.")
    ),

    // No. 011: Iris (Lumin, Grass)
    Aniimo(
        id = "iris",
        name = "Iris",
        title = "Petal Sprite",
        element = Element.GRASS,
        role = "Floral Seed & Healing Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 011",
        evolvesToIds = listOf("irisalis"),
        evolutionRequirement = "Lv. 18 Blooming Pollen",
        baseAtk = 820,
        baseDps = 14000,
        maxOptimizedDps = 56000,
        critRate = 22.0f,
        critDmg = 148.0f,
        attackSpeed = 1.40f,
        overview = "No. 011 in the Aniilog. The Lumin stage flower dancer. Skips across lush meadows scattering fragrant pollen that heals companions while slowing approaching monsters.",
        faceDrawableResName = "img_face_irisalis",
        skills = listOf(
            AniimoSkill(
                id = "iri_s1",
                name = "Pollen Dust",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Grass Magic",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 50000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Throws a cloud of blinding floral pollen.",
                dpsSynergyEffect = "Slows targets by 20%."
            ),
            AniimoSkill(
                id = "iri_s2",
                name = "Floral Waltz",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "430% Petal Spin",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 56000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +20% Heal & 540% Petal Dance",
                enhancedDpsPerSec = 68000,
                description = "Core signature skill. Dances in circles releasing slicing rose leaves. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Heals nearby allies."
            ),
            AniimoSkill(
                id = "iri_pass",
                name = "Photosynthesis",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Regen",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively regenerates 2% HP every 3 seconds under sunlight.",
                dpsSynergyEffect = "Transfers into Irisalis's clone longevity."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("irisalis", "Irisalis", Element.GRASS, "S+", 34, "Floral Bloom", "Expands clone garden duration.", "+25% Grass DMG", "Seed field before evolving.")
        ),
        bestItems = listOf(
            RecommendedItem("item_iri_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Floral Waltz heals +50%.", "+20% DPS", true, "Rosetower Woods")
        ),
        statPriorities = listOf("Grass DMG%", "Heal Bonus", "Energy Regen"),
        rotationGuide = listOf("1. Dust with Pollen.", "2. Cast Floral Waltz.", "3. Tag into Irisalis.")
    ),

    // No. 012: Irisalis (Nova, Grass)
    Aniimo(
        id = "irisalis",
        name = "Irisalis",
        title = "Verdant Blossom Monarch",
        element = Element.GRASS,
        role = "Clone Swarm & Sustained DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 012",
        evolvesFromId = "iris",
        baseAtk = 1910,
        baseDps = 44800,
        maxOptimizedDps = 171000,
        critRate = 33.0f,
        critDmg = 190.0f,
        attackSpeed = 1.65f,
        overview = "No. 012 in the Aniilog. The majestic Nova evolution of Iris. Summons floral phantom clones that mimic all sword slashes, tripling party combat output inside blooming sanctuaries.",
        faceDrawableResName = "img_face_irisalis",
        skills = listOf(
            AniimoSkill(
                id = "iri_nova_s1",
                name = "Petal Phantom Clone",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Floral Slash",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 120000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Summons a floral clone that mirrors all basic and skill attacks for 12 seconds.",
                dpsSynergyEffect = "Clones deal 70% of Irisalis damage."
            ),
            AniimoSkill(
                id = "iri_nova_s2",
                name = "Verdant Sanctuary Bloom",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Flower Surge",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 171000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +30% Clone Atk & 830% Floral Strike",
                enhancedDpsPerSec = 205000,
                description = "Core signature skill. Creates a sacred floral domain that gives clones +50% attack speed. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Overdrives all active clones."
            ),
            AniimoSkill(
                id = "iri_nova_ult",
                name = "Hundred Petal Tempest",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1580% Botanical Swarm",
                cooldownSec = 18,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 155000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Command all clones to converge in a storm of razor petals.",
                dpsSynergyEffect = "Applies 30% Bleed to targets."
            ),
            AniimoSkill(
                id = "iri_nova_pass",
                name = "Symbiosis Overdrive",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Damage with 2+ Clones",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 43000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grants +25% total ATK while two or more petal clones are active.",
                dpsSynergyEffect = "Sustains massive continuous DPS."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("leafy", "Leafy", Element.GRASS, "S+", 38, "Verdant Harmony", "Doubles clone lifespan and boosts grass crit by 30%.", "+32% Grass DMG", "Deploy clones inside Verdant Sanctuary.")
        ),
        bestItems = listOf(
            RecommendedItem("item_iri_grim_max", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Floral Clones deal +35% damage.", "+34% Total DPS", true, "Alpha Nether Sanctuary")
        ),
        statPriorities = listOf("Grass DMG%", "Crit Rate", "Crit DMG", "Clone Duration"),
        rotationGuide = listOf("1. Summon Petal Phantom Clone.", "2. Deploy Verdant Sanctuary Bloom.", "3. Execute Hundred Petal Tempest.")
    ),

    // No. 013: Skippy (Lumin, Water)
    Aniimo(
        id = "skippy",
        name = "Skippy",
        title = "Tide Hopper",
        element = Element.WATER,
        role = "Aquatic Hop & Heal Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 013",
        evolvesToIds = listOf("pranky"),
        evolutionRequirement = "Lv. 16 Wave Pebble",
        baseAtk = 800,
        baseDps = 13500,
        maxOptimizedDps = 52000,
        critRate = 20.0f,
        critDmg = 142.0f,
        attackSpeed = 1.42f,
        overview = "No. 013 in the Aniilog. The adorable Water-type Lumin starter. Skips over tidal puddles and sea foam, splashing cleansing waves that heal allies and soak enemies.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "skip_s1",
                name = "Puddle Splash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "260% Water Impact",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 46000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Kicks a pressurized splash of seawater at foes.",
                dpsSynergyEffect = "Soaks targets for 6 seconds."
            ),
            AniimoSkill(
                id = "skip_s2",
                name = "Tidal Hop",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "410% Bubble Bounce",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 52000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +20% Swift Flow & 510% Splash",
                enhancedDpsPerSec = 64000,
                description = "Core signature skill. Hops on giant bubble crests that pop for area water damage. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Cleanses allied burns."
            ),
            AniimoSkill(
                id = "skip_pass",
                name = "Aquatic Coat",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+12% Water Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 20000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Reduces water skill cooldowns while in water terrain.",
                dpsSynergyEffect = "Transfers into Pranky."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("pranky", "Pranky", Element.WATER, "S+", 32, "Tidal Family", "Increases water soak duration by 50%.", "+20% Water DMG", "Soak with Skippy then swap.")
        ),
        bestItems = listOf(
            RecommendedItem("item_skip_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Tidal Hop creates extra puddles.", "+20% DPS", true, "Crescent Bay")
        ),
        statPriorities = listOf("Water DMG%", "Heal Bonus", "Movement Speed"),
        rotationGuide = listOf("1. Kick Puddle Splash.", "2. Hop on bubble with Tidal Hop.", "3. Tag into Pranky.")
    ),

    // No. 014: Pranky (Gamma, Water)
    Aniimo(
        id = "pranky",
        name = "Pranky",
        title = "Tide Trickster",
        element = Element.WATER,
        role = "Water CC & Branching Healer",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 014",
        evolvesFromId = "skippy",
        evolvesToIds = listOf("glacy", "leafy"),
        evolutionRequirement = "Lv. 24 (Ice Shard: Glacy / Verdant Leaf: Leafy)",
        baseAtk = 1320,
        baseDps = 27800,
        maxOptimizedDps = 108000,
        critRate = 25.0f,
        critDmg = 162.0f,
        attackSpeed = 1.46f,
        overview = "No. 014 in the Aniilog. The mischievous Gamma stage of Skippy. Whirls water currents to trap foes in buoyant whirlpools before branching into Glacy (Ice) or Leafy (Grass/Water).",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "pra_s1",
                name = "Water Jet Prank",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "350% Water Torrent",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 90000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Sprays high-pressure geysers that knock enemies off-balance.",
                dpsSynergyEffect = "Lowers target attack power by 15%."
            ),
            AniimoSkill(
                id = "pra_s2",
                name = "Whirlpool Trap",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "580% Vortex Trap",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 108000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +25% Vortex Pull & 720% Deluge",
                enhancedDpsPerSec = 132000,
                description = "Core signature skill. Spins a vortex that traps multiple targets in a swirl. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Prepares Break stagger."
            ),
            AniimoSkill(
                id = "pra_pass",
                name = "Bubble Armor",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Damage Reduction",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 28000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Absorbs incoming damage with a pressurized bubble barrier.",
                dpsSynergyEffect = "Protects team during casting animations."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("glacy", "Glacy", Element.ICE, "S+", 36, "Freeze Synergy", "Freezes whirlpool targets instantly.", "+28% Ice DMG", "Trap then freeze with Glacy.")
        ),
        bestItems = listOf(
            RecommendedItem("item_pra_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+45% ATK", "Whirlpool Trap pulls 30% stronger.", "+25% DPS", true, "Bay Alpha")
        ),
        statPriorities = listOf("Water DMG%", "Crowd Control Duration", "Crit Rate"),
        rotationGuide = listOf("1. Water Jet Prank to debuff.", "2. Lock with Whirlpool Trap.", "3. Tag into Nova evolution.")
    ),

    // No. 015: Glacy (Nova, Ice/Water)
    Aniimo(
        id = "glacy",
        name = "Glacy",
        title = "Permafrost Healer",
        element = Element.ICE,
        secondaryElement = Element.WATER,
        role = "Cryo Shatter & Freeze DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 015",
        evolvesFromId = "pranky",
        baseAtk = 1820,
        baseDps = 41000,
        maxOptimizedDps = 160000,
        critRate = 30.0f,
        critDmg = 180.0f,
        attackSpeed = 1.56f,
        overview = "No. 015 in the Aniilog. The glacial Nova evolution branching from Pranky. Freezes soaked enemies solid and shatters them with crystalline ice icicles.",
        faceDrawableResName = "img_face_malevsera",
        skills = listOf(
            AniimoSkill(
                id = "gla_s1",
                name = "Ice Spear Salvo",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "380% Cryo Piercing",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 112000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires 4 sharp ice spears that impale soaked enemies.",
                dpsSynergyEffect = "Applies 2 Frostbite stacks."
            ),
            AniimoSkill(
                id = "gla_s2",
                name = "Absolute Zero Glaciation",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "640% Permafrost Burst",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 160000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Shatter Rate & 790% Glacier Blast",
                enhancedDpsPerSec = 192000,
                description = "Core signature skill. Drops a colossal glacial iceberg on frozen foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Shatters frozen targets for double damage."
            ),
            AniimoSkill(
                id = "gla_pass",
                name = "Cryo Resonance",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Damage vs Frozen",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 38000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Party deals +25% critical damage against frozen or frostbitten enemies.",
                dpsSynergyEffect = "Combines with Malevsera for record clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("malevsera", "Malevsera", Element.ICE, "S+", 40, "Glacial Shatter", "Freezes bosses for Malevsera's Bloodthirst.", "+35% Cryo DMG", "Freeze with Glacy, burst with Malevsera.")
        ),
        bestItems = listOf(
            RecommendedItem("item_gla_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Glaciation deals 30% extra Shatter damage.", "+32% Total DPS", true, "Glacial Spire Drop")
        ),
        statPriorities = listOf("Ice DMG%", "Crit Rate", "Crit DMG", "Freeze Duration"),
        rotationGuide = listOf("1. Fire Ice Spear Salvo.", "2. Freeze target solid.", "3. Shatter with Absolute Zero Glaciation.")
    ),

    // No. 016: Leafy (Nova, Grass/Water)
    Aniimo(
        id = "leafy",
        name = "Leafy",
        title = "Verdant Dew Guardian",
        element = Element.GRASS,
        secondaryElement = Element.WATER,
        role = "Nature Regen & Bloom DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 016",
        evolvesFromId = "pranky",
        baseAtk = 1800,
        baseDps = 40200,
        maxOptimizedDps = 157000,
        critRate = 29.0f,
        critDmg = 175.0f,
        attackSpeed = 1.54f,
        overview = "No. 016 in the Aniilog. The alternative Grass/Water Nova evolution branching from Pranky. Spreads lush blooming clover beds that fuel team Energy Points while entangling monsters.",
        faceDrawableResName = "img_face_irisalis",
        skills = listOf(
            AniimoSkill(
                id = "lea_s1",
                name = "Dew Drop Barrage",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Grass/Water",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 109000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires charged hydro-clover droplets that explode in vines.",
                dpsSynergyEffect = "Entangles enemies for 3 seconds."
            ),
            AniimoSkill(
                id = "lea_s2",
                name = "Canon Nature Bloom",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "630% Verdant Torrent",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 157000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +25% EP Regen & 780% Botanical Blast",
                enhancedDpsPerSec = 188000,
                description = "Core signature skill. Creates a verdant garden that restores 8 EP/sec to companions. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Doubles clone duration."
            ),
            AniimoSkill(
                id = "lea_pass",
                name = "Nature Spring",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Party Regen",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 37000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Heals active party members continuously while standing on grass terrain.",
                dpsSynergyEffect = "Permits high-risk aggressive playstyles."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("irisalis", "Irisalis", Element.GRASS, "S+", 38, "Twin Bloom", "Canon Nature passive doubles Irisalis clone duration.", "+30% Sustained DPS", "Deploy garden then tag Irisalis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_lea_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Canon Nature restores +5 extra EP/sec.", "+30% Total DPS", true, "Deep Woods Alpha")
        ),
        statPriorities = listOf("Grass DMG%", "Energy Regen", "Crit Rate"),
        rotationGuide = listOf("1. Entangle with Dew Drop Barrage.", "2. Bloom Canon Nature for team EP.", "3. Tag into carry DPS.")
    ),

    // No. 017: Nimbi (Lumin, Wind)
    Aniimo(
        id = "nimbi",
        name = "Nimbi",
        title = "Cloud Fluff Sprite",
        element = Element.WIND,
        role = "Cloud Armor & Haste Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 017",
        evolvesToIds = listOf("turbo"),
        evolutionRequirement = "Lv. 17 Nimbus Feather",
        baseAtk = 830,
        baseDps = 14100,
        maxOptimizedDps = 55000,
        critRate = 22.5f,
        critDmg = 148.0f,
        attackSpeed = 1.44f,
        overview = "No. 017 in the Aniilog. The buoyant cloud Lumin stage. Drifts peacefully above grassy hills, wrapping companions in fluffy cumulus shields that absorb blows.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "nim_s1",
                name = "Cloud Puff",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "270% Wind Cushion",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 49000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Releases a puffy gust of wind that pushes foes away.",
                dpsSynergyEffect = "Grants +10% movement speed."
            ),
            AniimoSkill(
                id = "nim_s2",
                name = "Nimbus Shield",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "420% Aeroblast",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 55000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Windstride Feather (+15)",
                enhancedMultiplier = "Grants +25% Shield & 530% Gale Slam",
                enhancedDpsPerSec = 67000,
                description = "Core signature skill. Wraps party in a high-pressure wind shield. Enhanced by Maxed Windstride Feather (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Reflects projectile attacks."
            ),
            AniimoSkill(
                id = "nim_pass",
                name = "Weightless",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Dodge Speed",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases dodge invulnerability frames by 20%.",
                dpsSynergyEffect = "Transfers into Turbo."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("turbo", "Turbo", Element.WIND, "S+", 33, "Vortex Flight", "Increases turbo speed duration.", "+20% Wind DMG", "Deploy shield then swap to Turbo.")
        ),
        bestItems = listOf(
            RecommendedItem("item_nim_feath", "Windstride Feather (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Nimbus Shield absorbs 30% more.", "+21% DPS", true, "Cloud Plateau")
        ),
        statPriorities = listOf("Wind DMG%", "Dodge Speed", "Cooldown Reduction"),
        rotationGuide = listOf("1. Cast Cloud Puff.", "2. Protect party with Nimbus Shield.", "3. Tag into Turbo.")
    ),

    // No. 018: Turbo (Nova, Wind)
    Aniimo(
        id = "turbo",
        name = "Turbo",
        title = "Gale Cyclone Dasher",
        element = Element.WIND,
        role = "Hyper-Speed & Tornado DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 018",
        evolvesFromId = "nimbi",
        baseAtk = 1850,
        baseDps = 42000,
        maxOptimizedDps = 163000,
        critRate = 31.5f,
        critDmg = 184.0f,
        attackSpeed = 1.68f,
        overview = "No. 018 in the Aniilog. The supersonic Nova evolution of Nimbi. Dashes through enemies at mach speeds, leaving vacuum trails that collapse into devastating shockwaves.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "tur_s1",
                name = "Sonic Blitz",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "390% High-Speed Slash",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 116000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Flashes across the screen cutting through all aligned foes.",
                dpsSynergyEffect = "Grants +20% attack speed for 6s."
            ),
            AniimoSkill(
                id = "tur_s2",
                name = "Vacuum Tornado Impact",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "650% Supersonic Vortex",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 163000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Mach Speed & 800% Cyclone",
                enhancedDpsPerSec = 194000,
                description = "Core signature skill. Spins at mach 2 creating a vacuum tornado. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Churns boss poise meters."
            ),
            AniimoSkill(
                id = "tur_pass",
                name = "Mach Overdrive",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Damage at Max Speed",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +25% increased damage while sprinting or dodging.",
                dpsSynergyEffect = "Rewards fluid, agile movement."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("somniwing", "Somniwing", Element.WIND, "S+", 37, "Windstorm Drive", "Infinite stamina and rapid EP loops.", "+30% Wind DMG", "Loop Sonic Blitz and Vacuum Tornado.")
        ),
        bestItems = listOf(
            RecommendedItem("item_tur_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Vacuum Tornado deals +35% damage.", "+33% Total DPS", true, "Gale Summit")
        ),
        statPriorities = listOf("Wind DMG%", "Attack Speed", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Activate Sonic Blitz for haste.", "2. Unleash Vacuum Tornado Impact.", "3. Dash through staggered targets.")
    ),

    // No. 019: Dreaple (Lumin, Nature/Grass)
    Aniimo(
        id = "dreaple",
        name = "Dreaple",
        title = "Mossy Swamp Croc",
        element = Element.GRASS,
        role = "Swamp Camouflage & Bite DPS",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 019",
        evolvesToIds = listOf("crocobask"),
        evolutionRequirement = "Lv. 20 Bog Stone",
        baseAtk = 850,
        baseDps = 14400,
        maxOptimizedDps = 58000,
        critRate = 23.0f,
        critDmg = 152.0f,
        attackSpeed = 1.36f,
        overview = "No. 019 in the Aniilog. The camouflaged reptilian Lumin stage. Lurks beneath swamp duckweed, snapping shut with vice-like jaws when unwary prey steps near.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "dre_s1",
                name = "Swamp Snap",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Nature Physical",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 52000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Vicious jaw snap that inflicts bleed on targets.",
                dpsSynergyEffect = "Slows target by 15%."
            ),
            AniimoSkill(
                id = "dre_s2",
                name = "Muddy Tail Swipe",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Heavy Tail",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 58000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Bleed & 550% Mud Strike",
                enhancedDpsPerSec = 70000,
                description = "Core signature skill. Whips a mud-caked tail to stagger foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Blinds enemies."
            ),
            AniimoSkill(
                id = "dre_pass",
                name = "Bog Camouflage",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Ambush DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +15% damage on first strike after stealth.",
                dpsSynergyEffect = "Transfers into Crocobask."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("crocobask", "Crocobask", Element.GRASS, "S+", 34, "Swamp Apex", "Increases bleed damage by 30%.", "+22% Grass DMG", "Bite with Dreaple then tag Crocobask.")
        ),
        bestItems = listOf(
            RecommendedItem("item_dre_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Swamp Snap inflicts double bleed.", "+22% DPS", true, "Murk Bog")
        ),
        statPriorities = listOf("Grass DMG%", "Crit Rate", "Bleed Bonus"),
        rotationGuide = listOf("1. Ambush with Swamp Snap.", "2. Stagger with Muddy Tail Swipe.", "3. Tag into Crocobask.")
    ),

    // No. 020: Crocobask (Nova, Grass/Water)
    Aniimo(
        id = "crocobask",
        name = "Crocobask",
        title = "Abyssal Mire Dreadnought",
        element = Element.GRASS,
        secondaryElement = Element.WATER,
        role = "Armor Shred & Bleed Executioner",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 020",
        evolvesFromId = "dreaple",
        baseAtk = 1920,
        baseDps = 45000,
        maxOptimizedDps = 173000,
        critRate = 33.0f,
        critDmg = 191.0f,
        attackSpeed = 1.62f,
        overview = "No. 020 in the Aniilog. The colossal Nova evolution of Dreaple. Armored in fossilized bog-bark, it executes staggering death rolls that rip through boss defense plating.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "croc_s1",
                name = "Primal Death Roll",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Rotational Tear",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grabs enemy and executes a violent death roll, tearing 30% armor.",
                dpsSynergyEffect = "Applies 5 bleed stacks."
            ),
            AniimoSkill(
                id = "croc_s2",
                name = "Apex Jaws of Desolation",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "680% Crushing Maw",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 173000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Armor Shred & 840% Mega Clamp",
                enhancedDpsPerSec = 208000,
                description = "Core signature skill. Clamps down with 100,000 PSI force. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees Break phase."
            ),
            AniimoSkill(
                id = "croc_ult",
                name = "Quagmire Eruption",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1600% Bog Eruption",
                cooldownSec = 18,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 154000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Erupts geysers of corrosive swamp mud that dissolve enemy shields.",
                dpsSynergyEffect = "Stuns boss for 3 seconds."
            ),
            AniimoSkill(
                id = "croc_pass",
                name = "Blood in the Bog",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage vs Bleeding",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 43000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases all damage dealt to bleeding targets by 30%.",
                dpsSynergyEffect = "Enables record-breaking sustained execution."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 39, "Shatter & Shock", "Crocobask shreds defense, Fulmintis executes.", "+35% Boss Burst", "Clamp with Apex Jaws then tag Fulmintis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_croc_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Apex Jaws deals 35% extra damage.", "+35% Total DPS", true, "Alpha Mire Drop")
        ),
        statPriorities = listOf("Grass DMG%", "Break Efficiency", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Primal Death Roll to shred armor.", "2. Crush with Apex Jaws of Desolation.", "3. Finish with Quagmire Eruption.")
    ),

    // No. 021: Hummin (Lumin, Air/Support)
    Aniimo(
        id = "hummin",
        name = "Hummin",
        title = "Puffby Sprite",
        element = Element.WIND,
        role = "Air Float & Resonance Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 021",
        evolvesToIds = listOf("hexxin"),
        evolutionRequirement = "Lv. 19 Mystic Feather",
        baseAtk = 810,
        baseDps = 13900,
        maxOptimizedDps = 53000,
        critRate = 21.5f,
        critDmg = 146.0f,
        attackSpeed = 1.48f,
        overview = "No. 021 in the Aniilog. The tiny humming puffball Lumin stage (also called Puffby). Vibrates its translucent wings at harmonic frequencies to float effortlessly and buff companions.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "hum_s1",
                name = "Humming Dart",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "270% Aero Needle",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 48000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Darts forward vibrating wings to pierce foes.",
                dpsSynergyEffect = "Grants +10% attack speed."
            ),
            AniimoSkill(
                id = "hum_s2",
                name = "Resonance Buzz",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "420% Sonic Hum",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 53000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Windstride Feather (+15)",
                enhancedMultiplier = "Grants +20% Speed & 520% Buzz Strike",
                enhancedDpsPerSec = 65000,
                description = "Core signature skill. Emits an ultrasonic wave that disorients monsters. Enhanced by Maxed Windstride Feather (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Lowers target defense."
            ),
            AniimoSkill(
                id = "hum_pass",
                name = "Flutter Haste",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+12% Team Haste",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 21000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases team attack speed by 12%.",
                dpsSynergyEffect = "Transfers into Hexxin."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("hexxin", "Hexxin", Element.DARK, "S+", 32, "Mystic Echo", "Deepens hex debuff potency.", "+20% Magic DMG", "Buzz then tag Hexxin.")
        ),
        bestItems = listOf(
            RecommendedItem("item_hum_feath", "Windstride Feather (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Resonance Buzz debuffs +10% more.", "+20% DPS", true, "Meadow Alpha")
        ),
        statPriorities = listOf("Wind DMG%", "Attack Speed", "Cooldown Reduction"),
        rotationGuide = listOf("1. Humming Dart for haste.", "2. Resonance Buzz to debuff.", "3. Tag into Hexxin.")
    ),

    // No. 022: Hexxin (Gamma, Dark/Grass)
    Aniimo(
        id = "hexxin",
        name = "Hexxin",
        title = "Buffby Mystic",
        element = Element.DARK,
        secondaryElement = Element.GRASS,
        role = "Curse Amp & Debuff DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 022",
        evolvesFromId = "hummin",
        evolvesToIds = listOf("tuckin"),
        evolutionRequirement = "Lv. 28 Royal Sigil",
        baseAtk = 1340,
        baseDps = 28200,
        maxOptimizedDps = 110000,
        critRate = 26.0f,
        critDmg = 164.0f,
        attackSpeed = 1.48f,
        overview = "No. 022 in the Aniilog. The Gamma evolution of Hummin (also called Buffby). Weaves dark hexes and arcane talismans to amplify the damage enemies receive from all elements.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "hex_s1",
                name = "Hex Talisman",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "360% Curse Needle",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 92000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Throws a trio of cursed talismans that stick to foes.",
                dpsSynergyEffect = "Amplifies damage received by 15%."
            ),
            AniimoSkill(
                id = "hex_s2",
                name = "Arcane Resonance Detonation",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "600% Mystic Burst",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 110000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +25% Curse Amp & 740% Hex Shock",
                enhancedDpsPerSec = 135000,
                description = "Core signature skill. Detonates all active talismans in a violet blast. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Causes stagger."
            ),
            AniimoSkill(
                id = "hex_pass",
                name = "Evil Eye",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+18% Dark Amp",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 30000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Targets affected by Hex take 18% bonus critical hit damage.",
                dpsSynergyEffect = "Sets up massive team burst."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("tuckin", "Tuckin", Element.DARK, "S+", 36, "Royal Hex", "Transfers talismans into royal crests.", "+28% Magic DMG", "Stack talismans then tag Tuckin.")
        ),
        bestItems = listOf(
            RecommendedItem("item_hex_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+46% ATK", "Detonation deals +30% damage.", "+26% DPS", true, "Haunted Copse")
        ),
        statPriorities = listOf("Dark DMG%", "Crit Rate", "Curse Amp"),
        rotationGuide = listOf("1. Stick Hex Talismans.", "2. Arcane Resonance Detonation.", "3. Tag into Tuckin.")
    ),

    // No. 023: Tuckin (Nova, Royal/Support)
    Aniimo(
        id = "tuckin",
        name = "Tuckin",
        title = "Royalby Sovereign",
        element = Element.DARK,
        role = "Royal Command & Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 023",
        evolvesFromId = "hexxin",
        baseAtk = 1880,
        baseDps = 43500,
        maxOptimizedDps = 168000,
        critRate = 32.0f,
        critDmg = 188.0f,
        attackSpeed = 1.62f,
        overview = "No. 023 in the Aniilog. The opulent Nova evolution of Hexxin (also called Royalby). Wears majestic gilded crests and commands spectral regal legions to crush opposition.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "tuc_s1",
                name = "Crown Scepter Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Royal Dark",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Strikes with a gilded scepter releasing crown blades.",
                dpsSynergyEffect = "Applies 20% Vulnerability."
            ),
            AniimoSkill(
                id = "tuc_s2",
                name = "Regal Decree Cataclysm",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Imperial Judgment",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 168000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +30% Royal Command & 820% Strike",
                enhancedDpsPerSec = 200000,
                description = "Core signature skill. Issues a royal decree summoning falling crown blades. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss poise."
            ),
            AniimoSkill(
                id = "tuc_pass",
                name = "Sovereign Authority",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Team Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Party deals +25% increased damage while boss is under 50% HP.",
                dpsSynergyEffect = "Accelerates phase 2 execution."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Crown Storm", "Decree debuff doubles Fulmintis lightning slashes.", "+32% Boss DMG", "Issue Decree then let Fulmintis burst.")
        ),
        bestItems = listOf(
            RecommendedItem("item_tuc_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Regal Decree deals +35% damage.", "+34% Total DPS", true, "Royal Citadel Alpha")
        ),
        statPriorities = listOf("Dark DMG%", "Crit Rate", "Crit DMG", "Boss Execution"),
        rotationGuide = listOf("1. Scepter Slash to debuff.", "2. Cast Regal Decree Cataclysm.", "3. Tag into executioner.")
    ),

    // No. 024: Budclaw (Lumin, Earth/Grass)
    Aniimo(
        id = "budclaw",
        name = "Budclaw",
        title = "Pebble Claw",
        element = Element.EARTH,
        secondaryElement = Element.GRASS,
        role = "Burrow & Earth Break",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 024",
        evolvesToIds = listOf("shrubclaw"),
        evolutionRequirement = "Lv. 17 Burrow Stone",
        baseAtk = 840,
        baseDps = 14200,
        maxOptimizedDps = 56000,
        critRate = 22.0f,
        critDmg = 150.0f,
        attackSpeed = 1.38f,
        overview = "No. 024 in the Aniilog. The sturdy earth-claw Lumin stage. Digs into bedrock with armored crystalline claws, surfacing unexpectedly to knock back foes.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "bud_s1",
                name = "Pebble Strike",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Earth Physical",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 50000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Throws a hail of sharp flint pebbles.",
                dpsSynergyEffect = "Slows targets by 15%."
            ),
            AniimoSkill(
                id = "bud_s2",
                name = "Subterranean Burrow",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "430% Earth Breach",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 56000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Poise Damage & 540% Breach",
                enhancedDpsPerSec = 68000,
                description = "Core signature skill. Dives underground and erupts under foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Grants iframes while subterranean."
            ),
            AniimoSkill(
                id = "bud_pass",
                name = "Hardened Shell",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Defense",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases team defense by 15% after burrowing.",
                dpsSynergyEffect = "Transfers into Shrubclaw and Geoclaw."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("geoclaw", "Geoclaw", Element.ICE, "S+", 34, "Earth Ice Fusion", "Doubles breach poise damage.", "+25% Break DMG", "Burrow with Budclaw then tag Geoclaw.")
        ),
        bestItems = listOf(
            RecommendedItem("item_bud_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Subterranean Burrow iframes +1s.", "+21% DPS", true, "Crag Mine")
        ),
        statPriorities = listOf("Earth DMG%", "Break Efficiency", "Defense"),
        rotationGuide = listOf("1. Throw Pebble Strike.", "2. Erupt with Subterranean Burrow.", "3. Tag into Shrubclaw.")
    ),

    // No. 025: Shrubclaw (Gamma, Ice/Earth)
    Aniimo(
        id = "shrubclaw",
        name = "Shrubclaw",
        title = "Permafrost Burrower",
        element = Element.ICE,
        secondaryElement = Element.EARTH,
        role = "Break & Cryo Earth DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 025",
        evolvesFromId = "budclaw",
        evolvesToIds = listOf("geoclaw"),
        evolutionRequirement = "Lv. 26 Glacial Core",
        baseAtk = 1360,
        baseDps = 28600,
        maxOptimizedDps = 112000,
        critRate = 26.5f,
        critDmg = 166.0f,
        attackSpeed = 1.48f,
        overview = "No. 025 in the Aniilog. The Gamma evolution of Budclaw. Combines permafrost ice blades with heavy earth claws to tunnel through frozen tundra and ambush bosses.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "shrub_s1",
                name = "Frost Claw Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Ice/Earth Strike",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 94000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Twin claw slash that leaves frosty crystal shards.",
                dpsSynergyEffect = "Shreds 20% target defense."
            ),
            AniimoSkill(
                id = "shrub_s2",
                name = "Glacial Trench Breaker",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "610% Permafrost Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 112000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Shatter & 760% Trench Slam",
                enhancedDpsPerSec = 138000,
                description = "Core signature skill. Slams both claws into the earth to send forward a line of exploding ice stalagmites. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Freezes and staggers."
            ),
            AniimoSkill(
                id = "shrub_pass",
                name = "Cryo Armor",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Break Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 31000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +20% Break damage against frozen targets.",
                dpsSynergyEffect = "Transfers into Geoclaw's tectonic slam."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("geoclaw", "Geoclaw", Element.ICE, "S+", 37, "Tectonic Glacier", "Glacial trench sets up massive Geoclaw break.", "+30% Break DMG", "Trench then tag Geoclaw.")
        ),
        bestItems = listOf(
            RecommendedItem("item_shrub_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Trench Breaker deals +30% damage.", "+27% DPS", true, "Frost Ridge Alpha")
        ),
        statPriorities = listOf("Ice DMG%", "Earth DMG%", "Break Efficiency", "Crit Rate"),
        rotationGuide = listOf("1. Frost Claw Slash to shred defense.", "2. Glacial Trench Breaker to stagger.", "3. Tag into Geoclaw.")
    )
)
