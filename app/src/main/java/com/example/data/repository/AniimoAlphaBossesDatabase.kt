package com.example.data.repository

import com.example.data.model.BossEncounter
import com.example.data.model.Element

object AniimoAlphaBossesDatabase {

    val allAlphaBosses: List<BossEncounter> = listOf(
        BossEncounter(
            id = "alpha_turbo",
            name = "Alpha Turbo",
            zone = "Nimbus Fields [Tutorial / Breezy Plains]",
            elementWeakness = Element.FIRE,
            difficulty = "Field Alpha (Lv 12)",
            recommendedDps = 35000,
            parClearTime = "00:28.00",
            description = "High-speed roaming gale rabbit miniboss. Agile dashes and whirlwind jumps; easily melted by Fire skills."
        ),
        BossEncounter(
            id = "alpha_stellarys",
            name = "Alpha Stellarys",
            zone = "Forest of Falling Stars [Breezy Plains]",
            elementWeakness = Element.LIGHT,
            difficulty = "Mythic Alpha (Lv 55)",
            recommendedDps = 165000,
            parClearTime = "00:36.50",
            description = "Majestic dark astral deity roaming the celestial woods. Summons meteor showers; weak to Light blade strikes."
        ),
        BossEncounter(
            id = "alpha_scorchhowl",
            name = "Alpha Scorchhowl",
            zone = "Beast Fang Ridge [Sanctum: Frigid Waters]",
            elementWeakness = Element.WATER,
            difficulty = "Nightmare Alpha (Lv 48)",
            recommendedDps = 140000,
            parClearTime = "00:42.00",
            description = "Fierce alpha inferno wolf surrounded by blazing embers. Vulnerable to Water bursts and frost staggering."
        ),
        BossEncounter(
            id = "alpha_whisperwake_kraken",
            name = "Alpha Whisperwake Kraken",
            zone = "Whisperwake Isles Deep [Alpha Tier 10]",
            elementWeakness = Element.LIGHTNING,
            difficulty = "Mythic Alpha (Lv 60)",
            recommendedDps = 175000,
            parClearTime = "00:45.00",
            description = "Colossal abyssal kraken emerging from the ocean depths. Dominates the water; shredded by Lightning rotations."
        ),
        BossEncounter(
            id = "alpha_glynsera",
            name = "Alpha Glynsera",
            zone = "Beast Fang Ridge [Breezy Plains]",
            elementWeakness = Element.EARTH,
            difficulty = "Elite Alpha (Lv 42)",
            recommendedDps = 110000,
            parClearTime = "00:50.00",
            description = "Serpentine shadow predator with high dodge evasiveness. Earth Break attacks crack its venomous shield."
        ),
        BossEncounter(
            id = "alpha_irisal",
            name = "Alpha Irisal",
            zone = "Sea of Flowers [Breezy Plains]",
            elementWeakness = Element.FIRE,
            difficulty = "Field Alpha (Lv 35)",
            recommendedDps = 95000,
            parClearTime = "00:44.00",
            description = "Giant blooming floral fairy that casts pollen confusion and clone mirages. Drops Irisalis Petals."
        ),
        BossEncounter(
            id = "alpha_leafy",
            name = "Alpha Leafy",
            zone = "The Mistwoods [Breezy Plains]",
            elementWeakness = Element.FIRE,
            difficulty = "Field Alpha (Lv 22)",
            recommendedDps = 55000,
            parClearTime = "00:32.00",
            description = "Thick foliage beast that burrows underground to heal. High Break vulnerability when emerging."
        ),
        BossEncounter(
            id = "alpha_tubster",
            name = "Alpha Tubster",
            zone = "Rolling Hills [Breezy Plains]",
            elementWeakness = Element.LIGHTNING,
            difficulty = "Field Alpha (Lv 28)",
            recommendedDps = 70000,
            parClearTime = "00:38.00",
            description = "Bulky mud-rolling titan with hefty physical armor. Slow windups leave huge openings for burst."
        ),
        BossEncounter(
            id = "alpha_bouldus",
            name = "Alpha Bouldus",
            zone = "Russet Highlands [Breezy Plains]",
            elementWeakness = Element.WIND,
            difficulty = "Heavy Alpha (Lv 45)",
            recommendedDps = 125000,
            parClearTime = "00:48.00",
            description = "Massive armored rock tortoise with granite armor plating. Wind erosion skills break its shell."
        ),
        BossEncounter(
            id = "alpha_blazen",
            name = "Alpha Blazen",
            zone = "Blitzwood [Breezy Plains]",
            elementWeakness = Element.WATER,
            difficulty = "Elite Alpha (Lv 38)",
            recommendedDps = 105000,
            parClearTime = "00:39.00",
            description = "Electrified fire hare that zips across the battlefield at extreme speed. Water pools ground its charges."
        ),
        BossEncounter(
            id = "alpha_panpanta",
            name = "Alpha Panpanta",
            zone = "Bamboo Groves [Breezy Plains]",
            elementWeakness = Element.DARK,
            difficulty = "Field Alpha (Lv 30)",
            recommendedDps = 80000,
            parClearTime = "00:41.00",
            description = "Playful yet devastating martial arts bear. Staggers players with heavy bamboo strikes."
        ),
        BossEncounter(
            id = "alpha_ignitis",
            name = "Alpha Ignitis",
            zone = "Volcanic Foothills [Breezy Plains]",
            elementWeakness = Element.WATER,
            difficulty = "Elite Alpha (Lv 46)",
            recommendedDps = 130000,
            parClearTime = "00:45.00",
            description = "Fiery apex dragonet that blankets the area in magma pools. High fire resist, melted by water torrents."
        ),
        BossEncounter(
            id = "alpha_grizbo",
            name = "Alpha Grizbo",
            zone = "Highland Caverns [Breezy Plains]",
            elementWeakness = Element.WIND,
            difficulty = "Nightmare Alpha (Lv 52)",
            recommendedDps = 150000,
            parClearTime = "00:41.00",
            description = "Brutal territorial bear alpha wielding crushing earth slams. Drops Ferocious Fang upgrade materials."
        ),
        BossEncounter(
            id = "alpha_magmarex",
            name = "Alpha Magmarex",
            zone = "Volcanic Caldera [Breezy Plains]",
            elementWeakness = Element.WATER,
            difficulty = "Mythic Alpha (Lv 58)",
            recommendedDps = 168000,
            parClearTime = "00:39.00",
            description = "Ancient caldera rex that commands lava vents. Devastating explosive shockwaves."
        ),
        BossEncounter(
            id = "alpha_luminelle",
            name = "Alpha Luminelle",
            zone = "Ancient Sanctuary [Highland Summit]",
            elementWeakness = Element.DARK,
            difficulty = "Mythic Boss (Lv 60)",
            recommendedDps = 180000,
            parClearTime = "00:34.00",
            description = "Radiant light sovereign hovering above the sanctuary. High evasion and laser beams; weak to Umbral dark skills."
        ),
        BossEncounter(
            id = "alpha_geoclaw",
            name = "Alpha Geoclaw",
            zone = "Rocky Shorelines [Whisperwake / Breezy Border]",
            elementWeakness = Element.LIGHTNING,
            difficulty = "Secret Alpha (Lv 15)",
            recommendedDps = 45000,
            parClearTime = "00:30.00",
            description = "Hidden tide crab alpha tucked away in beach caverns. Drops Giant Tortoise Shell components."
        ),
        BossEncounter(
            id = "alpha_minespine",
            name = "Alpha Minespine",
            zone = "Subterranean Gear Works [Deep Mines]",
            elementWeakness = Element.EARTH,
            difficulty = "Secret Alpha (Lv 56)",
            recommendedDps = 160000,
            parClearTime = "00:38.00",
            description = "Mechanical quill beast guarding deep mineral deposits. Drops Fission Needles and Avenging Gear."
        )
    )
}
