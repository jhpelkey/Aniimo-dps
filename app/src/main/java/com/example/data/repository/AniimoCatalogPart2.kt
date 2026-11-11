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
 * Canonical Aniilog Catalog Part 2: No. 026 to No. 050
 * Includes Burrow Novas, Rose/Sword line, Dark/Ignitis, Cryo wolves, Electric, and Tidal lines.
 */
val aniimoCatalogPart2: List<Aniimo> = listOf(
    // No. 026: Geoclaw (Nova, Ice/Earth)
    Aniimo(
        id = "geoclaw",
        name = "Geoclaw",
        title = "Tectonic Permafrost Titan",
        element = Element.ICE,
        secondaryElement = Element.EARTH,
        role = "Break & Cryo-Earth Seismic DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 026",
        evolvesFromId = "shrubclaw",
        baseAtk = 1910,
        baseDps = 44500,
        maxOptimizedDps = 172000,
        critRate = 32.5f,
        critDmg = 189.0f,
        attackSpeed = 1.58f,
        overview = "No. 026 in the Aniilog. The Nova stage apex evolution of Budclaw. Unites glacial permafrost with tectonic plates to crush enemy stagger gauges with earth-shattering fissure slams.",
        faceDrawableResName = "img_face_grizbo",
        skills = listOf(
            AniimoSkill(
                id = "geo_s1",
                name = "Permafrost Shockwave",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Cryo/Earth",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 119000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Slams the ground sending a shockwave of jagged ice spires.",
                dpsSynergyEffect = "Reduces enemy attack speed by 25%."
            ),
            AniimoSkill(
                id = "geo_s2",
                name = "Tectonic Glacier Cataclysm",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Seismic Shatter",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 172000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Break Stagger & 830% Seismic Impact",
                enhancedDpsPerSec = 206000,
                description = "Core signature skill. Rips open a continental ice rift beneath foes. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 45% of boss Break bars."
            ),
            AniimoSkill(
                id = "geo_pass",
                name = "Glacial Core Anchor",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Poise Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Immune to knockback and deals +25% poise damage while on earth or ice terrain.",
                dpsSynergyEffect = "Enables uninterrupted boss shield shredding."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 39, "Shatter Shock", "Geoclaw breaks shields, Fulmintis slashes for crit records.", "+35% Boss DMG", "Tectonic Cataclysm then swap.")
        ),
        bestItems = listOf(
            RecommendedItem("item_geo_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Tectonic Glacier deals +35% Break damage.", "+34% Total DPS", true, "Alpha Tundra")
        ),
        statPriorities = listOf("Break Efficiency", "Ice DMG%", "Earth DMG%", "Crit Rate"),
        rotationGuide = listOf("1. Permafrost Shockwave to debuff.", "2. Tectonic Glacier Cataclysm during boss charge.", "3. Tag into burst DPS.")
    ),

    // No. 027: Jabster (Nova, Dark/Ice)
    Aniimo(
        id = "jabster",
        name = "Jabster",
        title = "Abyssal Pincer Bruiser",
        element = Element.DARK,
        secondaryElement = Element.ICE,
        role = "Break & Rapid Pincer Striker",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 027",
        baseAtk = 1860,
        baseDps = 42800,
        maxOptimizedDps = 166000,
        critRate = 31.0f,
        critDmg = 186.0f,
        attackSpeed = 1.66f,
        overview = "No. 027 in the Aniilog. The deep-sea Dark/Ice boxing crustacean. Delivers blistering machine-gun pincer jabs that shatter crystalline armor and freeze underwater targets.",
        faceDrawableResName = "img_face_malevsera",
        skills = listOf(
            AniimoSkill(
                id = "jab_s1",
                name = "Mach Pincer Jab",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "390% Rapid Dark Strike",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 117000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires off 6 high-speed boxing strikes in one second.",
                dpsSynergyEffect = "Builds combo meter rapidly."
            ),
            AniimoSkill(
                id = "jab_s2",
                name = "Frostbite Uppercut",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "650% Cryo Dark Impact",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 166000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Fission Needles (+15)",
                enhancedMultiplier = "Grants +25% Double Punch & 800% Uppercut",
                enhancedDpsPerSec = 196000,
                description = "Core signature skill. Launches targets into the air with a frozen sonic boom. Enhanced by Maxed Fission Needles (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Freezes on critical hit."
            ),
            AniimoSkill(
                id = "jab_pass",
                name = "Iron Shell Stance",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Attack Speed",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 40000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Gains +20% attack speed when landing consecutive blows.",
                dpsSynergyEffect = "Maximizes on-hit relic triggers."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("whirlbite", "Whirlbite", Element.WATER, "S+", 36, "Abyssal Boxers", "Whirlbite water enables permanent frostbite.", "+30% Damage", "Tag team rapid combos.")
        ),
        bestItems = listOf(
            RecommendedItem("item_jab_need", "Fission Needles (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+50% ATK", "Mach Pincer has 50% chance to double hit.", "+32% DPS", true, "Abyssal Trench")
        ),
        statPriorities = listOf("Dark DMG%", "Attack Speed", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Mach Pincer Jab to ramp attack speed.", "2. Frostbite Uppercut on stagger.", "3. Continue machine-gun boxing.")
    ),

    // No. 028: Flamerion (Nova, Fire)
    Aniimo(
        id = "flamerion",
        name = "Flamerion",
        title = "Solar Fire Lion",
        element = Element.FIRE,
        role = "Solar Burst & Area Cleave",
        metaTier = "S",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 028",
        baseAtk = 1870,
        baseDps = 43200,
        maxOptimizedDps = 167000,
        critRate = 31.5f,
        critDmg = 188.0f,
        attackSpeed = 1.60f,
        overview = "No. 028 in the Aniilog. The majestic standalone solar lion. Basks in daylight to superheat its mane, discharging wide-arc radiant flame waves that scorch enemy squads.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "flam_s1",
                name = "Solar Claw Arc",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Solar Slash",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Sweeps an expansive crescent wave of solar plasma.",
                dpsSynergyEffect = "Ignites all enemies in an 8m arc."
            ),
            AniimoSkill(
                id = "flam_s2",
                name = "Blazing Pride Roar",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Solar Supernova",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 167000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Pyro Amp & 820% Lion Roar",
                enhancedDpsPerSec = 199000,
                description = "Core signature skill. Roars with blinding solar radiance. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Blinds and burns."
            ),
            AniimoSkill(
                id = "flam_pass",
                name = "Blazing Flames",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+22% Fire Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases team fire damage by 22% during battle.",
                dpsSynergyEffect = "Stacks with Scorchhowl."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 38, "Solar Flash", "Increases solar crit rate by 35%.", "+30% Crit Multiplier", "Cast Roar inside Dazmand's field.")
        ),
        bestItems = listOf(
            RecommendedItem("item_flam_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Blazing Pride Roar deals +35% damage.", "+33% Total DPS", true, "Solar Caldera")
        ),
        statPriorities = listOf("Fire DMG%", "Crit Rate", "Crit DMG", "Area Cleave"),
        rotationGuide = listOf("1. Solar Claw Arc to ignite squad.", "2. Blazing Pride Roar for area burst.", "3. Tag into single-target carry.")
    ),

    // No. 029: Flutternym (Lumin, Wind)
    Aniimo(
        id = "flutternym",
        name = "Flutternym",
        title = "Breeze Nymph",
        element = Element.WIND,
        role = "Flutter Heal & Wind Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 029",
        evolvesToIds = listOf("gracewing"),
        evolutionRequirement = "Lv. 20 Grace Feather",
        baseAtk = 810,
        baseDps = 13800,
        maxOptimizedDps = 53000,
        critRate = 21.0f,
        critDmg = 145.0f,
        attackSpeed = 1.44f,
        overview = "No. 029 in the Aniilog. The graceful wind nymph Lumin stage. Dances through gentle breezes, showering allies in soft fluttering petals that restore health and energy.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "flut_s1",
                name = "Zephyr Gust",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "270% Wind Gentle",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 48000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Flutters wings to blow a soothing healing breeze.",
                dpsSynergyEffect = "Heals nearest ally for 12% HP."
            ),
            AniimoSkill(
                id = "flut_s2",
                name = "Flutter Dance Bloom",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "420% Aero Petal",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 53000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Windstride Feather (+15)",
                enhancedMultiplier = "Grants +25% Heal & 520% Dance Strike",
                enhancedDpsPerSec = 65000,
                description = "Core signature skill. Spins gracefully to disperse restorative wind motes. Enhanced by Maxed Windstride Feather (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Cleanses negative status."
            ),
            AniimoSkill(
                id = "flut_pass",
                name = "Flutter Heal",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Healing Power",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 21000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party healing received by 15%.",
                dpsSynergyEffect = "Transfers into Gracewing."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("gracewing", "Gracewing", Element.WIND, "S+", 34, "Nymph Flight", "Doubles aerial flight duration.", "+22% Wind DMG", "Heal then swap to Gracewing.")
        ),
        bestItems = listOf(
            RecommendedItem("item_flut_feath", "Windstride Feather (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Flutter Dance cleanses all debuffs.", "+20% DPS", true, "Valley Sanctuary")
        ),
        statPriorities = listOf("Healing Power", "Wind DMG%", "Energy Regen"),
        rotationGuide = listOf("1. Cast Zephyr Gust to heal.", "2. Disperse Flutter Dance Bloom.", "3. Tag into Gracewing.")
    ),

    // No. 030: Gracewing (Nova, Wind)
    Aniimo(
        id = "gracewing",
        name = "Gracewing",
        title = "Sky Monarch Seraph",
        element = Element.WIND,
        role = "Aerial Superiority & Razor Gale DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 030",
        evolvesFromId = "flutternym",
        baseAtk = 1860,
        baseDps = 42200,
        maxOptimizedDps = 164000,
        critRate = 31.0f,
        critDmg = 184.0f,
        attackSpeed = 1.62f,
        overview = "No. 030 in the Aniilog. The celestial Nova evolution of Flutternym. Soars above the clouds on translucent prism wings, casting razor feathers that track and pin bosses.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "gra_s1",
                name = "Prism Feather Volley",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "390% Homing Quills",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 115000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires 8 homing prismatic wind feathers.",
                dpsSynergyEffect = "Applies 20% Wind Vulnerability."
            ),
            AniimoSkill(
                id = "gra_s2",
                name = "Seraphic Gale Dive",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "650% Aerial Plunge",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 164000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Aerial Crit & 800% Seraph Strike",
                enhancedDpsPerSec = 195000,
                description = "Core signature skill. Dives from maximum height with supersonic feathers. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees critical hits while airborne."
            ),
            AniimoSkill(
                id = "gra_pass",
                name = "Sky Sovereignty",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Airborne Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 40000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +25% increased damage while launching attacks from the air.",
                dpsSynergyEffect = "Enables safe aerial boss evasion."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("somniwing", "Somniwing", Element.WIND, "S+", 37, "Sky Dominion", "Infinite flight stamina and rapid feather loops.", "+30% Wind DMG", "Feather volley into Seraphic Dive.")
        ),
        bestItems = listOf(
            RecommendedItem("item_gra_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Seraphic Gale Dive deals +35% damage.", "+33% Total DPS", true, "Alpha Cloud Spire")
        ),
        statPriorities = listOf("Wind DMG%", "Airborne Damage", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Feather Volley to pin boss.", "2. Seraphic Gale Dive from high altitude.", "3. Re-engage aerial flight.")
    ),

    // No. 031: Somniwing (Nova, Wind/Grass)
    Aniimo(
        id = "somniwing",
        name = "Somniwing",
        title = "Gale EP Battery",
        element = Element.WIND,
        secondaryElement = Element.GRASS,
        role = "EP Battery & Cooldown Haste DPS",
        metaTier = "S+",
        stage = EvolutionStage.STANDALONE,
        aniilogNumber = "No. 031",
        baseAtk = 1870,
        baseDps = 43000,
        maxOptimizedDps = 168000,
        critRate = 32.0f,
        critDmg = 185.0f,
        attackSpeed = 1.65f,
        overview = "No. 031 in the Aniilog. The premiere standalone Wind/Grass battery. Generates rapid Energy Points and reduces party skill cooldowns by 20%, powering record-breaking leaderboard speedruns.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "som_s1",
                name = "Gale Feather Slice",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Wind Slash",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 117000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Slices through the air sending twin sonic blades forward.",
                dpsSynergyEffect = "Restores 10 EP on hit."
            ),
            AniimoSkill(
                id = "som_s2",
                name = "Hypnotic Dream Vortex",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Whirlwind Deluge",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 168000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Capacitous Battery (+15)",
                enhancedMultiplier = "Grants +30% EP Overflow & 820% Vortex",
                enhancedDpsPerSec = 202000,
                description = "Core signature skill. Spawns an EP-generating dream whirlwind that tops off the active team. Enhanced by Maxed Capacitous Battery (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Restores 25 EP instantly to team."
            ),
            AniimoSkill(
                id = "som_pass",
                name = "Gale Battery Resonator",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Cooldown Reduction",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively reduces team skill cooldowns by 20% permanently.",
                dpsSynergyEffect = "Eliminates all ability downtime across the roster."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 42, "Infinite Loop", "Fulmintis spends EP, Somniwing instantly refunds.", "+40% Team DPS", "Swap to Somniwing for instant 25 EP refund.")
        ),
        bestItems = listOf(
            RecommendedItem("item_som_batt", "Capacitous Battery (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Dream Vortex refunds 25 EP instantly.", "+34% Total DPS", true, "Highland Sanctum")
        ),
        statPriorities = listOf("Energy Regen", "Cooldown Reduction", "Wind DMG%", "Crit Rate"),
        rotationGuide = listOf("1. Cast Gale Feather Slice to start generation.", "2. Deploy Hypnotic Dream Vortex.", "3. Tag into burst carry.")
    ),

    // No. 032: Eko (Lumin, Wind)
    Aniimo(
        id = "eko",
        name = "Eko",
        title = "Falling Star Whistler",
        element = Element.WIND,
        role = "Wind Debuff & Storm Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 032",
        baseAtk = 830,
        baseDps = 14200,
        maxOptimizedDps = 56000,
        critRate = 22.0f,
        critDmg = 148.0f,
        attackSpeed = 1.45f,
        overview = "No. 032 in the Aniilog. The mystical Forest of Falling Stars wind sprite. Possesses the Judgment Storm trait, boosting whole-team Wind damage by 20% whenever an ability applies a debuff.",
        faceDrawableResName = "img_face_cornet",
        skills = listOf(
            AniimoSkill(
                id = "eko_s1",
                name = "Starlight Whistle",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Wind Song",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 50000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Trills an astral whistle that inflicts wind weakness.",
                dpsSynergyEffect = "Applies 15% Wind Vulnerability for 10s."
            ),
            AniimoSkill(
                id = "eko_s2",
                name = "Falling Star Gust",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Comet Whirl",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 56000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Windstride Feather (+15)",
                enhancedMultiplier = "Grants +25% Wind Boost & 550% Starlight Strike",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Calls down a small meteor enveloped in gale wind. Enhanced by Maxed Windstride Feather (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Triggers Judgment Storm."
            ),
            AniimoSkill(
                id = "eko_pass",
                name = "Judgment Storm",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Team Wind DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Applying a debuff with any skill increases team Wind damage by 20% for 15s.",
                dpsSynergyEffect = "Core synergy with Cornet and Somniwing."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("cornet", "Cornet", Element.WIND, "S+", 35, "Storm Choir", "Judgment Storm fuels Clarion Blast.", "+25% Wind Burst", "Debuff with Eko then swap to Cornet.")
        ),
        bestItems = listOf(
            RecommendedItem("item_eko_feath", "Windstride Feather (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Judgment Storm duration extended to 20s.", "+22% DPS", true, "Starfall Copse")
        ),
        statPriorities = listOf("Debuff Duration", "Wind DMG%", "Energy Regen"),
        rotationGuide = listOf("1. Starlight Whistle to debuff.", "2. Trigger Judgment Storm with Falling Star Gust.", "3. Tag into Cornet.")
    ),

    // No. 033: Budsquire (Lumin, Grass)
    Aniimo(
        id = "budsquire",
        name = "Budsquire",
        title = "Towerwood Blade Apprentice",
        element = Element.GRASS,
        role = "Sword Dance & Rapid Slashing DPS",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 033",
        evolvesToIds = listOf("thornblade", "melloblum"),
        evolutionRequirement = "Lv. 27 + Solo Boss Defeat: Thornblade / Floral Nectar: Melloblum",
        baseAtk = 860,
        baseDps = 14600,
        maxOptimizedDps = 59000,
        critRate = 24.0f,
        critDmg = 155.0f,
        attackSpeed = 1.48f,
        overview = "No. 033 in the Aniilog. The sword-wielding grass squire found in Rosetower Woods. Practices relentless Sword Dance routines that branch into Thornblade or Melloblum.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "budsq_s1",
                name = "Sprout Cut",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Leaf Blade",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 52000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Quick sword thrust with a sharpened wooden blade.",
                dpsSynergyEffect = "Builds 1 Sword Dance stack."
            ),
            AniimoSkill(
                id = "budsq_s2",
                name = "Sword Dance Flurry",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "450% Whirlwind Slashing",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 59000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Blade Might & 570% Flurry",
                enhancedDpsPerSec = 71000,
                description = "Core signature skill. Spins with twin wooden rapiers. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Grants +40% basic attack damage."
            ),
            AniimoSkill(
                id = "budsq_pass",
                name = "Sword Dance Trait",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+8% ATK per Stack",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Each Sword Dance stack grants +8% ATK (up to 5 stacks).",
                dpsSynergyEffect = "Transfers directly into Thornblade."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("thornblade", "Thornblade", Element.GRASS, "S+", 36, "Blade Lineage", "Full sword dance inheritance.", "+28% Grass DMG", "Max stacks then tag Thornblade.")
        ),
        bestItems = listOf(
            RecommendedItem("item_budsq_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Sword Dance max stacks increased to 6.", "+23% DPS", true, "Rosetower Clearing")
        ),
        statPriorities = listOf("Grass DMG%", "Crit Rate", "Crit DMG", "Attack Speed"),
        rotationGuide = listOf("1. Sprout Cut to build stacks.", "2. Sword Dance Flurry for flurry burst.", "3. Tag into Thornblade.")
    ),

    // No. 034: Thornblade (Nova, Grass)
    Aniimo(
        id = "thornblade",
        name = "Thornblade",
        title = "Bramble Knight Sovereign",
        element = Element.GRASS,
        role = "Sword Dance Stacks & Critical Shred",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 034",
        evolvesFromId = "budsquire",
        baseAtk = 1930,
        baseDps = 45200,
        maxOptimizedDps = 174000,
        critRate = 34.0f,
        critDmg = 195.0f,
        attackSpeed = 1.70f,
        overview = "No. 034 in the Aniilog. The master swordsman Nova evolution of Budsquire. Wields duel bramble rapiers with maxed Sword Dance stacks to tear through Alpha bosses in record times.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "tho_s1",
                name = "Bramble Impale",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Thorn Pierce",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 122000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Lunges forward with a piercing thorn rapier strike, penetrating 25% defense.",
                dpsSynergyEffect = "Builds 2 Sword Dance stacks."
            ),
            AniimoSkill(
                id = "tho_s2",
                name = "Ironthorn Cyclone Execution",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "680% Blade Storm",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 174000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Fission Needles (+15 Contract)",
                enhancedMultiplier = "Grants +35% Double Strike & 840% Cyclone",
                enhancedDpsPerSec = 210000,
                description = "Core signature skill. Spins into an impenetrable razor cyclone. Enhanced by Maxed Fission Needles (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Consumes Sword Dance to double critical damage."
            ),
            AniimoSkill(
                id = "tho_pass",
                name = "Ironbark Mastery",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+40% Basic Attack DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "At 5 Sword Dance stacks, basic attacks deal +40% extra damage and cannot be deflected.",
                dpsSynergyEffect = "Allows lightning-fast shredding of boss health."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("bailite", "Bailite", Element.EARTH, "S+", 38, "Earth-Wood Shred", "Bailite shreds 35% armor; Thornblade executes.", "+32% Boss Damage", "Break with Bailite then cast Ironthorn Cyclone.")
        ),
        bestItems = listOf(
            RecommendedItem("item_tho_need", "Fission Needles (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Ironthorn Cyclone has 50% chance for double hits.", "+36% Total DPS", true, "Alpha Magma Wyrm Run")
        ),
        statPriorities = listOf("Grass DMG%", "Crit Rate", "Crit DMG", "Sword Dance Stacks"),
        rotationGuide = listOf("1. Bramble Impale to build max stacks.", "2. Ironthorn Cyclone Execution on Break.", "3. Basic attack weave during stagger.")
    ),

    // No. 035: Melloblum (Gamma, Grass)
    Aniimo(
        id = "melloblum",
        name = "Melloblum",
        title = "Honey Nectar Blossom",
        element = Element.GRASS,
        role = "Nectar Healing & Haste Support",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 035",
        evolvesFromId = "budsquire",
        baseAtk = 1320,
        baseDps = 27500,
        maxOptimizedDps = 106000,
        critRate = 25.0f,
        critDmg = 160.0f,
        attackSpeed = 1.44f,
        overview = "No. 035 in the Aniilog. The alternative Gamma evolution of Budsquire in Rosetower Woods. Produces sweet healing honey nectar that boosts team attack speed and cleanses status.",
        faceDrawableResName = "img_face_irisalis",
        skills = listOf(
            AniimoSkill(
                id = "mel_s1",
                name = "Honey Splash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "350% Nectar Burst",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 88000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Sprays sticky golden nectar that slows foes by 30%.",
                dpsSynergyEffect = "Heals allies in splash zone."
            ),
            AniimoSkill(
                id = "mel_s2",
                name = "Sweet Blossom Surge",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "580% Pollen Cloud",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 106000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +25% Party Haste & 720% Honey Strike",
                enhancedDpsPerSec = 130000,
                description = "Core signature skill. Releases a therapeutic aroma granting +25% party haste. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Cleanses all poison and burns."
            ),
            AniimoSkill(
                id = "mel_pass",
                name = "Nectar Shield",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% HP Shield",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 28000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Grants active companion a honey shield that absorbs 15% max HP.",
                dpsSynergyEffect = "Keeps fragile DPS carries alive."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("thornblade", "Thornblade", Element.GRASS, "S+", 36, "Rose Sweetness", "Haste allows Thornblade to stack Sword Dance 50% faster.", "+25% Attack Speed", "Apply Sweet Blossom then swap to Thornblade.")
        ),
        bestItems = listOf(
            RecommendedItem("item_mel_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+45% ATK", "Sweet Blossom duration +4s.", "+24% DPS", true, "Rosetower Alpha")
        ),
        statPriorities = listOf("Healing Power", "Grass DMG%", "Party Haste"),
        rotationGuide = listOf("1. Honey Splash to slow boss.", "2. Sweet Blossom Surge for team haste.", "3. Tag into carry.")
    ),

    // No. 036: Pomegg (Lumin, Grass)
    Aniimo(
        id = "pomegg",
        name = "Pomegg",
        title = "Pomegranate Seedling",
        element = Element.GRASS,
        role = "Break & Seed Cluster Striker",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 036",
        evolvesToIds = listOf("pomawk"),
        evolutionRequirement = "Lv. 21 Crimson Rind",
        baseAtk = 850,
        baseDps = 14400,
        maxOptimizedDps = 57000,
        critRate = 23.0f,
        critDmg = 152.0f,
        attackSpeed = 1.40f,
        overview = "No. 036 in the Aniilog. The armored pomegranate seed Lumin stage. Encased in a tough ruby rind, it fires explosive ruby seeds that burst upon hitting enemy shields.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "pom_s1",
                name = "Seed Spit",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Ruby Seed",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 51000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Spits a barrage of 5 hard ruby seeds.",
                dpsSynergyEffect = "Chunks poise meters."
            ),
            AniimoSkill(
                id = "pom_s2",
                name = "Pomegg Shell Slam",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Heavy Rind",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 57000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Poise Dmg & 550% Shell Slam",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Launches forward like a cannonball. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Causes stagger."
            ),
            AniimoSkill(
                id = "pom_pass",
                name = "Ruby Armor",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Defense",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases Break effectiveness by 15% against elite bosses.",
                dpsSynergyEffect = "Transfers into Pomawk."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("pomawk", "Pomawk", Element.GRASS, "S+", 34, "Rind Power", "Seed explosion damage +30%.", "+22% Break DMG", "Slam then tag Pomawk.")
        ),
        bestItems = listOf(
            RecommendedItem("item_pom_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Shell Slam deals +25% Break.", "+21% DPS", true, "Orchard Alpha")
        ),
        statPriorities = listOf("Break Efficiency", "Grass DMG%", "Crit Rate"),
        rotationGuide = listOf("1. Seed Spit to whittle poise.", "2. Pomegg Shell Slam to stagger.", "3. Tag into Pomawk.")
    ),

    // No. 037: Pomawk (Nova, Grass)
    Aniimo(
        id = "pomawk",
        name = "Pomawk",
        title = "Crimson Crest Breaker",
        element = Element.GRASS,
        role = "Break & Explosive Seed Burst",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 037",
        evolvesFromId = "pomegg",
        baseAtk = 1880,
        baseDps = 43000,
        maxOptimizedDps = 167000,
        critRate = 32.0f,
        critDmg = 186.0f,
        attackSpeed = 1.62f,
        overview = "No. 037 in the Aniilog. The warrior bird Nova evolution of Pomegg. Sports a fiery crimson crest and hurls exploding ruby seed clusters that blast away boss armor.",
        faceDrawableResName = "img_face_thornblade",
        skills = listOf(
            AniimoSkill(
                id = "pomk_s1",
                name = "Crimson Seed Barrage",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Explosive Seeds",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 118000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Hurls a volley of exploding seeds that detonate on impact.",
                dpsSynergyEffect = "Shreds 25% enemy defense."
            ),
            AniimoSkill(
                id = "pomk_s2",
                name = "Pome Crest Divebomb",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Aerial Rind Slam",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 167000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Break Stagger & 820% Divebomb",
                enhancedDpsPerSec = 199000,
                description = "Core signature skill. Dives headfirst with armored crest like a missile. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Deals 1.4x Break damage."
            ),
            AniimoSkill(
                id = "pomk_pass",
                name = "Detonation Shrapnel",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Area Explosion",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Seed explosions leave sharp thorns that damage moving enemies.",
                dpsSynergyEffect = "Synergizes with team gather abilities."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("thornblade", "Thornblade", Element.GRASS, "S+", 38, "Verdant Shred", "Pomawk breaks shields, Thornblade executes.", "+32% Boss DMG", "Divebomb then let Thornblade slash.")
        ),
        bestItems = listOf(
            RecommendedItem("item_pomk_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Divebomb deals +35% Break damage.", "+33% Total DPS", true, "Alpha Orchard Drop")
        ),
        statPriorities = listOf("Break Efficiency", "Grass DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Crimson Seed Barrage to shred defense.", "2. Pome Crest Divebomb to Break boss.", "3. Tag into carry.")
    ),

    // No. 038: Dewy (Lumin, Dark)
    Aniimo(
        id = "dewy",
        name = "Dewy",
        title = "Nocturnal Mist Sprite",
        element = Element.DARK,
        role = "Shadow Mist & Blind Support",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 038",
        evolvesToIds = listOf("fragrancier"),
        evolutionRequirement = "Lv. 22 Night Petal",
        baseAtk = 820,
        baseDps = 14000,
        maxOptimizedDps = 54000,
        critRate = 22.0f,
        critDmg = 148.0f,
        attackSpeed = 1.42f,
        overview = "No. 038 in the Aniilog. The elusive nocturnal shadow-droplet Lumin stage. Cloaks itself in misty twilight aromas that confuse monsters and veil companions from detection.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "dewy_s1",
                name = "Shadow Mist Puff",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Dark Mist",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 49000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Releases a veil of shadow mist that blinds enemies.",
                dpsSynergyEffect = "Targets miss 25% of attacks."
            ),
            AniimoSkill(
                id = "dewy_s2",
                name = "Nocturnal Petal Wave",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "430% Dark Aroma",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 54000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +20% Curse & 530% Shadow Petal",
                enhancedDpsPerSec = 66000,
                description = "Core signature skill. Sends a wave of black flower petals that hexes foes. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Amplifies team dark damage."
            ),
            AniimoSkill(
                id = "dewy_pass",
                name = "Twilight Cloak",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+12% Team Stealth DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 21000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Party deals +12% damage from inside shadow mist.",
                dpsSynergyEffect = "Transfers into Fragrancier."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fragrancier", "Fragrancier", Element.DARK, "S+", 34, "Aroma Depth", "Extends mist duration by 50%.", "+22% Dark DMG", "Mist with Dewy then tag Fragrancier.")
        ),
        bestItems = listOf(
            RecommendedItem("item_dewy_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+35% ATK", "Shadow Mist area +40%.", "+20% DPS", true, "Night Grove")
        ),
        statPriorities = listOf("Dark DMG%", "Blind Duration", "Cooldown Reduction"),
        rotationGuide = listOf("1. Puff Shadow Mist.", "2. Wave Nocturnal Petals.", "3. Tag into Fragrancier.")
    ),

    // No. 039: Fragrancier (Gamma, Dark)
    Aniimo(
        id = "fragrancier",
        name = "Fragrancier",
        title = "Dusk Perfume Weaver",
        element = Element.DARK,
        role = "Aroma Hex & Damage Amplification",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 039",
        evolvesFromId = "dewy",
        baseAtk = 1350,
        baseDps = 28400,
        maxOptimizedDps = 111000,
        critRate = 26.5f,
        critDmg = 165.0f,
        attackSpeed = 1.48f,
        overview = "No. 039 in the Aniilog. The Gamma evolution of Dewy. Distills cursed nocturnal blossoms into intoxicating perfume fields that weaken enemy attack while multiplying allied burst.",
        faceDrawableResName = "img_face_stellarys",
        skills = listOf(
            AniimoSkill(
                id = "fra_s1",
                name = "Aroma Hex Dart",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "360% Dark Perfume",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 93000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a concentrated flask of dusk perfume.",
                dpsSynergyEffect = "Reduces target defense by 20%."
            ),
            AniimoSkill(
                id = "fra_s2",
                name = "Incense of Despair",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "610% Abyssal Fragrance",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 111000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Echoing Grimoire (+15)",
                enhancedMultiplier = "Grants +30% Hex Amp & 750% Perfume Cloud",
                enhancedDpsPerSec = 136000,
                description = "Core signature skill. Deploys an incense cloud that hexes all targets inside. Enhanced by Maxed Echoing Grimoire (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Boosts team damage by 25%."
            ),
            AniimoSkill(
                id = "fra_pass",
                name = "Intoxicating Vapors",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Party Crit Multiplier",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 30000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Party gains +20% critical damage against enemies standing in Incense of Despair.",
                dpsSynergyEffect = "Perfect for pairing with burst carries."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 38, "Hex Lightning", "Incense doubles Fulmintis slash damage.", "+30% Burst DMG", "Deploy incense then let Fulmintis burst.")
        ),
        bestItems = listOf(
            RecommendedItem("item_fra_grim", "Echoing Grimoire (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+46% ATK", "Incense of Despair lasts +5s longer.", "+26% DPS", true, "Abyssal Grove")
        ),
        statPriorities = listOf("Dark DMG%", "Crit Rate", "Hex Potency"),
        rotationGuide = listOf("1. Fire Aroma Hex Dart.", "2. Deploy Incense of Despair.", "3. Tag into Fulmintis or Ignitis.")
    ),

    // No. 040: Wisptis (Lumin, Lightning/Dark)
    Aniimo(
        id = "wisptis",
        name = "Wisptis",
        title = "Electro Stash Spark",
        element = Element.LIGHTNING,
        role = "EP Spark & Crit Setup",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 040",
        evolvesToIds = listOf("fulmintis"),
        evolutionRequirement = "Lv. 25 + Prismana Core",
        baseAtk = 880,
        baseDps = 15000,
        maxOptimizedDps = 61000,
        critRate = 26.0f,
        critDmg = 160.0f,
        attackSpeed = 1.48f,
        overview = "No. 040 in the Aniilog. The foundational Lumin pre-evolution of Fulmintis. Emits bright electro-static wisps that establish the legendary Electro Stash EP-refund mechanism upon landing critical hits.",
        faceDrawableResName = "img_face_fulmintis",
        skills = listOf(
            AniimoSkill(
                id = "wisp_s1",
                name = "Spark Jolt",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Static Zap",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 54000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Discharges a rapid lightning spark that shocks enemies.",
                dpsSynergyEffect = "Restores 2 EP on critical strike."
            ),
            AniimoSkill(
                id = "wisp_s2",
                name = "Static Discharge",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "460% Electro Burst",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 61000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Pendant (+15 Contract)",
                enhancedMultiplier = "Grants +20% Crit Rate & 580% Static Slam",
                enhancedDpsPerSec = 75000,
                description = "Core signature skill. Detonates static charges in a bright sphere. Enhanced by Maxed Pendant (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Guarantees 4 EP refund on crit."
            ),
            AniimoSkill(
                id = "wisp_pass",
                name = "Flicker Charge",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Crit Rate",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 24000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases own critical rate by 15%.",
                dpsSynergyEffect = "Directly evolves into Fulmintis's Electro Stash."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 36, "Radiant Spark", "Multiplies critical hit chance.", "+25% Crit Rate", "Tag Dazmand to max out crit rate.")
        ),
        bestItems = listOf(
            RecommendedItem("item_wisp_pend", "Pendant (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+38% ATK", "Static Discharge grants +25% crit rate.", "+24% DPS", true, "Thunder Sanctum")
        ),
        statPriorities = listOf("Crit Rate", "Lightning DMG%", "Energy Point Refund"),
        rotationGuide = listOf("1. Fire Spark Jolt for EP.", "2. Crit with Static Discharge.", "3. Tag into Fulmintis.")
    ),

    // No. 041: Ignitis (Nova, Dark/Fire)
    Aniimo(
        id = "ignitis",
        name = "Ignitis",
        title = "Abyssal Cinder Emperor",
        element = Element.DARK,
        secondaryElement = Element.FIRE,
        role = "Break & Pyro-Abyssal Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 041",
        baseAtk = 1950,
        baseDps = 46000,
        maxOptimizedDps = 176000,
        critRate = 34.0f,
        critDmg = 196.0f,
        attackSpeed = 1.68f,
        overview = "No. 041 in the Aniilog. The terrifying Dark/Fire emperor. Uses forbidden cinder flames to immolate enemy defenses, delivering staggering sub-40 second clears on endgame raid bosses.",
        faceDrawableResName = "img_face_ignitis",
        skills = listOf(
            AniimoSkill(
                id = "ign_s1",
                name = "Nether Flame Scythe",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "430% Dark/Fire Cleave",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 125000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Sweeps an abyssal scythe of black fire, cutting through 25% defense.",
                dpsSynergyEffect = "Inflicts Abyssal Flame for 8s."
            ),
            AniimoSkill(
                id = "ign_s2",
                name = "Cataclysmic Cinder Nova",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Nether Detonation",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 176000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Flame Amp & 860% Cataclysm Strike",
                enhancedDpsPerSec = 212000,
                description = "Core signature skill. Detonates all dark fire stacks in a seismic explosion. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Shatters boss poise gauges."
            ),
            AniimoSkill(
                id = "ign_pass",
                name = "Emperor of Ash",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage vs Burning",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 45000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +30% increased damage against targets with 3+ burns or curses.",
                dpsSynergyEffect = "Enables speedrun world-record clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("dazmand", "Dazmand", Element.LIGHT, "S+", 40, "Solar Dark Eclipse", "Dazmand crit field maximizes Cataclysmic Cinder Nova.", "+35% Burst DMG", "Pop Dazmand field before Cinder Nova.")
        ),
        bestItems = listOf(
            RecommendedItem("item_ign_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+55% ATK", "Cinder Nova deals 35% extra damage.", "+36% Total DPS", true, "Alpha Nether Titan Drop")
        ),
        statPriorities = listOf("Dark DMG%", "Fire DMG%", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Nether Flame Scythe to seed stacks.", "2. Cataclysmic Cinder Nova during Break.", "3. Incinerate remaining poise.")
    ),

    // No. 042: Bonesky (Lumin, Ice)
    Aniimo(
        id = "bonesky",
        name = "Bonesky",
        title = "Frost Wolf Pup",
        element = Element.ICE,
        role = "Cryo Bite & Pack Tracker",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 042",
        evolvesToIds = listOf("fenrier"),
        evolutionRequirement = "Lv. 18 Glacial Fang",
        baseAtk = 850,
        baseDps = 14300,
        maxOptimizedDps = 57000,
        critRate = 23.5f,
        critDmg = 154.0f,
        attackSpeed = 1.42f,
        overview = "No. 042 in the Aniilog. The Lumin base stage of the Glynsera wolfpack. Roams frozen tundra cliffs, delivering sharp frost bites that track fleeing targets.",
        faceDrawableResName = "img_face_glynsera",
        skills = listOf(
            AniimoSkill(
                id = "bon_s1",
                name = "Frostbite Nip",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Cryo Bite",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 51000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Quick frozen bite that chills enemy armor.",
                dpsSynergyEffect = "Slows target by 20%."
            ),
            AniimoSkill(
                id = "bon_s2",
                name = "Glacial Pounce",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Cryo Dash",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 57000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Freeze Rate & 550% Frost Pounce",
                enhancedDpsPerSec = 70000,
                description = "Core signature skill. Pounces forward leaving ice shards. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Freezes on crit."
            ),
            AniimoSkill(
                id = "bon_pass",
                name = "Pack Howl",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Team Ice DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases team Ice damage by 15% after pouncing.",
                dpsSynergyEffect = "Transfers into Fenrier."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fenrier", "Fenrier", Element.ICE, "S+", 34, "Wolf Pack", "Doubles frostbite tick rate.", "+22% Ice DMG", "Nip then tag Fenrier.")
        ),
        bestItems = listOf(
            RecommendedItem("item_bon_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Glacial Pounce freezes instantly.", "+21% DPS", true, "Tundra Den")
        ),
        statPriorities = listOf("Ice DMG%", "Crit Rate", "Freeze Chance"),
        rotationGuide = listOf("1. Frostbite Nip to slow.", "2. Glacial Pounce to freeze.", "3. Tag into Fenrier.")
    ),

    // No. 043: Fenrier (Gamma, Ice)
    Aniimo(
        id = "fenrier",
        name = "Fenrier",
        title = "Glacial Fang Alpha",
        element = Element.ICE,
        role = "Break & Cryo Bleed DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 043",
        evolvesFromId = "bonesky",
        evolvesToIds = listOf("glynsera"),
        evolutionRequirement = "Lv. 28 Ice Heart",
        baseAtk = 1360,
        baseDps = 28600,
        maxOptimizedDps = 113000,
        critRate = 27.0f,
        critDmg = 168.0f,
        attackSpeed = 1.50f,
        overview = "No. 043 in the Aniilog. The Gamma evolution of Bonesky. Commands frozen tundra blizzards with crystalline ice fangs, shredding enemy defense before evolving into Glynsera.",
        faceDrawableResName = "img_face_glynsera",
        skills = listOf(
            AniimoSkill(
                id = "fen_s1",
                name = "Blizzard Rake",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Ice Claw",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 94000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Tears with ice claws creating a freezing vortex.",
                dpsSynergyEffect = "Applies 2 Frostbite stacks."
            ),
            AniimoSkill(
                id = "fen_s2",
                name = "Savage Glacier Pounce",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "620% Cryo Impact",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 113000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Shatter & 770% Savage Strike",
                enhancedDpsPerSec = 139000,
                description = "Core signature skill. Leaps with crushing weight to shatter frozen targets. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss poise."
            ),
            AniimoSkill(
                id = "fen_pass",
                name = "Frostbite Tracker",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Damage vs Chilled",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 31000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +20% damage against enemies suffering from Frostbite.",
                dpsSynergyEffect = "Transfers into Glynsera's Lunar Howl."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("glynsera", "Glynsera", Element.ICE, "S+", 38, "Apex Predator", "Blizzard Rake seeds frost for Glynsera.", "+30% Cryo DMG", "Rake then tag Glynsera.")
        ),
        bestItems = listOf(
            RecommendedItem("item_fen_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Savage Glacier Pounce deals +30% damage.", "+28% DPS", true, "Alpha Wolf Cave")
        ),
        statPriorities = listOf("Ice DMG%", "Crit Rate", "Crit DMG", "Break Efficiency"),
        rotationGuide = listOf("1. Blizzard Rake to apply Frostbite.", "2. Savage Glacier Pounce to stagger.", "3. Tag into Glynsera.")
    ),

    // No. 044: Glynsera (Nova, Ice)
    Aniimo(
        id = "glynsera",
        name = "Glynsera",
        title = "Lunar Frost Apex",
        element = Element.ICE,
        role = "Break & Lunar Cryo Burst DPS",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 044",
        evolvesFromId = "fenrier",
        baseAtk = 1930,
        baseDps = 45500,
        maxOptimizedDps = 175000,
        critRate = 33.5f,
        critDmg = 194.0f,
        attackSpeed = 1.66f,
        overview = "No. 044 in the Aniilog. The apex Nova evolution of Bonesky and Fenrier. Howls under the midnight aurora to call down crushing lunar ice spikes that shatter boss stagger bars.",
        faceDrawableResName = "img_face_glynsera",
        skills = listOf(
            AniimoSkill(
                id = "glyn_s1",
                name = "Aurora Fang Slash",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "420% Lunar Ice Slash",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 124000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Twin slashes imbued with lunar light that shred 25% defense.",
                dpsSynergyEffect = "Freezes non-bosses instantly."
            ),
            AniimoSkill(
                id = "glyn_s2",
                name = "Lunar Blizzard Howl",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "690% Glacier Cascade",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 175000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Lunar Shatter & 860% Howl Impact",
                enhancedDpsPerSec = 211000,
                description = "Core signature skill. Howls furiously calling down giant aurora icicles. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Shatters frozen bosses for massive damage."
            ),
            AniimoSkill(
                id = "glyn_pass",
                name = "Apex Lunar Dominance",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+30% Damage vs Frozen",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 44000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Deals +30% critical hit damage against frozen or staggered targets.",
                dpsSynergyEffect = "Key to sub-45 second raid clears."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("malevsera", "Malevsera", Element.ICE, "S+", 40, "Glacial Dominance", "Dual Ice Hyper-Carry shreds frost titan shields.", "+35% Cryo DMG", "Alternate Lunar Howl and Bloodthirst.")
        ),
        bestItems = listOf(
            RecommendedItem("item_glyn_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+54% ATK", "Lunar Blizzard Howl deals 35% extra damage.", "+35% Total DPS", true, "Glacial Spire Alpha")
        ),
        statPriorities = listOf("Ice DMG%", "Crit Rate", "Crit DMG", "Break Multiplier"),
        rotationGuide = listOf("1. Aurora Fang Slash to shred defense.", "2. Lunar Blizzard Howl during Break stagger.", "3. Execute with frostbite weaves.")
    ),

    // No. 045: Bolty (Lumin, Lightning)
    Aniimo(
        id = "bolty",
        name = "Bolty",
        title = "Static Pup",
        element = Element.LIGHTNING,
        role = "Break & Static Arc Striker",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 045",
        evolvesToIds = listOf("blazen"),
        evolutionRequirement = "Lv. 22 Thunder Fang",
        baseAtk = 850,
        baseDps = 14400,
        maxOptimizedDps = 58000,
        critRate = 24.0f,
        critDmg = 155.0f,
        attackSpeed = 1.48f,
        overview = "No. 045 in the Aniilog. The energetic electric pup Lumin stage. Generates crackling static charges by rubbing its paws together, releasing electric spark arcs that stun monsters.",
        faceDrawableResName = "img_face_fulmintis",
        skills = listOf(
            AniimoSkill(
                id = "blt_s1",
                name = "Static Zap",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "290% Lightning Zap",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 52000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a crackling lightning bolt that electrocutes foes.",
                dpsSynergyEffect = "Restores 3 EP on hit."
            ),
            AniimoSkill(
                id = "blt_s2",
                name = "Volt Dash Pounce",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "450% Lightning Rush",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 58000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Capacitous Battery (+15)",
                enhancedMultiplier = "Grants +25% Stun Chance & 560% Volt Strike",
                enhancedDpsPerSec = 71000,
                description = "Core signature skill. Darts across the field as a living lightning bolt. Enhanced by Maxed Capacitous Battery (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Stuns target for 2s."
            ),
            AniimoSkill(
                id = "blt_pass",
                name = "Static Charge",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Lightning DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 23000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases own Lightning critical rate by 15%.",
                dpsSynergyEffect = "Transfers into Blazen."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("blazen", "Blazen", Element.LIGHTNING, "S+", 35, "Thunder Lineage", "Doubles chain lightning arcs.", "+25% Lightning DMG", "Zap then tag Blazen.")
        ),
        bestItems = listOf(
            RecommendedItem("item_blt_batt", "Capacitous Battery (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Volt Dash refunds 10 EP.", "+22% DPS", true, "Thunder Plains")
        ),
        statPriorities = listOf("Lightning DMG%", "Crit Rate", "Stun Chance"),
        rotationGuide = listOf("1. Static Zap to build EP.", "2. Volt Dash Pounce to stun.", "3. Tag into Blazen.")
    ),

    // No. 046: Blazen (Nova, Lightning)
    Aniimo(
        id = "blazen",
        name = "Blazen",
        title = "Stormfang Sovereign",
        element = Element.LIGHTNING,
        role = "Break & Chain Lightning Burst",
        metaTier = "S+",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 046",
        evolvesFromId = "bolty",
        baseAtk = 1900,
        baseDps = 44500,
        maxOptimizedDps = 172000,
        critRate = 33.0f,
        critDmg = 192.0f,
        attackSpeed = 1.66f,
        overview = "No. 046 in the Aniilog. The ferocious Lightning-type Nova evolution of Bolty. Unleashes 100,000-volt stormfang slashes that chain through boss body segments to trigger instant Break.",
        faceDrawableResName = "img_face_fulmintis",
        skills = listOf(
            AniimoSkill(
                id = "bla_s1",
                name = "Thunder Arc Cleave",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "410% Lightning Cleave",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 120000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Sweeps an electrified lightning blade that arcs to 4 targets.",
                dpsSynergyEffect = "Shreds 20% lightning resistance."
            ),
            AniimoSkill(
                id = "bla_s2",
                name = "Volt Storm Cataclysm",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "670% Hyper-Voltage",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 172000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Capacitous Battery (+15 Contract)",
                enhancedMultiplier = "Grants +30% Static Arc & 830% Thunder Strike",
                enhancedDpsPerSec = 206000,
                description = "Core signature skill. Drops a colossal thunderbolt directly onto the target. Enhanced by Maxed Capacitous Battery (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks boss stagger bars."
            ),
            AniimoSkill(
                id = "bla_pass",
                name = "Galvanic Surge",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Critical Damage",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 42000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases party critical hit damage by 25% while boss is electrified.",
                dpsSynergyEffect = "Multiplies total party DPS."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("fulmintis", "Fulmintis", Element.LIGHTNING, "S+", 40, "Overload Twins", "Dual lightning creates continuous chain reaction.", "+35% Lightning Burst", "Chain arcs with Blazen then slash with Fulmintis.")
        ),
        bestItems = listOf(
            RecommendedItem("item_bla_batt", "Capacitous Battery (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Volt Storm Cataclysm refunds 15 EP.", "+34% Total DPS", true, "Alpha Storm Summit")
        ),
        statPriorities = listOf("Lightning DMG%", "Crit Rate", "Crit DMG", "Break Multiplier"),
        rotationGuide = listOf("1. Thunder Arc Cleave to seed static.", "2. Volt Storm Cataclysm on Break.", "3. Chain lightning weaves.")
    ),

    // No. 047: Squarrel (Lumin, Fire)
    Aniimo(
        id = "squarrel",
        name = "Squarrel",
        title = "Flaming Acorn Scamp",
        element = Element.FIRE,
        role = "Break & Pyro Acorn Bomb",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 047",
        evolvesToIds = listOf("squashel"),
        evolutionRequirement = "Lv. 21 Molten Nut",
        baseAtk = 840,
        baseDps = 14200,
        maxOptimizedDps = 56000,
        critRate = 22.5f,
        critDmg = 150.0f,
        attackSpeed = 1.45f,
        overview = "No. 047 in the Aniilog. The fiery squirrel Lumin stage. Gathers volcanic embers inside glowing acorn shells, hurling them like grenades to knock down approaching foes.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "sq_s1",
                name = "Ember Acorn Toss",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Fire Shrapnel",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 50000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Hurls an exploding flaming acorn that scatters burning cinders.",
                dpsSynergyEffect = "Burns enemies for 4s."
            ),
            AniimoSkill(
                id = "sq_s2",
                name = "Blazing Tail Slam",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Fire Tail",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 56000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Burn Damage & 550% Tail Strike",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Whips a fiery bushy tail to detonate all acorns. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Causes stagger."
            ),
            AniimoSkill(
                id = "sq_pass",
                name = "Nut Hoarder",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Bomb DMG",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Increases damage of thrown projectile attacks by 15%.",
                dpsSynergyEffect = "Transfers into Squashel."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("squashel", "Squashel", Element.FIRE, "S+", 34, "Acorn Blaze", "Doubles acorn explosion radius.", "+22% Fire DMG", "Toss acorn then tag Squashel.")
        ),
        bestItems = listOf(
            RecommendedItem("item_sq_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Blazing Tail Slam deals +25% Break.", "+21% DPS", true, "Volcanic Forest")
        ),
        statPriorities = listOf("Fire DMG%", "Bomb Damage", "Crit Rate"),
        rotationGuide = listOf("1. Toss Ember Acorns.", "2. Detonate with Blazing Tail Slam.", "3. Tag into Squashel.")
    ),

    // No. 048: Squashel (Nova, Fire)
    Aniimo(
        id = "squashel",
        name = "Squashel",
        title = "Inferno Nut Bombardier",
        element = Element.FIRE,
        role = "Break & Pyro Cluster Bomb DPS",
        metaTier = "S",
        stage = EvolutionStage.NOVA,
        aniilogNumber = "No. 048",
        evolvesFromId = "squarrel",
        baseAtk = 1870,
        baseDps = 42800,
        maxOptimizedDps = 166000,
        critRate = 31.5f,
        critDmg = 186.0f,
        attackSpeed = 1.62f,
        overview = "No. 048 in the Aniilog. The heavy-artillery Nova evolution of Squarrel. Launches cluster magma bombs that create localized volcanic eruptions to shatter boss super-armor.",
        faceDrawableResName = "img_face_scorchhowl",
        skills = listOf(
            AniimoSkill(
                id = "sqh_s1",
                name = "Magma Acorn Barrage",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "400% Cluster Pyro",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 117000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Fires a cluster of 6 molten acorns that carpet bomb the arena.",
                dpsSynergyEffect = "Shreds 25% fire defense."
            ),
            AniimoSkill(
                id = "sqh_s2",
                name = "Volcanic Tail Cataclysm",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "660% Lava Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 166000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +30% Break Stagger & 820% Cataclysm Strike",
                enhancedDpsPerSec = 198000,
                description = "Core signature skill. Slams the ground triggering a massive lava fountain. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Chunks 40% of boss poise."
            ),
            AniimoSkill(
                id = "sqh_pass",
                name = "Combustion Expert",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+25% Fire Shrapnel",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 41000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Explosive attacks deal +25% increased damage to staggered bosses.",
                dpsSynergyEffect = "Enables massive burst windows."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("scorchhowl", "Scorchhowl", Element.FIRE, "S+", 38, "Dual Pyro Burst", "Combines cluster bombs with Scorchhowl magma howls.", "+32% Fire DMG", "Bombard then let Scorchhowl howl.")
        ),
        bestItems = listOf(
            RecommendedItem("item_sqh_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+52% ATK", "Volcanic Tail Cataclysm deals +35% damage.", "+33% Total DPS", true, "Alpha Caldera Drop")
        ),
        statPriorities = listOf("Fire DMG%", "Break Efficiency", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Magma Acorn Barrage to carpet bomb.", "2. Volcanic Tail Cataclysm on Break.", "3. Tag into burst DPS.")
    ),

    // No. 049: Susuta (Lumin, Water)
    Aniimo(
        id = "susuta",
        name = "Susuta",
        title = "Water Hippo Calf",
        element = Element.WATER,
        role = "Break & Aquatic Ram",
        metaTier = "A",
        stage = EvolutionStage.LUMIN,
        aniilogNumber = "No. 049",
        evolvesToIds = listOf("popota"),
        evolutionRequirement = "Lv. 20 River Stone",
        baseAtk = 860,
        baseDps = 14500,
        maxOptimizedDps = 57000,
        critRate = 22.0f,
        critDmg = 148.0f,
        attackSpeed = 1.36f,
        overview = "No. 049 in the Aniilog. The hefty water hippo Lumin stage. Wades through shallow riverbeds, using its buoyant armored snout to ram into enemies with high hydraulic force.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "sus_s1",
                name = "Snout Bump",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "280% Water Blunt",
                cooldownSec = 3,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 51000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Charges forward bumping foes with an armored snout.",
                dpsSynergyEffect = "Knocks back targets."
            ),
            AniimoSkill(
                id = "sus_s2",
                name = "Riverbed Belly Flop",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "440% Hydraulic Slam",
                cooldownSec = 7,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 57000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15)",
                enhancedMultiplier = "Grants +25% Poise Damage & 550% Flop Strike",
                enhancedDpsPerSec = 69000,
                description = "Core signature skill. Launches into the air and crashes down with a massive water splash. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Soaks all targets."
            ),
            AniimoSkill(
                id = "sus_pass",
                name = "Thick Blubber",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+15% Max HP",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 22000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Passively increases party water resistance and max HP by 15%.",
                dpsSynergyEffect = "Transfers into Popota."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("popota", "Popota", Element.WATER, "S+", 34, "River Herd", "Doubles soak duration.", "+22% Water DMG", "Flop then tag Popota.")
        ),
        bestItems = listOf(
            RecommendedItem("item_sus_fang", "Ferocious Fang (+15)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+36% ATK", "Belly Flop soaks 30% wider.", "+21% DPS", true, "River Basin")
        ),
        statPriorities = listOf("Break Efficiency", "Water DMG%", "Max HP"),
        rotationGuide = listOf("1. Bump with Snout Bump.", "2. Soak with Riverbed Belly Flop.", "3. Tag into Popota.")
    ),

    // No. 050: Popota (Gamma, Water)
    Aniimo(
        id = "popota",
        name = "Popota",
        title = "Tidal Hippo Charger",
        element = Element.WATER,
        role = "Break & Water Surge DPS",
        metaTier = "S",
        stage = EvolutionStage.GAMMA,
        aniilogNumber = "No. 050",
        evolvesFromId = "susuta",
        evolvesToIds = listOf("piopiota"),
        evolutionRequirement = "Lv. 39 Wave Stone",
        baseAtk = 1350,
        baseDps = 28200,
        maxOptimizedDps = 110000,
        critRate = 26.0f,
        critDmg = 164.0f,
        attackSpeed = 1.46f,
        overview = "No. 050 in the Aniilog. The Gamma evolution of Susuta. Surges through rivers and ocean shorelines, creating pressurized water walls that deflect projectiles while ramming boss poise meters.",
        faceDrawableResName = "img_face_sherro",
        skills = listOf(
            AniimoSkill(
                id = "pop_s1",
                name = "Pressurized Charge",
                type = SkillType.BASE_SKILL_1,
                damageMultiplier = "370% Water Impact",
                cooldownSec = 4,
                dpsPotentialTier = "A-Tier",
                dpsPerSec = 93000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Charges forward cloaked in a high-pressure hydro barrier.",
                dpsSynergyEffect = "Shreds 20% target defense."
            ),
            AniimoSkill(
                id = "pop_s2",
                name = "Tidal Geyser Breach",
                type = SkillType.CORE_SKILL,
                damageMultiplier = "610% Hydro Eruption",
                cooldownSec = 8,
                dpsPotentialTier = "S-Tier",
                dpsPerSec = 110000,
                isHighestDps = true,
                isCoreSkill = true,
                enhancedByMaxedItem = "Maxed Ferocious Fang (+15 Contract)",
                enhancedMultiplier = "Grants +25% Break Stagger & 760% Breach Slam",
                enhancedDpsPerSec = 137000,
                description = "Core signature skill. Slams the ground summoning geysers under enemies. Enhanced by Maxed Ferocious Fang (+15).",
                dpsSynergyEffect = "Highest DPS potential in kit. Staggers and soaks."
            ),
            AniimoSkill(
                id = "pop_pass",
                name = "Hydro Deflector",
                type = SkillType.CORE_PASSIVE,
                damageMultiplier = "+20% Damage Reduction",
                cooldownSec = 0,
                dpsPotentialTier = "Support",
                dpsPerSec = 30000,
                isHighestDps = false,
                isCoreSkill = false,
                description = "Absorbs 20% incoming damage while standing in water puddles.",
                dpsSynergyEffect = "Transfers into Piopiota."
            )
        ),
        bestPartners = listOf(
            PartnerSynergy("piopiota", "Piopiota", Element.WATER, "S+", 38, "Tidal Apex", "Full hydraulic inheritance.", "+30% Water DMG", "Geyser then tag Piopiota.")
        ),
        bestItems = listOf(
            RecommendedItem("item_pop_fang", "Ferocious Fang (+15 Contract)", ItemSlot.CONTRACT_HELD_ITEM, "Legendary", "+48% ATK", "Tidal Geyser Breach deals +30% damage.", "+27% DPS", true, "River Alpha")
        ),
        statPriorities = listOf("Water DMG%", "Break Efficiency", "Crit Rate", "Crit DMG"),
        rotationGuide = listOf("1. Pressurized Charge to shred defense.", "2. Tidal Geyser Breach to stagger.", "3. Tag into Piopiota.")
    )
)
