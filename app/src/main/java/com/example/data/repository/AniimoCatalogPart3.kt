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
 * Canonical Aniilog Catalog Part 3: No. 051 to No. 075
 * Includes Piopiota, Otter line, Bear line, Abyssal carries, Prismana Apex (Fulmintis), and Whisperwake Alphas.
 */
val aniimoCatalogPart3: List<Aniimo> = listOf(
    // No. 051: Piopiota (Nova, Water)
    Aniimo(
        id = "piopiota",
        name = "Piopiota",
        title = "Tidal Sovereign Colossus",
        element = Element.WATER,
        role = "Break & Hydraulic Geyser DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 051",
        evolvesFromId = "popota",
        baseAtk = 1920,
        baseDps = 44800,
        maxOptimizedDps = 173000,
        critRate = 32.5f,
        critDmg = 190.0f,
        attackSpeed = 1.62f,
        overview = "No. 051 in the Aniilog. The colossal Nova evolution of Popota. Summons massive ocean surges and high-pressure geysers that pulverize boss super-armor into submission.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "pio_s1",
                name = "Hydro Tsunami Charge",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Pressurized Tsunami",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Surges forward riding a crest of pressurized ocean water.",
                dpsSynergyEffect = "Shreds 25% target defense."
            ),
            AniimoSkill(
                id = "pio_s2",
                name = "Cataclysmic Geyser Slam",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "680% Hydraulic Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 173000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Break Stagger & 840% Geyser Strike",
                enhancedDpsPerSec = 208000,
                description = "Core signature skill. Slams the ground with immense weight to erupt a volcanic geyser. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 45% of boss Break bars."
            ),
            AniimoSkill(
                id = "pio_pass",
                name = "Oceanic Dominion",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Poise Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 43000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +25% poise damage to soaked targets and grants whole team water shield.",
                dpsSynergyEffect = "Enables flawless Break rotations."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 40, "Electro-Hydraulic", "Soaked enemies conduct Fulmintis lightning slashes.", "+35% Boss Damage", "Geyser Slam then tag Fulmintis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_pio_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Geyser Slam deals +35% damage.", "+35% Total DPS", true, "Alpha Whisperwake Drop")
        ),
        statPriorities = listOf("Break Efficiency", "Water DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Hydro Tsunami Charge to shred defense.", "2. Cataclysmic Geyser Slam to Break boss.", "3. Tag into burst DPS.")
    ),

    // No. 052: Shelly (Lumin, Water)
    Aniimo(
        id = "shelly",
        name = "Shelly",
        title = "Tidal Otter Pup",
        element = Element.WATER,
        role = "Shell Slide & Water Puddle DPS",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 052",
        evolvesToIds = listOf("sheldon"),
        evolutionRequirement = "Lv. 19 Pearl Shell",
        baseAtk = 820,
        baseDps = 14000,
        maxOptimizedDps = 55000,
        critRate = 22.0f,
        critDmg = 146.0f,
        attackSpeed = 1.44f,
        overview = "No. 052 in the Aniilog. The playful otter pup Lumin stage. Slides on belly through ocean puddles, cracking open hard pearl shells against enemy armor.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "she_lum_s1",
                name = "Shell Tap",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Water Shell",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 50000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Taps a hardened clamshell against target with sharp force.",
                dpsSynergyEffect = "Leaves water puddle on contact."
            ),
            AniimoSkill(
                id = "she_lum_s2",
                name = "Puddle Belly Slide",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Hydro Slide",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 55000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Speed & 550% Slide Strike",
                enhancedDpsPerSec = 68000,
                description = "Core signature skill. Slides forward on water crest to ram foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Grants 2s invulnerability."
            ),
            AniimoSkill(
                id = "she_lum_pass",
                name = "Tidal Heart",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Water DMG in Puddles",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases Water damage by 15% for 10s while in water terrain.",
                dpsSynergyEffect = "Transfers into Sheldon and Sherro."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("sheldon", "Sheldon", Element.WATER, "S+", 34, "Otter Family", "Doubles puddle generation.", "+22% Water DMG", "Slide with Shelly then tag Sheldon.")
        ),
        bestItems = listOf(
            RecommendedItem("item_she_lum_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Puddle Belly Slide leaves 2x puddles.", "+21% DPS", true, "Crescent Beach")
        ),
        statPriorities = listOf("Water DMG%", "Movement Speed", "Crit Rate"),
        rotationGuide = listOf("1. Shell Tap to create puddle.", "2. Puddle Belly Slide through enemy.", "3. Tag into Sheldon.")
    ),

    // No. 053: Sheldon (Gamma, Water)
    Aniimo(
        id = "sheldon",
        name = "Sheldon",
        title = "Armored Shell Otter",
        element = Element.WATER,
        role = "Break & Shell Armor DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 053",
        evolvesFromId = "shelly",
        evolvesToIds = listOf("sherro"),
        evolutionRequirement = "Lv. 28 Ocean Pearl",
        baseAtk = 1340,
        baseDps = 28000,
        maxOptimizedDps = 109000,
        critRate = 26.0f,
        critDmg = 162.0f,
        attackSpeed = 1.48f,
        overview = "No. 053 in the Aniilog. The Gamma evolution of Shelly. Fashions thick coral-encrusted shells into defensive armor and offensive sledgehammers to batter foes.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "shed_s1",
                name = "Coral Shell Strike",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "360% Water Shell",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 91000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Swings a heavy coral shell, cracking 20% target defense.",
                dpsSynergyEffect = "Builds 2 Shell Armor stacks."
            ),
            AniimoSkill(
                id = "shed_s2",
                name = "Hydro Current Charge",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "600% Whirlpool Rush",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 109000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Poise Break & 740% Hydro Slam",
                enhancedDpsPerSec = 134000,
                description = "Core signature skill. Charges forward spinning within a water current. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss poise."
            ),
            AniimoSkill(
                id = "shed_pass",
                name = "Tidal Heart Resonance",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Water Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 29000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Gains +20% Water damage after absorbing water or standing in puddles.",
                dpsSynergyEffect = "Transfers into Sherro."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("sherro", "Sherro", Element.WATER, "S+", 37, "Tidal Lineage", "Full puddle and current resonance.", "+28% Water DMG", "Current Charge then tag Sherro.")
        ),
        bestItems = listOf(
            RecommendedItem("item_shed_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+46% ATK", "Current Charge deals +30% damage.", "+26% DPS", true, "Echoback Landing")
        ),
        statPriorities = listOf("Water DMG%", "Break Efficiency", "Crit Rate"),
        rotationGuide = listOf("1. Coral Shell Strike to shred defense.", "2. Hydro Current Charge to stagger.", "3. Tag into Sherro.")
    ),

    // No. 054: Sherro (Nova, Water)
    Aniimo(
        id = "sherro",
        name = "Sherro",
        title = "Tidal Shell Drifter",
        element = Element.WATER,
        role = "Aquatic Current & Rapid Tide DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 054",
        evolvesFromId = "sheldon",
        baseAtk = 1890,
        baseDps = 44000,
        maxOptimizedDps = 170000,
        critRate = 32.0f,
        critDmg = 188.0f,
        attackSpeed = 1.65f,
        overview = "No. 054 in the Aniilog. The apex Nova evolution of Shelly and Sheldon. Surges across arena puddles with unmatched mobility, unleashing pressurized 700% water divebombs.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "she_s1",
                name = "Shell Crash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Water Physical",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 119000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Slides forward on a water crest, ramming foes with an armored pearl shell.",
                dpsSynergyEffect = "Leaves water puddles that boost Sherro's speed by 25%."
            ),
            AniimoSkill(
                id = "she_s2",
                name = "Vortex Current Dive",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Pressurized Torrent",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 170000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Swift Flow & 830% Water Strike",
                enhancedDpsPerSec = 206000,
                description = "Core signature skill. Dives into a subterranean whirlpool and emerges beneath the enemy. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Grants invulnerability during dive animation."
            ),
            AniimoSkill(
                id = "she_pass",
                name = "Tide Surfer",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% ATK on Water Terrain",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "While standing in water or puddles, gains +25% total ATK and dodging invulnerability frames.",
                dpsSynergyEffect = "Enables flawless defensive evasion."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 40, "Electro-Hydraulic Overcharge", "Puddles amplify lightning chains.", "+35% Boss DMG", "Soak with Vortex Current Dive then tag Fulmintis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_she_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Vortex Current Dive deals +35% damage.", "+35% Total DPS", true, "Whisperwake Reef (+15)")
        ),
        statPriorities = listOf("Water DMG%", "Crit Rate", "Crit DMG", "Movement Speed"),
        rotationGuide = listOf("1. Shell Crash to seed puddles.", "2. Vortex Current Dive during boss attacks.", "3. Slide to maintain +25% ATK.")
    ),

    // No. 055: Cubbo (Lumin, Earth)
    Aniimo(
        id = "cubbo",
        name = "Cubbo",
        title = "Grizzly Stone Cub",
        element = Element.EARTH,
        role = "Break & Rage Stacking Starter",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 055",
        evolvesToIds = listOf("grizbo"),
        evolutionRequirement = "Lv. 24 Earth Core",
        baseAtk = 870,
        baseDps = 14800,
        maxOptimizedDps = 59000,
        critRate = 23.0f,
        critDmg = 152.0f,
        attackSpeed = 1.34f,
        overview = "No. 055 in the Aniilog. The sturdy bear cub Lumin stage. Roams mountain caves rolling boulders to build inner Rage, evolving into the mighty Grizbo.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "cub_s1",
                name = "Pebble Swipe",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Earth Claw",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 52000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Claws with stone paws, building 15 Rage.",
                dpsSynergyEffect = "Staggers small enemies."
            ),
            AniimoSkill(
                id = "cub_s2",
                name = "Rolling Boulder",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "450% Crag Roll",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 59000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Rage Gain & 560% Boulder Strike",
                enhancedDpsPerSec = 72000,
                description = "Core signature skill. Rolls a giant granite rock into foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss poise."
            ),
            AniimoSkill(
                id = "cub_pass",
                name = "Sturdy Fur",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Defense",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Reduces physical damage taken by 15%.",
                dpsSynergyEffect = "Transfers into Grizbo's Berserker Rage."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("grizbo", "Grizbo", Element.EARTH, "S+", 36, "Ursine Fury", "Transfers full 100 Rage instantly.", "+28% Earth DMG", "Max Rage then tag Grizbo.")
        ),
        bestItems = listOf(
            RecommendedItem("item_cub_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Rolling Boulder builds +25 extra Rage.", "+22% DPS", true, "Mountain Den")
        ),
        statPriorities = listOf("Earth DMG%", "Rage Generation", "Break Efficiency"),
        rotationGuide = listOf("1. Pebble Swipe to generate Rage.", "2. Rolling Boulder to build to 100.", "3. Tag into Grizbo.")
    ),

    // No. 056: Grizbo (Nova, Earth)
    Aniimo(
        id = "grizbo",
        name = "Grizbo",
        title = "Titan Ursine Berserker",
        element = Element.EARTH,
        role = "Rage Stacks & Earth Shatter Burst",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 056",
        evolvesFromId = "cubbo",
        baseAtk = 1940,
        baseDps = 45500,
        maxOptimizedDps = 175000,
        critRate = 33.0f,
        critDmg = 194.0f,
        attackSpeed = 1.62f,
        overview = "No. 056 in the Aniilog. The colossal Earth berserker Nova evolution of Cubbo. Stacks 100 Rage and 12 Claw Marks (+60% Might) to one-shot boss phase 2 in sub-40 second clears.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "griz_s1",
                name = "Seismic Claw Swipe",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Earth Physical",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Tears through bedrock, generating 25 Rage and applying 2 Claw Marks.",
                dpsSynergyEffect = "Shreds 25% target defense."
            ),
            AniimoSkill(
                id = "griz_s2",
                name = "Earth Shatter Strike",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Seismic Pulverize",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 175000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Consumes 12 Claw Marks (+60% Might) & 860% Strike",
                enhancedDpsPerSec = 212000,
                description = "Core signature skill. Consumes all Claw Marks to deliver a catastrophic ground pound. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Obliterates boss stagger bars."
            ),
            AniimoSkill(
                id = "griz_pass",
                name = "Primal Ursine Berserker",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+40% ATK at 100 Rage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "At 100 Rage, gains +40% total ATK and cannot be interrupted by attacks.",
                dpsSynergyEffect = "Enables world-record Thunder Sovereign clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("bailite", "Bailite", Element.EARTH, "S+", 40, "Seismic Resonance", "Bailite shreds 35% earth defense, Grizbo one-shots.", "+35% Boss Burst", "Break with Bailite then cast Earth Shatter Strike.")
        ),
        bestItems = listOf(
            RecommendedItem("item_griz_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Earth Shatter Strike deals +35% damage.", "+36% Total DPS", true, "Alpha Thunder Sovereign Drop")
        ),
        statPriorities = listOf("Earth DMG%", "Rage Stacks", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Reach 100 Rage with Seismic Claw.", "2. Stack 12 Claw Marks.", "3. Unleash Earth Shatter Strike.")
    ),

    // No. 057: Dewlop (Lumin, Water)
    Aniimo(
        id = "dewlop",
        name = "Dewlop",
        title = "Tidal Bubble Tadpole",
        element = Element.WATER,
        role = "Hydration & Bubble Burst",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 057",
        evolvesToIds = listOf("dewple"),
        evolutionRequirement = "Lv. 20 Coral Pearl",
        baseAtk = 810,
        baseDps = 13800,
        maxOptimizedDps = 53000,
        critRate = 21.0f,
        critDmg = 144.0f,
        attackSpeed = 1.40f,
        overview = "No. 057 in the Aniilog. The buoyant coral tadpole Lumin stage. Blows shimmering iridescent bubbles that soak enemies and shield companions.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "dewl_s1",
                name = "Bubble Blast",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "270% Water Bubble",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 48000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a cluster of popping bubbles that soak targets.",
                dpsSynergyEffect = "Soaks targets for 6s."
            ),
            AniimoSkill(
                id = "dewl_s2",
                name = "Droplet Torrent",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "420% Hydro Cascade",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 53000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +25% Soak Potency & 530% Torrent",
                enhancedDpsPerSec = 66000,
                description = "Core signature skill. Calls down a refreshing rain of droplets. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Cleanses team debuffs."
            ),
            AniimoSkill(
                id = "dewl_pass",
                name = "Hydration Trait",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Water DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 21000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases team Water damage by 15% during rain.",
                dpsSynergyEffect = "Transfers into Dewple."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dewple", "Dewple", Element.WATER, "S+", 34, "Coral Lineage", "Full hydration inheritance.", "+22% Water DMG", "Rain with Dewlop then tag Dewple.")
        ),
        bestItems = listOf(
            RecommendedItem("item_dewl_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Droplet Torrent lasts +3s.", "+20% DPS", true, "Coral Lagoon")
        ),
        statPriorities = listOf("Water DMG%", "Heal Bonus", "Energy Regen"),
        rotationGuide = listOf("1. Bubble Blast to soak.", "2. Droplet Torrent for team regen.", "3. Tag into Dewple.")
    ),

    // No. 058: Dewple (Gamma / Lumin, Water)
    Aniimo(
        id = "dewple",
        name = "Dewple",
        title = "Coral Spine Deluge",
        element = Element.WATER,
        role = "Water Shred & Geyser DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 058",
        evolvesFromId = "dewlop",
        baseAtk = 1360,
        baseDps = 28600,
        maxOptimizedDps = 112000,
        critRate = 27.0f,
        critDmg = 166.0f,
        attackSpeed = 1.50f,
        overview = "No. 058 in the Aniilog. The Coral-spined evolution of Dewlop. Shreds monster water resistance with pressurized coral spines, pairing with Whirlbite for Kraken clears.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "dewp_s1",
                name = "Coral Spine Deluge",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Pressurized Spines",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 94000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires 6 coral spines that shred 25% water resistance.",
                dpsSynergyEffect = "Prepares targets for Whirlbite."
            ),
            AniimoSkill(
                id = "dewp_s2",
                name = "Tidal Geyser Slam",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "620% Hydro Slam",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 112000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +30% Water Amp & 770% Geyser Impact",
                enhancedDpsPerSec = 138000,
                description = "Core signature skill. Slams the ground with coral geysers. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Staggers and soaks."
            ),
            AniimoSkill(
                id = "dewp_pass",
                name = "Coral Resonator",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Party Water DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 31000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party Water damage by 25% against soaked targets.",
                dpsSynergyEffect = "Essential for sub-45 second Kraken runs."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("whirlbite", "Whirlbite", Element.WATER, "S+", 40, "Abyssal Duo", "Dewple shreds water defense, Whirlbite loops Riptide Maw.", "+35% Boss DPS", "Spine deluge then let Whirlbite bite.")
        ),
        bestItems = listOf(
            RecommendedItem("item_dewp_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Tidal Geyser Slam deals +30% damage.", "+28% DPS", true, "Alpha Kraken Reef")
        ),
        statPriorities = listOf("Water DMG%", "Resistance Shred", "Crit Rate"),
        rotationGuide = listOf("1. Coral Spine Deluge to shred resistance.", "2. Tidal Geyser Slam to stagger.", "3. Tag into Whirlbite.")
    ),

    // No. 059: Sera (Lumin, Ice)
    Aniimo(
        id = "sera",
        name = "Sera",
        title = "Shadow Viper Hatchling",
        element = Element.ICE,
        role = "Frostbite Venom & Bleed Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 059",
        evolvesToIds = listOf("malevsera"),
        evolutionRequirement = "Lv. 24 Abyssal Ice Shard",
        baseAtk = 880,
        baseDps = 15000,
        maxOptimizedDps = 60000,
        critRate = 25.0f,
        critDmg = 160.0f,
        attackSpeed = 1.48f,
        overview = "No. 059 in the Aniilog. The cold-blooded shadow viper Lumin stage. Injects chilling frostbite venom that slows prey, evolving into the bloodthirsty Malevsera.",
        faceDrawableResName = "img_face_malevsera",
        skills = listOf(
            AniimoSkill(
                id = "ser_s1",
                name = "Toxic Frost Sting",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Cryo Venom",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 53000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Strikes with venomous ice fangs, chilling targets.",
                dpsSynergyEffect = "Slows target by 25%."
            ),
            AniimoSkill(
                id = "ser_s2",
                name = "Shadow Coil Strike",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "460% Cryo Constrict",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 60000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Venom & 580% Coil Strike",
                enhancedDpsPerSec = 74000,
                description = "Core signature skill. Constricts around foes with freezing shadow coils. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Inflicts deep bleed."
            ),
            AniimoSkill(
                id = "ser_pass",
                name = "Poison Gland",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Poison/Bleed DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 24000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases damage over time effects by 15%.",
                dpsSynergyEffect = "Transfers into Malevsera's Bloodthirst."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("malevsera", "Malevsera", Element.ICE, "S+", 36, "Viper Lineage", "Full venom and bloodthirst inheritance.", "+28% Cryo DMG", "Constrict then tag Malevsera.")
        ),
        bestItems = listOf(
            RecommendedItem("item_ser_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Shadow Coil inflicts double bleed.", "+23% DPS", true, "Glacial Chasm")
        ),
        statPriorities = listOf("Ice DMG%", "Bleed Bonus", "Crit Rate"),
        rotationGuide = listOf("1. Toxic Frost Sting to chill.", "2. Shadow Coil Strike to bleed.", "3. Tag into Malevsera.")
    ),

    // No. 060: Malevsera (Nova, Ice)
    Aniimo(
        id = "malevsera",
        name = "Malevsera",
        title = "Abyssal Frost Sovereign",
        element = Element.ICE,
        role = "Execute & Low-HP Bloodthirst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 060",
        evolvesFromId = "sera",
        baseAtk = 1950,
        baseDps = 46200,
        maxOptimizedDps = 176500,
        critRate = 34.0f,
        critDmg = 196.0f,
        attackSpeed = 1.68f,
        overview = "No. 060 in the Aniilog. The high-risk execute sovereign evolved from Sera. Scales exponentially as HP drops (+48% Bloodthirst scaling below 20% HP), obliterating Titan Glacis in 42.15 seconds.",
        faceDrawableResName = "img_face_malevsera",
        skills = listOf(
            AniimoSkill(
                id = "mal_s1",
                name = "Frost Fang Rend",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "430% Cryo Physical",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 124000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Tears through enemies with frozen fangs, reducing 25% defense.",
                dpsSynergyEffect = "Grants 1 Bloodthirst charge on hit."
            ),
            AniimoSkill(
                id = "mal_s2",
                name = "Glacial Bloodthirst",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Permafrost Execute",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 176500,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +35% Low-HP Scaling & 870% Execute",
                enhancedDpsPerSec = 214000,
                description = "Core signature skill. Lunges forward in a flurry of icy fangs. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Deals double damage below 30% HP."
            ),
            AniimoSkill(
                id = "mal_pass",
                name = "Bloodthirst Scaling",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+48% ATK at Low HP",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 45000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Gains up to +48% total ATK as health drops below 20%.",
                dpsSynergyEffect = "Enables record-breaking world-record clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("glacy", "Glacy", Element.ICE, "S+", 40, "Glacial Bloodlust", "Glacy freezes targets, Malevsera executes.", "+35% Cryo Burst", "Freeze with Glacy then execute with Glacial Bloodthirst.")
        ),
        bestItems = listOf(
            RecommendedItem("item_mal_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Glacial Bloodthirst deals +35% execute damage.", "+36% Total DPS", true, "Alpha Glacis Speedrun")
        ),
        statPriorities = listOf("Ice DMG%", "Low-HP ATK Scaling", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Frost Fang Rend to shred defense.", "2. Manage HP around 20% for max Bloodthirst.", "3. Execute with Glacial Bloodthirst.")
    ),

    // No. 061: Manelet (Lumin, Grass)
    Aniimo(
        id = "manelet",
        name = "Manelet",
        title = "Verdant Cub",
        element = Element.GRASS,
        role = "Ambush Pounce & Bleed Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 061",
        evolvesToIds = listOf("fenmane"),
        evolutionRequirement = "Lv. 22 Forest Mane",
        baseAtk = 850,
        baseDps = 14400,
        maxOptimizedDps = 57000,
        critRate = 23.5f,
        critDmg = 153.0f,
        attackSpeed = 1.45f,
        overview = "No. 061 in the Aniilog. The lively leafy feline Lumin stage. Stalks through tall savannah grasses, springing forward to inflict bleeding scratches with sharp thorn claws.",
        faceDrawableResName = "img_face_fenmane",
        skills = listOf(
            AniimoSkill(
                id = "man_s1",
                name = "Leaf Claw",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Grass Physical",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 51000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Quick swipe with razor leaf claws.",
                dpsSynergyEffect = "Applies bleed for 4s."
            ),
            AniimoSkill(
                id = "man_s2",
                name = "Ambush Pounce",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Floral Leap",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 57000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Bleed & 550% Pounce Strike",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Pounces from tall grass to pin foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees crit on bleed."
            ),
            AniimoSkill(
                id = "man_pass",
                name = "Scent Tracker",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Damage vs Bleeding",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases damage against bleeding targets by 15%.",
                dpsSynergyEffect = "Transfers into Fenmane."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fenmane", "Fenmane", Element.GRASS, "S+", 35, "Lion Pride", "Doubles bleed damage.", "+25% Grass DMG", "Bleed with Manelet then tag Fenmane.")
        ),
        bestItems = listOf(
            RecommendedItem("item_man_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Ambush Pounce deals +25% bleed.", "+21% DPS", true, "Savannah Clearing")
        ),
        statPriorities = listOf("Grass DMG%", "Bleed Bonus", "Crit Rate"),
        rotationGuide = listOf("1. Leaf Claw to start bleed.", "2. Ambush Pounce to pin.", "3. Tag into Fenmane.")
    ),

    // No. 062: Fenmane (Nova, Grass)
    Aniimo(
        id = "fenmane",
        name = "Fenmane",
        title = "Verdant Apex Lion",
        element = Element.GRASS,
        role = "Break & Bleed Explosion Burst",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 062",
        evolvesFromId = "manelet",
        baseAtk = 1910,
        baseDps = 44800,
        maxOptimizedDps = 172000,
        critRate = 33.0f,
        critDmg = 191.0f,
        attackSpeed = 1.64f,
        overview = "No. 062 in the Aniilog. The majestic grass-mane lion Nova evolution of Manelet. Commands emerald savannah winds to detonate bleed stacks into massive area plant explosions.",
        faceDrawableResName = "img_face_fenmane",
        skills = listOf(
            AniimoSkill(
                id = "fenm_s1",
                name = "Savage Thorn Rake",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Thorn Slash",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 120000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Rakes with emerald thorn claws, shredding 25% defense.",
                dpsSynergyEffect = "Applies 3 bleed stacks."
            ),
            AniimoSkill(
                id = "fenm_s2",
                name = "Roar of the Savannah",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Verdant Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 172000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Bleed Detonation & 830% Roar",
                enhancedDpsPerSec = 206000,
                description = "Core signature skill. Roars to detonate all active bleed stacks in an emerald explosion. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss Break bars."
            ),
            AniimoSkill(
                id = "fenm_pass",
                name = "Savannah Sovereign",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Damage vs Bleeding",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases all party damage to bleeding enemies by 25%.",
                dpsSynergyEffect = "Essential for nature speedrun teams."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("thornblade", "Thornblade", Element.GRASS, "S+", 38, "Dual Grass Shred", "Combines bleed explosions with Sword Dance.", "+32% Grass DMG", "Detonate bleed then let Thornblade slash.")
        ),
        bestItems = listOf(
            RecommendedItem("item_fenm_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Roar of the Savannah deals +35% damage.", "+34% Total DPS", true, "Savannah Alpha")
        ),
        statPriorities = listOf("Grass DMG%", "Bleed Detonation", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Savage Thorn Rake to stack bleed.", "2. Roar of the Savannah to detonate.", "3. Cleave with basic attacks.")
    ),

    // No. 063: Silklet (Lumin, Light)
    Aniimo(
        id = "silklet",
        name = "Silklet",
        title = "Prism Caterpillar",
        element = Element.LIGHT,
        role = "Prism Thread & Crit Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 063",
        evolvesToIds = listOf("dazmand"),
        evolutionRequirement = "Lv. 25 Luminous Chrysalis",
        baseAtk = 830,
        baseDps = 14100,
        maxOptimizedDps = 55000,
        critRate = 22.5f,
        critDmg = 148.0f,
        attackSpeed = 1.44f,
        overview = "No. 063 in the Aniilog. The radiant silk caterpillar Lumin stage. Weaves prismatic silk threads that refract ambient starlight, setting up Dazmand's game-defining critical hit auras.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "silk_s1",
                name = "Prism Thread",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Light Silk",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 49000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Shoots a radiant silk thread that tethers foes.",
                dpsSynergyEffect = "Reduces enemy mobility by 30%."
            ),
            AniimoSkill(
                id = "silk_s2",
                name = "Luminous Cocoon Burst",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "430% Radiant Prism",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 55000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Pendant (+15 Contract)",
                enhancedMultiplier = "Grants +25% Crit Setup & 540% Cocoon Strike",
                enhancedDpsPerSec = 67000,
                description = "Core signature skill. Wraps party in a light cocoon that bursts with healing starlight. Enhanced by Maxed Pendant (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Grants +20% party crit."
            ),
            AniimoSkill(
                id = "silk_pass",
                name = "Silk Armor",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Shield Absorption",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Absorbs 15% incoming damage with silk barriers.",
                dpsSynergyEffect = "Transfers into Dazmand's Radiant Amplification."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 36, "Radiant Lineage", "Full prism thread inheritance.", "+28% Light DMG", "Cocoon then tag Dazmand.")
        ),
        bestItems = listOf(
            RecommendedItem("item_silk_pend", "Pendant (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Cocoon Burst grants +25% crit.", "+22% DPS", true, "Luminous Grove")
        ),
        statPriorities = listOf("Crit Rate", "Light DMG%", "Shield Power"),
        rotationGuide = listOf("1. Tether with Prism Thread.", "2. Luminous Cocoon Burst for crit buff.", "3. Tag into Dazmand.")
    ),

    // No. 064: Dazmand (Nova, Light)
    Aniimo(
        id = "dazmand",
        name = "Dazmand",
        title = "Radiant Crit Sovereign",
        element = Element.LIGHT,
        role = "Critical Amplification & Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 064",
        evolvesFromId = "silklet",
        baseAtk = 1960,
        baseDps = 46500,
        maxOptimizedDps = 178000,
        critRate = 35.0f,
        critDmg = 200.0f,
        attackSpeed = 1.70f,
        overview = "No. 064 in the Aniilog. The definitive S+ tier Light support/burst sovereign evolved from Silklet. Active aura multiplies team critical hit damage by 40% and adds +15% crit rate, essential for all #1 world records.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "daz_s1",
                name = "Radiant Prism Lance",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "430% Light Piercing",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 125000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a concentrated prism lance that pierces 30% enemy defense.",
                dpsSynergyEffect = "Applies Radiant Vulnerability."
            ),
            AniimoSkill(
                id = "daz_s2",
                name = "Aura of the Diamond Star",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Radiant Flash",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 178000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Pendant (+15 Contract)",
                enhancedMultiplier = "Grants +40% Team Crit Multiplier & 860% Star Burst",
                enhancedDpsPerSec = 215000,
                description = "Core signature skill. Expands a diamond starlight field that boosts team crit damage by 40%. Enhanced by Maxed Pendant (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guaranteed critical hits for whole team."
            ),
            AniimoSkill(
                id = "daz_pass",
                name = "Radiant Crit Amplification",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+40% Team Crit DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 46000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party critical damage by 40% and critical strike chance by 15%.",
                dpsSynergyEffect = "The cornerstone passive for every world-record speedrun."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 45, "Radiant Storm", "Dazmand crit field maximizes Fulmintis slash resets.", "+42% Team DPS", "Pop Aura of Diamond Star right before Fulmintis Enhanced Light Blade.")
        ),
        bestItems = listOf(
            RecommendedItem("item_daz_pend", "Pendant (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Aura of Diamond Star duration +5s.", "+38% Total DPS", true, "Alpha Nether Colossus Drop")
        ),
        statPriorities = listOf("Crit Rate", "Crit DMG", "Light DMG%", "Aura Uptime"),
        rotationGuide = listOf("1. Radiant Prism Lance to shred defense.", "2. Expand Aura of Diamond Star.", "3. Tag into Fulmintis or Ignitis for world record clear.")
    ),

    // No. 065: Octoball (Lumin, Water)
    Aniimo(
        id = "octoball",
        name = "Octoball",
        title = "Gambler's Bubble Octo",
        element = Element.WATER,
        role = "Jackpot Roll & Ink Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 065",
        evolvesToIds = listOf("gachapus"),
        evolutionRequirement = "Lv. 24 Lucky Coin",
        baseAtk = 860,
        baseDps = 14600,
        maxOptimizedDps = 58000,
        critRate = 24.0f,
        critDmg = 155.0f,
        attackSpeed = 1.45f,
        overview = "No. 065 in the Aniilog. The rolling octopus dice Lumin stage. Squirts high-contrast ink and rolls luck-based dice bubbles, evolving into the legendary Gachapus.",
        faceDrawableResName = "img_face_gachapus",
        skills = listOf(
            AniimoSkill(
                id = "octo_s1",
                name = "Ink Squirt",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Ink Burst",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 51000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Squirts dark ink blinding monsters for 3s.",
                dpsSynergyEffect = "Slows targets by 20%."
            ),
            AniimoSkill(
                id = "octo_s2",
                name = "Jackpot Roll",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "450% Dice Impact",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 58000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Fission Needles (+15)",
                enhancedMultiplier = "Grants +25% Lucky Hit & 560% Dice Slam",
                enhancedDpsPerSec = 71000,
                description = "Core signature skill. Rolls like a giant d20 dice. Enhanced by Maxed Fission Needles (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. 50% chance for triple damage."
            ),
            AniimoSkill(
                id = "octo_pass",
                name = "Gambler's Luck",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Crit Chance",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party crit chance by 15%.",
                dpsSynergyEffect = "Transfers into Gachapus's Super Jackpot."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("gachapus", "Gachapus", Element.WATER, "S+", 36, "Jackpot Family", "Full dice luck inheritance.", "+28% Water DMG", "Roll then tag Gachapus.")
        ),
        bestItems = listOf(
            RecommendedItem("item_octo_need", "Fission Needles (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Jackpot Roll double hit chance +20%.", "+23% DPS", true, "Treasure Reef")
        ),
        statPriorities = listOf("Crit Rate", "Water DMG%", "Luck Bonus"),
        rotationGuide = listOf("1. Ink Squirt to blind.", "2. Roll with Jackpot Roll.", "3. Tag into Gachapus.")
    ),

    // No. 066: Gachapus (Nova, Water)
    Aniimo(
        id = "gachapus",
        name = "Gachapus",
        title = "Jackpot Abyssal Cephalopod",
        element = Element.WATER,
        role = "RNG Jackpot & Double Hit DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 066",
        evolvesFromId = "octoball",
        baseAtk = 1940,
        baseDps = 45800,
        maxOptimizedDps = 176000,
        critRate = 34.0f,
        critDmg = 195.0f,
        attackSpeed = 1.68f,
        overview = "No. 066 in the Aniilog. The high-risk casino cephalopod Nova evolution of Octoball. Pairs with Fission Needles (50% chance for double hits) and Super Jackpot to crit for 420k per basic attack, dominating Kraken leaderboards.",
        faceDrawableResName = "img_face_gachapus",
        skills = listOf(
            AniimoSkill(
                id = "gach_s1",
                name = "Lucky Tentacle Slap",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Hydro Slap",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Slaps with 8 tentacles simultaneously, rolling for jackpot multipliers.",
                dpsSynergyEffect = "Restores 4 EP on lucky roll."
            ),
            AniimoSkill(
                id = "gach_s2",
                name = "Super Jackpot Fever",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Jackpot Cataclysm",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 176000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Fission Needles (+15 Contract)",
                enhancedMultiplier = "Grants 50% Double Damage & 860% Fever Slam",
                enhancedDpsPerSec = 212000,
                description = "Core signature skill. Triggers Super Jackpot Fever, giving all attacks a 50% chance to double damage. Enhanced by Maxed Fission Needles (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Basic attacks crit for 420k each."
            ),
            AniimoSkill(
                id = "gach_pass",
                name = "House Always Wins",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage on Lucky Roll",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Whenever an attack rolls lucky double damage, gains +30% attack speed for 5s.",
                dpsSynergyEffect = "Sub-40 second Whisperwake Kraken world record driver."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("wavwal", "Wavwal", Element.ICE, "S+", 42, "Cryo Jackpot", "Wavwal freezes Kraken in 12s, Gachapus shreds during Break.", "+38% Clear Speed", "Freeze with Wavwal, pop Super Jackpot.")
        ),
        bestItems = listOf(
            RecommendedItem("item_gach_need", "Fission Needles (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "50% chance for double hits on every attack.", "+36% Total DPS", true, "Alpha Kraken Sub-40 Run")
        ),
        statPriorities = listOf("Crit Rate", "Crit DMG", "Water DMG%", "Double Hit Rate"),
        rotationGuide = listOf("1. Lucky Tentacle Slap to start rolls.", "2. Pop Super Jackpot Fever during Break.", "3. Machine-gun basic attacks for 420k crits.")
    ),

    // No. 067: Chompkin (Lumin, Water)
    Aniimo(
        id = "chompkin",
        name = "Chompkin",
        title = "Tidal Sharkling",
        element = Element.WATER,
        role = "Riptide Bite & Bleed Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 067",
        evolvesToIds = listOf("whirlbite"),
        evolutionRequirement = "Lv. 24 Shark Tooth",
        baseAtk = 870,
        baseDps = 14800,
        maxOptimizedDps = 59000,
        critRate = 24.5f,
        critDmg = 158.0f,
        attackSpeed = 1.48f,
        overview = "No. 067 in the Aniilog. The relentless sharkling Lumin stage. Circles wounded monsters in coastal shallows, scenting blood in water to evolve into Whirlbite.",
        faceDrawableResName = "img_face_whirlbite",
        skills = listOf(
            AniimoSkill(
                id = "chm_s1",
                name = "Snap Chomp",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Water Bite",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 53000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Vicious shark bite that inflicts deep bleeding.",
                dpsSynergyEffect = "Slows target by 20%."
            ),
            AniimoSkill(
                id = "chm_s2",
                name = "Whirlpool Dive",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "450% Aquatic Plunge",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 59000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Bleed & 570% Shark Strike",
                enhancedDpsPerSec = 72000,
                description = "Core signature skill. Dives into water and surges up underneath foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Freezes on crit."
            ),
            AniimoSkill(
                id = "chm_pass",
                name = "Blood in Water",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Damage vs Bleeding",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Gains +15% attack speed when attacking bleeding targets.",
                dpsSynergyEffect = "Transfers into Whirlbite's Riptide Maw."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("whirlbite", "Whirlbite", Element.WATER, "S+", 36, "Apex Frenzy", "Full shark bloodlust inheritance.", "+28% Water DMG", "Bite with Chompkin then tag Whirlbite.")
        ),
        bestItems = listOf(
            RecommendedItem("item_chm_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Snap Chomp inflicts double bleed.", "+22% DPS", true, "Shark Lagoon")
        ),
        statPriorities = listOf("Water DMG%", "Bleed Bonus", "Crit Rate"),
        rotationGuide = listOf("1. Snap Chomp to start bleed.", "2. Whirlpool Dive to stagger.", "3. Tag into Whirlbite.")
    ),

    // No. 068: Whirlbite (Nova, Water)
    Aniimo(
        id = "whirlbite",
        name = "Whirlbite",
        title = "Abyssal Riptide Apex",
        element = Element.WATER,
        role = "Break & Riptide Maw Looping DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 068",
        evolvesFromId = "chompkin",
        baseAtk = 1930,
        baseDps = 45500,
        maxOptimizedDps = 175000,
        critRate = 33.5f,
        critDmg = 194.0f,
        attackSpeed = 1.68f,
        overview = "No. 068 in the Aniilog. The ferocious ocean apex Nova evolution of Chompkin. Loops Riptide Maw 5 consecutive times with Echoing Grimoire (+25% damage boost) to shatter Kraken armor in 44 seconds.",
        faceDrawableResName = "img_face_whirlbite",
        skills = listOf(
            AniimoSkill(
                id = "whirl_s1",
                name = "Tidal Fin Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Hydro Blade",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Swipes with razor dorsal fins, shredding 25% water defense.",
                dpsSynergyEffect = "Applies Deep Bleed."
            ),
            AniimoSkill(
                id = "whirl_s2",
                name = "Riptide Maw Frenzy",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Whirlpool Maw",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 175000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +30% Reset Chance & 860% Maw Slam",
                enhancedDpsPerSec = 212000,
                description = "Core signature skill. Churns a colossal whirlpool maw that resets cooldown on kill or stagger. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Can be looped 5 times consecutively."
            ),
            AniimoSkill(
                id = "whirl_pass",
                name = "Apex Ocean Predator",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage vs Soaked",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +30% increased damage to soaked or bleeding enemies.",
                dpsSynergyEffect = "Powers sub-45 second Kraken speedruns."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dewple", "Dewple", Element.WATER, "S+", 40, "Abyssal Current", "Dewple shreds water resistance, Whirlbite loops Maw.", "+35% Boss DMG", "Shred with Dewple then loop Riptide Maw.")
        ),
        bestItems = listOf(
            RecommendedItem("item_whirl_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Riptide Maw gains +25% damage boost.", "+36% Total DPS", true, "Alpha Kraken Sub-45 Run")
        ),
        statPriorities = listOf("Water DMG%", "Reset Chance", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Tidal Fin Slash to shred defense.", "2. Loop Riptide Maw Frenzy 5 times consecutively.", "3. Execute with Deep Bleed.")
    ),

    // No. 069: Fulmintis (Nova, Lightning)
    Aniimo(
        id = "fulmintis",
        name = "Fulmintis",
        title = "Prismana Storm Sovereign",
        element = Element.LIGHTNING,
        role = "Burst & Crit Snowball DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 069",
        evolvesFromId = "wisptis",
        baseAtk = 1960,
        baseDps = 46500,
        maxOptimizedDps = 178000,
        critRate = 34.0f,
        critDmg = 195.0f,
        attackSpeed = 1.70f,
        overview = "No. 069 in the Aniilog. The premiere S-Rank Lightning and Light Prismana carry evolved from Wisptis. Utilizes Electro Stash to refund 4 EP on every critical hit, creating an unstoppable snowball loop of high-frequency slashes.",
        faceDrawableResName = "img_face_fulmintis",
        skills = listOf(
            AniimoSkill(
                id = "ful_s1",
                name = "Thunder Flash Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Lightning Physical",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 120000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Executes rapid high-frequency lightning slashes through enemies, generating static charges.",
                dpsSynergyEffect = "Triggers Electro Stash to restore 4 EP on critical hits."
            ),
            AniimoSkill(
                id = "ful_s2",
                name = "Enhanced Light Blade",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "680% Light/Lightning Strike",
                cooldownSec = 9,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 178000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Pendant (+15 Contract)",
                enhancedMultiplier = "Grants +25% Skill Crit Rate for 20s & 850% ATK Slash",
                enhancedDpsPerSec = 210000,
                description = "Core signature skill. Imbues claws with pure radiant energy, granting +25% skill critical rate for 20s. Enhanced by Maxed Pendant (+15) for maximum slash frequency.",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees crit resets and enables continuous skill looping."
            ),
            AniimoSkill(
                id = "ful_ult",
                name = "Judgment Storm",
                type = SkillType.BASE_SKILL_2,
                damageMultiplier = "1650% Thunder Burst",
                cooldownSec = 18,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 165000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Calls down a colossal judgment lightning bolt that detonates all static charges and applies heavy Stun.",
                dpsSynergyEffect = "Deals +50% bonus Break damage against boss stagger gauges."
            ),
            AniimoSkill(
                id = "ful_pass",
                name = "Electro Stash",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "4 EP Refund / Crit",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 45000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Restores 4 Energy Points (EP) whenever any skill lands a critical hit.",
                dpsSynergyEffect = "Eliminates EP downtime during combat, allowing endless ability spam."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 42, "Radiant Crit Amplification", "Dazmand's active aura multiplies team critical hit damage by 40% and increases critical strike chance by 15%.", "+30% Critical multiplier during boss Break phase.", "Pop Dazmand's Crit Amp field right before Fulmintis casts Enhanced Light Blade."),
            PartnerSynergy("somniwing", "Somniwing", Element.WIND, "S+", 38, "Gale EP Battery", "Somniwing generates rapid Energy Points and reduces team skill cooldowns by 20%.", "Instant 25 EP refund on partner switch.", "Swap Somniwing to top off EP bar, then return to Fulmintis for Judgment Storm.")
        ),
        bestItems = listOf(
            RecommendedItem("item_ful_pendant", "Pendant (+15 Contracted)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+45% Slash DMG & +380 ATK", "Signature BiS: Maximizes Fulmintis's slash frequency and grants +25% skill damage under contract.", "+38.5% Total DPS", true, "Alpha Fulmintis Raid Drop (Contract +15)"),
            RecommendedItem("item_ful_fang", "Ferocious Fang (+15)", ItemSlot.PRIMARY_HELD_ITEM, "Legendary", "+55% ATK & +30% Break DMG", "Enhanced Light Blade deals +35% damage during Break windows.", "+32.0% Total DPS", true, "Alpha Nether Colossus")
        ),
        statPriorities = listOf("Crit Rate (target: 70%+)", "Crit DMG (target: 220%+)", "Lightning DMG% (target: 75%+)", "Energy Recharge / EP Efficiency", "Attack Speed (+20%+)"),
        rotationGuide = listOf("1. Activate Dazmand's Radiant Crit Amplification field.", "2. Cast Enhanced Light Blade (+25% crit rate for 20s).", "3. Spam Thunder Flash Slash; Electro Stash refunds 4 EP on every crit.", "4. Unleash Judgment Storm on frame 1 of boss Break stagger window.")
    ),

    // No. 070: Wavwal (Nova, Ice)
    Aniimo(
        id = "wavwal",
        name = "Wavwal",
        title = "Glacial Narwhal Sovereign",
        element = Element.ICE,
        role = "Break & Cryo Armor Shatter",
        metaTier = "S+",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 070",
        baseAtk = 1890,
        baseDps = 43800,
        maxOptimizedDps = 169000,
        critRate = 31.5f,
        critDmg = 186.0f,
        attackSpeed = 1.60f,
        overview = "No. 070 in the Aniilog. The titanic glacial narwhal sovereign. Rams targets with a 10-foot crystalline ice horn, freezing Alpha boss armor in 12 seconds flat.",
        faceDrawableResName = "img_face_malevsera",
        skills = listOf(
            AniimoSkill(
                id = "wav_s1",
                name = "Glacial Horn Impale",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Cryo Horn",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Impales with a glacial horn, freezing targets instantly.",
                dpsSynergyEffect = "Shreds 30% ice resistance."
            ),
            AniimoSkill(
                id = "wav_s2",
                name = "Permafrost Breach",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Glacier Shatter",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 169000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +35% Shatter Multiplier & 830% Horn Blast",
                enhancedDpsPerSec = 202000,
                description = "Core signature skill. Drives horn into bedrock releasing frozen tsunamis. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 45% of boss Break bars."
            ),
            AniimoSkill(
                id = "wav_pass",
                name = "Absolute Zero Aura",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Freeze Duration",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Boss freeze duration extended by 25%.",
                dpsSynergyEffect = "Sets up Gachapus and Whirlbite clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("gachapus", "Gachapus", Element.WATER, "S+", 42, "Ice-Water Hyper Break", "Wavwal breaks Kraken in 12s, Gachapus melts HP.", "+38% Clear Speed", "Impale with Wavwal then tag Gachapus.")
        ),
        bestItems = listOf(
            RecommendedItem("item_wav_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Permafrost Breach deals +35% damage.", "+34% Total DPS", true, "Alpha Kraken Raid")
        ),
        statPriorities = listOf("Break Efficiency", "Ice DMG%", "Freeze Duration", "Crit Rate"),
        rotationGuide = listOf("1. Glacial Horn Impale to freeze.", "2. Permafrost Breach to stagger boss.", "3. Tag into Gachapus.")
    ),

    // No. 071: Bailite (Lumin, Earth)
    Aniimo(
        id = "bailite",
        name = "Bailite",
        title = "Bedrock Armored Sentinel",
        element = Element.EARTH,
        role = "Break & Armor Shred Support",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 071",
        baseAtk = 1820,
        baseDps = 41500,
        maxOptimizedDps = 161000,
        critRate = 29.0f,
        critDmg = 176.0f,
        attackSpeed = 1.52f,
        overview = "No. 071 in the Aniilog. The premiere Earth-element armor shredder. Slams the ground with stone pillars that shred 35% earth defense, enabling Grizbo and Thornblade world-record clears.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "bai_s1",
                name = "Bedrock Sledge",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "380% Earth Hammer",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 112000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Swings a bedrock hammer shredding 35% target defense.",
                dpsSynergyEffect = "Applies Armor Shatter for 12s."
            ),
            AniimoSkill(
                id = "bai_s2",
                name = "Seismic Fault Line",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "630% Fissure Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 161000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Armor Shred & 780% Fissure Slam",
                enhancedDpsPerSec = 192000,
                description = "Core signature skill. Rips open an earth fault line beneath foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 40% of poise."
            ),
            AniimoSkill(
                id = "bai_pass",
                name = "Fortified Bedrock",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Team Break DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 39000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Party deals +25% Break damage against staggered targets.",
                dpsSynergyEffect = "Vital for sub-40 second clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("grizbo", "Grizbo", Element.EARTH, "S+", 40, "Titan Shatter", "Bailite shreds 35% defense, Grizbo one-shots.", "+35% Boss Damage", "Sledge then tag Grizbo.")
        ),
        bestItems = listOf(
            RecommendedItem("item_bai_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Fault Line deals +30% Break damage.", "+31% Total DPS", true, "Thunder Sovereign Alpha")
        ),
        statPriorities = listOf("Armor Shred", "Break Efficiency", "Earth DMG%"),
        rotationGuide = listOf("1. Bedrock Sledge to shred 35% defense.", "2. Seismic Fault Line to stagger boss.", "3. Tag into Grizbo or Thornblade.")
    ),

    // No. 072: Saws (Nova, Earth)
    Aniimo(
        id = "saws",
        name = "Saws",
        title = "Iron Sawblade Golem",
        element = Element.EARTH,
        role = "Break & Continuous Saw Shred",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 072",
        baseAtk = 1870,
        baseDps = 42800,
        maxOptimizedDps = 166000,
        critRate = 30.5f,
        critDmg = 182.0f,
        attackSpeed = 1.60f,
        overview = "No. 072 in the Aniilog. The mechanical earth-cutting golem. Spins dual high-torque buzz saws that rip through mineral armor and boss shields with relentless continuous ticks.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "saw_s1",
                name = "Buzz Saw Cleave",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "390% Rotational Shred",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 115000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Spins twin sawblades that shred 25% target defense.",
                dpsSynergyEffect = "Ticks 6 times over 2 seconds."
            ),
            AniimoSkill(
                id = "saw_s2",
                name = "Overdrive Diamond Saw",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "650% Industrial Grinder",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 166000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Fission Needles (+15)",
                enhancedMultiplier = "Grants +30% Grinder Damage & 800% Saw Slam",
                enhancedDpsPerSec = 196000,
                description = "Core signature skill. Overheats motors to grind through boss shields. Enhanced by Maxed Fission Needles (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. High Break efficiency."
            ),
            AniimoSkill(
                id = "saw_pass",
                name = "Diamond Coating",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Continuous Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 40000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases damage of multi-tick continuous skills by 20%.",
                dpsSynergyEffect = "Maximizes on-hit relic synergy."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("bailite", "Bailite", Element.EARTH, "S+", 36, "Mechanical Quarry", "Bailite defense shred doubles saw ticks.", "+28% Earth DMG", "Shred then grind with Diamond Saw.")
        ),
        bestItems = listOf(
            RecommendedItem("item_saw_need", "Fission Needles (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Saw Cleave has 50% chance for double hits.", "+32% DPS", true, "Industrial Quarry")
        ),
        statPriorities = listOf("Earth DMG%", "Continuous Tick Rate", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Buzz Saw Cleave to shred armor.", "2. Overdrive Diamond Saw on stagger.", "3. Weave basic attacks.")
    ),

    // No. 073: Jeburst (Nova, Fire/Electric)
    Aniimo(
        id = "jeburst",
        name = "Jeburst",
        title = "Plasma Jet Striker",
        element = Element.FIRE,
        secondaryElement = Element.LIGHTNING,
        role = "Break & Plasma Rocket Burst",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 073",
        baseAtk = 1880,
        baseDps = 43200,
        maxOptimizedDps = 167000,
        critRate = 31.5f,
        critDmg = 185.0f,
        attackSpeed = 1.64f,
        overview = "No. 073 in the Aniilog. The rocket-propelled Fire/Electric burst specialist. Ignites turbo plasma thrusters to deliver supersonic ramming attacks that blast bosses across the arena.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "jeb_s1",
                name = "Plasma Thruster Dash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Fire/Electric Rush",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 117000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Ignites turbo thrusters to ram through aligned targets.",
                dpsSynergyEffect = "Electrifies and burns simultaneously."
            ),
            AniimoSkill(
                id = "jeb_s2",
                name = "Rocket Supernova Plunge",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Turbo Explosion",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 167000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Rocket Stagger & 820% Plunge Impact",
                enhancedDpsPerSec = 199000,
                description = "Core signature skill. Rocket climbs to sky ceiling and crashes down like a missile. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 40% of boss poise."
            ),
            AniimoSkill(
                id = "jeb_pass",
                name = "Turbo Afterburner",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Speed & Burst",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Gains +25% attack speed and +20% damage after using mobility skills.",
                dpsSynergyEffect = "Rewards aggressive repositioning."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Plasma Overload", "Dual Fire and Lightning trigger overload explosions.", "+32% Burst DMG", "Plunge then tag Fulmintis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_jeb_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Rocket Supernova deals +35% damage.", "+33% Total DPS", true, "Rocket Spire")
        ),
        statPriorities = listOf("Fire DMG%", "Lightning DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Plasma Thruster Dash to ignite.", "2. Rocket Supernova Plunge on Break.", "3. Tag into carry.")
    ),

    // No. 074: Jaguaraun (Standalone, Lightning)
    Aniimo(
        id = "jaguaraun",
        name = "Jaguaraun",
        title = "Thunder Fang Stalker",
        element = Element.LIGHTNING,
        role = "Lightning Break & Piercing Claws",
        metaTier = "S+",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 074",
        baseAtk = 1910,
        baseDps = 44500,
        maxOptimizedDps = 171000,
        critRate = 33.0f,
        critDmg = 192.0f,
        attackSpeed = 1.68f,
        overview = "No. 074 in the Aniilog. The Whisperwake Isles high-agility lightning feline. Renowned for maximum Break capability, tearing through boss super-armor with electrified claw sweeps.",
        faceDrawableResName = "img_face_fulmintis",
        skills = listOf(
            AniimoSkill(
                id = "jag_s1",
                name = "Thunder Claw Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "430% Lightning Physical",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 125000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Rapid triple claw swipe infusing targets with Galvanic Weakness (-15% Lightning Def).",
                dpsSynergyEffect = "Restores 4 EP when landing a critical blow."
            ),
            AniimoSkill(
                id = "jag_s2",
                name = "Volt Pounce Breaker",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Lightning Impact",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 171000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +35% Poise Break & 850% Shockwave",
                enhancedDpsPerSec = 208000,
                description = "Core signature skill. Leaps high and crashes down with a 50,000-volt shockwave that drains 40% of boss poise shields. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees stagger against electrified targets."
            ),
            AniimoSkill(
                id = "jag_pass",
                name = "Stalker Instinct",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Break Multiplier",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +25% bonus damage to bosses during the Break stagger window.",
                dpsSynergyEffect = "Synergizes with Fulmintis for speedrun records."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Thunder Storm Alpha", "Dual Lightning Hyper-Break boosts team raid DPS by 35%.", "+30% Critical damage.", "Break poise with Volt Pounce then swap.")
        ),
        bestItems = listOf(
            RecommendedItem("item_jag_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Volt Pounce deals 35% extra Break damage.", "+35% Total DPS", true, "Highland Alpha"),
            RecommendedItem("item_jag_batt", "Capacitous Battery", ItemSlot.SECONDARY_HELD_ITEM, "Legendary", "+20% Damage Amp", "Refunds 12 EP on Break trigger.", "+20% DPS", true, "Alpha Drop")
        ),
        statPriorities = listOf("Break Efficiency", "Lightning DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Apply Galvanic Weakness with Thunder Claw.", "2. Stagger boss with Volt Pounce Breaker.", "3. Burst during Break window.")
    ),

    // No. 075: Reefish (Standalone, Water/Earth)
    Aniimo(
        id = "reefish",
        name = "Reefish",
        title = "Breezy Shoal Glider",
        element = Element.WATER,
        secondaryElement = Element.EARTH,
        role = "Submerged Torrent Striker",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 075",
        baseAtk = 1880,
        baseDps = 43200,
        maxOptimizedDps = 166000,
        critRate = 32.0f,
        critDmg = 188.0f,
        attackSpeed = 1.64f,
        overview = "No. 075 in the Aniilog. The signature marine fish of Whisperwake Isles (Sub-index No. 001). Glides seamlessly above coral reefs, summoning whirlpool torrents that pull and drown entire waves.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "reef_s1",
                name = "Coral Fin Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Water Blade",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Slices with calcified coral fin blades, applying 20% water vulnerability.",
                dpsSynergyEffect = "Leaves water puddle on contact."
            ),
            AniimoSkill(
                id = "reef_s2",
                name = "Vortex Shoal Maw",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Pressurized Whirlpool",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 166000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15 Contract)",
                enhancedMultiplier = "Grants +30% Vortex Pull & 820% Shoal Burst",
                enhancedDpsPerSec = 198000,
                description = "Core signature skill. Summons a school of vortex fish that pull targets into a crushing whirlpool. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Ticks 6 times over 3 seconds."
            ),
            AniimoSkill(
                id = "reef_pass",
                name = "Shoal Harmony",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Water Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party water damage by 20% while in water terrain.",
                dpsSynergyEffect = "Pairs with Sherro and Whirlbite."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("sherro", "Sherro", Element.WATER, "S+", 38, "Shoal Surf", "Permanent puddle coverage for 100% buff uptime.", "+30% Water DMG", "Vortex Shoal then let Sherro dive.")
        ),
        bestItems = listOf(
            RecommendedItem("item_reef_grim", "Echoing Grimoire (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Vortex Shoal deals 35% extra damage.", "+33% Total DPS", true, "Whisperwake Alpha Kraken")
        ),
        statPriorities = listOf("Water DMG%", "Area Cleave", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Coral Fin Slash to apply vulnerability.", "2. Group enemies with Vortex Shoal Maw.", "3. Burst with basic attacks.")
    )
)
