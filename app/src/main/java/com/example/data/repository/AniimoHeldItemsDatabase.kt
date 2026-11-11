package com.example.data.repository

import com.example.data.model.ItemSlot
import com.example.data.model.RecommendedItem

object AniimoHeldItemsDatabase {

    val allHeldItems: List<RecommendedItem> = listOf(
        RecommendedItem(
            id = "ferocious_fang",
            name = "Ferocious Fang",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+28% Physical Attack & +15% Crit Rate",
            coreEffect = "Consecutive basic or skill attacks build Bloodthirst stacks; at 5 stacks unleash a 45% bonus physical rupture.",
            dpsContribution = "+38% DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Grizbo & Beast Fang Ridge drop"
        ),
        RecommendedItem(
            id = "heartseeker_pendant",
            name = "Heartseeker Pendant",
            slot = ItemSlot.CONTRACT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+32% Skill Multiplier & +20% Critical Damage",
            coreEffect = "Increases damage against enemies above 70% HP by 30% and guarantees critical strike on first cast.",
            dpsContribution = "+42% Burst DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Nether Colossus & Starlet Paw Shop"
        ),
        RecommendedItem(
            id = "capacitous_battery",
            name = "Capacitous Battery",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+35% Energy Regen & +20% Lightning Dmg",
            coreEffect = "Reduces skill cooldowns by 18% and shares 25% of energy generated with the active partner Aniimo.",
            dpsContribution = "+30% Sustained DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Thunder Sovereign & Storm Spire"
        ),
        RecommendedItem(
            id = "fission_needles",
            name = "Fission Needles",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+30% Armor Break Power & +18% Attack Speed",
            coreEffect = "Strikes against staggered bosses splinter into 3 piercing needles dealing 35% damage each.",
            dpsContribution = "+35% Boss Stagger DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Minespine & Subterranean Quarry"
        ),
        RecommendedItem(
            id = "spirited_feather",
            name = "Spirited Feather",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+22% Movement Speed & +15% Wind Damage",
            coreEffect = "Dodging or sprinting triggers a Gale buff increasing next skill damage by 25%.",
            dpsContribution = "+22% Mobility DPS",
            isBestInSlot = false,
            sourceLocation = "Alpha Turbo & Nimbus Fields"
        ),
        RecommendedItem(
            id = "gargantuan_horn",
            name = "Gargantuan Horn",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+34% Heavy Attack Power & +12% Max HP",
            coreEffect = "Heavy and plunge attacks deal 50% increased damage and trigger a seismic shockwave.",
            dpsContribution = "+32% Heavy DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Tubster & Rolling Hills"
        ),
        RecommendedItem(
            id = "destinys_dice",
            name = "Destiny's Dice",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+25% Crit Rate & +25% Lucky Hit Chance",
            coreEffect = "Rolls a 1-6 modifier on skill hits; rolls of 4+ multiply skill damage by up to 60%.",
            dpsContribution = "+36% High-Roll DPS",
            isBestInSlot = true,
            sourceLocation = "Egg Heist Secret Chests"
        ),
        RecommendedItem(
            id = "avenging_gear",
            name = "Avenging Gear",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+28% Physical DEF & +20% Counter Attack Dmg",
            coreEffect = "Taking damage builds charge; after 3 hits releases a steam burst counter dealing 80% ATK damage.",
            dpsContribution = "+26% Tank Counter DPS",
            isBestInSlot = false,
            sourceLocation = "Alpha Minespine & Deep Mineral Quarry"
        ),
        RecommendedItem(
            id = "bailite_crest",
            name = "Bailite Crest",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+25% Earth & +25% Dark Synergy Damage",
            coreEffect = "Increases team resonance buffs by 15% and grants 35% stagger damage against shielded alphas.",
            dpsContribution = "+28% Synergy DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Bouldus & Russet Highlands"
        ),
        RecommendedItem(
            id = "supernova_shard",
            name = "Supernova Shard",
            slot = ItemSlot.CONTRACT_HELD_ITEM,
            rarity = "Mythic",
            mainStat = "+35% Elemental Burst Multiplier & +18% Crit Rate",
            coreEffect = "Ultimate skills generate a Prismana Nova vortex that pulls foes and causes 55% elemental explosion.",
            dpsContribution = "+45% Endgame Burst DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Stellarys & Celestial Woods"
        ),
        RecommendedItem(
            id = "chrono_pocketwatch",
            name = "Chrono Pocketwatch",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+25% Cooldown Reduction & +10% Attack Speed",
            coreEffect = "Reduces team swap cooldown by 3 seconds and boosts partner entrance burst by 20%.",
            dpsContribution = "+24% Rotation Flow",
            isBestInSlot = false,
            sourceLocation = "Academy Relic Vault"
        ),
        RecommendedItem(
            id = "tidal_pearl",
            name = "Tidal Pearl",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+30% Water Damage & +18% Energy Regen",
            coreEffect = "Water skills apply Drenched status, causing subsequent lightning and frost hits to critically trigger.",
            dpsContribution = "+34% Elemental Combo DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Whisperwake Kraken"
        ),
        RecommendedItem(
            id = "igneous_core",
            name = "Igneous Core",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+32% Fire Damage & +15% Burn Duration",
            coreEffect = "Ignites targets for 8 seconds, dealing 12% continuous fire damage every second.",
            dpsContribution = "+33% DoT Fire DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Magmarex & Volcanic Caldera"
        ),
        RecommendedItem(
            id = "obsidian_mirror",
            name = "Obsidian Mirror",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+24% Dark Resistance & +20% Dark Skill Dmg",
            coreEffect = "Reflects 15% of incoming status damage and cloaks the bearer in Umbral shroud after sprinting.",
            dpsContribution = "+22% Dark Shroud DPS",
            isBestInSlot = false,
            sourceLocation = "The Mistwoods Secret Altar"
        ),
        RecommendedItem(
            id = "giant_tortoise_shell",
            name = "Giant Tortoise Shell",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+35% Shield Absorption & +15% Max HP",
            coreEffect = "Grants an elemental barrier absorbing damage equal to 25% of bearer's HP every 20 seconds.",
            dpsContribution = "+20% Defensive Uptime",
            isBestInSlot = false,
            sourceLocation = "Alpha Geoclaw & Rocky Shorelines"
        ),
        RecommendedItem(
            id = "whispering_conch",
            name = "Whispering Conch",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+25% Healing Output & +18% Energy Regen",
            coreEffect = "Heals transmitted to team members generate 10 energy points per trigger.",
            dpsContribution = "+18% Sustain DPS",
            isBestInSlot = false,
            sourceLocation = "Whisperwake Isles Coast"
        ),
        RecommendedItem(
            id = "umbral_shroud",
            name = "Umbral Shroud",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+28% Dark Damage & +20% Backstab Crit Dmg",
            coreEffect = "Striking enemies from behind or in shadow deals 40% increased critical damage.",
            dpsContribution = "+35% Assassin DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Glynsera & Beast Fang Ridge"
        ),
        RecommendedItem(
            id = "prismana_prism",
            name = "Prismana Prism",
            slot = ItemSlot.CONTRACT_HELD_ITEM,
            rarity = "Mythic",
            mainStat = "+30% All Elemental Damage & +15% Elemental Penetration",
            coreEffect = "Hitting with two different elements within 4 seconds activates Rainbow Resonance for +35% damage.",
            dpsContribution = "+40% Multi-Element DPS",
            isBestInSlot = true,
            sourceLocation = "Sanctum of the Prismana Core"
        ),
        RecommendedItem(
            id = "gale_whistle",
            name = "Gale Whistle",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Rare",
            mainStat = "+15% Wind Damage & +12% Attack Speed",
            coreEffect = "Basic combo finishes summon a small whirlwind dealing 20% wind damage.",
            dpsContribution = "+16% Early Game DPS",
            isBestInSlot = false,
            sourceLocation = "Nimbus Fields Merchant"
        ),
        RecommendedItem(
            id = "phoenix_feather",
            name = "Phoenix Feather",
            slot = ItemSlot.CONTRACT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+25% Fire Damage & +1 Revive per Encounter",
            coreEffect = "Upon taking fatal damage, revives with 35% HP and unleashes a massive fire shockwave.",
            dpsContribution = "+25% Second Chance DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Ignitis & Volcanic Foothills"
        ),
        RecommendedItem(
            id = "sparkle_locket",
            name = "Sparkle Locket",
            slot = ItemSlot.SECONDARY_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+18% Crit Rate & +25% Sparkle Aura Radiance",
            coreEffect = "Boosts sparkling aura particle intensity and increases loot drop chances by 15%.",
            dpsContribution = "+20% Loot & Crit DPS",
            isBestInSlot = false,
            sourceLocation = "Starlet Paw Collector Quest"
        ),
        RecommendedItem(
            id = "thorns_carapace",
            name = "Thorns Carapace",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+22% Armor & +18% Physical Reflected Damage",
            coreEffect = "Direct physical hits from enemies reflect 25% damage back as piercing thorns.",
            dpsContribution = "+18% Retaliation DPS",
            isBestInSlot = false,
            sourceLocation = "Alpha Leafy & The Mistwoods"
        ),
        RecommendedItem(
            id = "berserker_mask",
            name = "Berserker Mask",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+35% ATK & -15% Defense",
            coreEffect = "When HP drops below 50%, ATK increases by an additional 30% and basic attacks cause bleed.",
            dpsContribution = "+38% Risk-Reward DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Panpanta & Bamboo Groves"
        ),
        RecommendedItem(
            id = "alchemical_flask",
            name = "Alchemical Flask",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Rare",
            mainStat = "+20% Potion Potency & +15% Status Duration",
            coreEffect = "Increases all deployed field buff durations by 30%.",
            dpsContribution = "+15% Utility Boost",
            isBestInSlot = false,
            sourceLocation = "Breezy Plains Alchemist"
        ),
        RecommendedItem(
            id = "glacial_fang",
            name = "Glacial Fang",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+28% Frost Damage & +18% Stagger Multiplier",
            coreEffect = "Freezes staggered enemies for 2 seconds, amplifying partner burst window by 25%.",
            dpsContribution = "+32% Stagger Burst DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Scorchhowl & Frigid Waters"
        ),
        RecommendedItem(
            id = "celestial_bell",
            name = "Celestial Bell",
            slot = ItemSlot.SUPPORT_HELD_ITEM,
            rarity = "Legendary",
            mainStat = "+30% Light Damage & +20% Party Energy Share",
            coreEffect = "Ringing bell waves purge negative status ailments from allies and boost team Light DPS by 25%.",
            dpsContribution = "+30% Holy Team DPS",
            isBestInSlot = true,
            sourceLocation = "Alpha Luminelle & Highland Summit"
        ),
        RecommendedItem(
            id = "thunder_spark_plug",
            name = "Thunder Spark Plug",
            slot = ItemSlot.PRIMARY_HELD_ITEM,
            rarity = "Epic",
            mainStat = "+24% Lightning Damage & +15% Attack Speed",
            coreEffect = "Every 4th attack discharges an electric jolt chaining to 2 nearby enemies.",
            dpsContribution = "+25% AoE Lightning DPS",
            isBestInSlot = false,
            sourceLocation = "Alpha Blazen & Blitzwood"
        )
    )
}
