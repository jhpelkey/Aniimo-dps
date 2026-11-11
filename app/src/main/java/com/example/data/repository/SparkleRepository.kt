package com.example.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import com.example.data.model.Aniimo
import com.example.data.model.Element
import com.example.data.model.EvolutionStage
import com.example.data.model.FormCategory
import com.example.data.model.GameRegion
import com.example.data.model.RegionalVariant
import com.example.data.model.SparkleType
import com.example.data.model.SpeciesFormAvailability
import com.example.data.model.SpeciesSparkleLook

/**
 * Extension property returning ONLY the authentic in-game Forms & Variants that this specific
 * Aniimo species actually possesses (e.g., Standard, Highland Form, Mountain Woods Form,
 * Snowfield Form, Sea of Flowers Form, Prismana Form if eligible, and Umbral Catch Roll if eligible).
 */
val Aniimo.regionalVariants: List<RegionalVariant>
    get() = SparkleRepository.getRegionalVariantsForAniimo(this)

/**
 * Extension property exposing the species' exact Form & Rarity Axis eligibility in-game.
 */
val Aniimo.formAvailability: SpeciesFormAvailability
    get() = SparkleRepository.getFormAvailability(this)

object SparkleRepository {

    /**
     * All 13 Official In-Game Sparkling Variations from Aniimo (aniimotools.dev Exhibition Wall):
     * - Sparkling Prototype (natural creature colors + glittering Sparkling particle coat)
     * - Numbered Sparkling Types I through X (99% combined pool from Sparkling Cubes / Sigils)
     * - Dazzling Sparkling (1% from Sparkling Cube [Normal Egg] / 0.5% from Sparkling Sigil & Pigment)
     * - Shadow Sparkling (1% from Sparkling Cube [Egg Heist Egg] / 0.5% from Sparkling Sigil & Pigment)
     */
    val allSparkleTypes: List<SparkleType> = listOf(
        SparkleType(
            id = "prototype",
            code = "Prototype",
            name = "Sparkling Prototype (Natural Coat)",
            rarity = "Base Sparkle",
            odds = "Wild 1/20,000 • Prototype",
            description = "The baseline in-game Sparkling Prototype! Preserves 100% of the Aniimo's authentic natural coat palette while infusing its model with the glittering in-game Sparkling star-mote & prism-glint particle coat.",
            coatEffect = "100% Natural In-Game Coat Colors + Sparkling Glint",
            auraEffect = "In-Game 4-Point Starlight Motes & Summon Ring",
            primaryColor = Color(0xFF38BDF8),
            secondaryColor = Color(0xFFFACC15),
            accentColor = Color(0xFFFFFFFF),
            hueShiftDegrees = 0f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • PROTOTYPE",
            cubeNormalOdds = "Base Wild / Nurture Roll",
            cubeEggHeistOdds = "Base Wild / Heist Roll",
            sigilPigmentOdds = "Restores Default Sparkle Coat"
        ),
        SparkleType(
            id = "type_1",
            code = "Type I",
            name = "Exhibition Type I (Amber-Gold Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type I. Shifts the Aniimo's primary fur, scales, or carapace toward warm amber-gold while keeping natural eye contrast and dark outlines intact.",
            coatEffect = "Warm Amber-Gold Primary Coat Zone Shift",
            auraEffect = "Golden Starlight Motes & Amber Summon Glint",
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFF59E0B),
            accentColor = Color(0xFFFEF08A),
            hueShiftDegrees = 26f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE I",
            cubeNormalOdds = "9.9% (99% Type I–X Pool)",
            cubeEggHeistOdds = "9.9% (99% Type I–X Pool)",
            sigilPigmentOdds = "9.9% (99% Type I–X Pool)"
        ),
        SparkleType(
            id = "type_2",
            code = "Type II",
            name = "Exhibition Type II (Crimson-Coral Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type II. Shifts the creature's secondary plates, crest, and coat midtones into vibrant crimson-coral with warm ruby sparkle motes.",
            coatEffect = "Crimson-Coral Carapace & Mane Zone Shift",
            auraEffect = "Ruby Star-Motes & Coral Shimmer",
            primaryColor = Color(0xFFEF4444),
            secondaryColor = Color(0xFFDC2626),
            accentColor = Color(0xFFFCA5A5),
            hueShiftDegrees = 336f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE II"
        ),
        SparkleType(
            id = "type_3",
            code = "Type III",
            name = "Exhibition Type III (Cobalt-Azure Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type III. Shifts the Aniimo's coat into cool cobalt-azure and sky-blue tones—iconic on Fire and Lightning species like Scorchhowl and Ignitis.",
            coatEffect = "Cobalt-Azure Coat & Sky-Cyan Trim Shift",
            auraEffect = "Azure Diamond Motes & Cool Prism Glint",
            primaryColor = Color(0xFF38BDF8),
            secondaryColor = Color(0xFF0284C7),
            accentColor = Color(0xFFBAE6FD),
            hueShiftDegrees = 192f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE III"
        ),
        SparkleType(
            id = "type_4",
            code = "Type IV",
            name = "Exhibition Type IV (Jade-Mint Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type IV. Shifts the creature's primary coat to verdant jade-mint with luminous emerald accent markings.",
            coatEffect = "Jade-Mint Fur & Emerald Scale Shift",
            auraEffect = "Verdant Emerald Star-Motes",
            primaryColor = Color(0xFF10B981),
            secondaryColor = Color(0xFF059669),
            accentColor = Color(0xFFA7F3D0),
            hueShiftDegrees = 122f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE IV"
        ),
        SparkleType(
            id = "type_5",
            code = "Type V",
            name = "Exhibition Type V (Chartreuse-Lime Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type V. Gives the Aniimo a crisp chartreuse-lime and volt-yellow coat palette with bright ion-green sparkle particles.",
            coatEffect = "Chartreuse-Lime & Volt-Yellow Coat Shift",
            auraEffect = "Bright Chartreuse Starlight Motes",
            primaryColor = Color(0xFFA3E635),
            secondaryColor = Color(0xFF38BDF8),
            accentColor = Color(0xFFECFCCB),
            hueShiftDegrees = 70f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE V"
        ),
        SparkleType(
            id = "type_6",
            code = "Type VI",
            name = "Exhibition Type VI (Sakura-Rose Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type VI. Shifts the creature's foliage, fur, or fins into pastel sakura-rose and coral-pink—signature look for Leafy, Budclaw, and Dewlop.",
            coatEffect = "Sakura-Rose & Soft Pink Accent Shift",
            auraEffect = "Rose-Quartz Star-Motes & Petal Glint",
            primaryColor = Color(0xFFF472B6),
            secondaryColor = Color(0xFFEC4899),
            accentColor = Color(0xFFFBCFE8),
            hueShiftDegrees = 304f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE VI"
        ),
        SparkleType(
            id = "type_7",
            code = "Type VII",
            name = "Exhibition Type VII (Amethyst-Violet Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type VII. Shifts the Aniimo's primary body to royal amethyst-violet with gold-tinted trim—the iconic in-game Sparkling palette for Fulmintis and Wisptis.",
            coatEffect = "Royal Amethyst-Violet & Gold Trim Shift",
            auraEffect = "Violet Star-Motes & Lavender Glint",
            primaryColor = Color(0xFFA855F7),
            secondaryColor = Color(0xFF7E22CE),
            accentColor = Color(0xFFE9D5FF),
            hueShiftDegrees = 262f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE VII"
        ),
        SparkleType(
            id = "type_8",
            code = "Type VIII",
            name = "Exhibition Type VIII (Pearl-Frost Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type VIII. Lightens the Aniimo's coat into a clean pearl-frost alabaster tone with icy cyan highlights.",
            coatEffect = "Alabaster Pearl-Frost & Pale Cyan Shift",
            auraEffect = "Frost-White Diamond Star-Motes",
            primaryColor = Color(0xFFE2E8F0),
            secondaryColor = Color(0xFF38BDF8),
            accentColor = Color(0xFFFFFFFF),
            hueShiftDegrees = 160f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE VIII"
        ),
        SparkleType(
            id = "type_9",
            code = "Type IX",
            name = "Exhibition Type IX (Charcoal-Garnet Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type IX. Deepens the creature's coat into sleek charcoal-slate with warm garnet-red markings and claws.",
            coatEffect = "Charcoal-Slate Coat & Garnet Marking Shift",
            auraEffect = "Warm Garnet & Dusk Star-Motes",
            primaryColor = Color(0xFFFB7185),
            secondaryColor = Color(0xFF475569),
            accentColor = Color(0xFFFFE4E6),
            hueShiftDegrees = 348f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE IX"
        ),
        SparkleType(
            id = "type_10",
            code = "Type X",
            name = "Exhibition Type X (Teal-Opal Coat)",
            rarity = "Numbered (I–X)",
            odds = "Wild 1/20,000 • Cube 9.9%",
            description = "Exhibition Wall Sparkling Type X. Shifts the Aniimo's coat into seafoam teal-opal with iridescent lavender-cyan secondary highlights.",
            coatEffect = "Seafoam Teal-Opal & Lavender Trim Shift",
            auraEffect = "Opaline Teal-Violet Star-Motes",
            primaryColor = Color(0xFF2DD4BF),
            secondaryColor = Color(0xFFC084FC),
            accentColor = Color(0xFF99F6E4),
            hueShiftDegrees = 152f,
            isSpecial = false,
            badgeLabel = "EXHIBITION WALL • TYPE X"
        ),
        SparkleType(
            id = "dazzling",
            code = "Dazzling",
            name = "Dazzling Sparkling (Hidden Bright Variant)",
            rarity = "Hidden Mythic",
            odds = "Normal Cube 1.0% • Sigil 0.5%",
            description = "Hidden Dazzling Sparkling variant! Exclusive to Sparkling Cube (Normal Egg: 1.0%) and Sparkling Sigil / Pigment (0.5%). Bathes the Aniimo in a bright champagne-gold & diamond-white coat with intense starlight burst flares.",
            coatEffect = "Hidden Bright Champagne-Gold & Diamond Coat",
            auraEffect = "Radiant Dazzling Starburst & Golden Halo",
            primaryColor = Color(0xFFFACC15),
            secondaryColor = Color(0xFFFEF08A),
            accentColor = Color(0xFFFFFFFF),
            hueShiftDegrees = 42f,
            isSpecial = true,
            badgeLabel = "HIDDEN SPARKLE • DAZZLING (1% NORMAL CUBE)",
            cubeNormalOdds = "1.0% (Exclusive to Normal Egg Cube)",
            cubeEggHeistOdds = "0.0% (Cannot roll from Egg Heist Cube)",
            sigilPigmentOdds = "0.5% (Sparkling Sigil / Pigment)"
        ),
        SparkleType(
            id = "shadow",
            code = "Shadow",
            name = "Shadow Sparkling (Hidden Dark Variant)",
            rarity = "Hidden Mythic",
            odds = "Egg Heist Cube 1.0% • Sigil 0.5%",
            description = "Hidden Shadow Sparkling variant! Exclusive to Sparkling Cube (Egg Heist Egg: 1.0%) and Sparkling Sigil / Pigment (0.5%). Gives the Aniimo a dark obsidian-indigo coat with abyssal violet-blue sparkles.",
            coatEffect = "Hidden Dark Obsidian-Indigo & Violet Coat",
            auraEffect = "Abyssal Shadow Star-Motes & Dark Indigo Ring",
            primaryColor = Color(0xFFC084FC),
            secondaryColor = Color(0xFF3B0764),
            accentColor = Color(0xFFF3E8FF),
            hueShiftDegrees = 280f,
            isSpecial = true,
            badgeLabel = "HIDDEN SPARKLE • SHADOW (1% EGG HEIST CUBE)",
            cubeNormalOdds = "0.0% (Cannot roll from Normal Egg Cube)",
            cubeEggHeistOdds = "1.0% (Exclusive to Egg Heist Cube)",
            sigilPigmentOdds = "0.5% (Sparkling Sigil / Pigment)"
        )
    )

    val defaultSparkle: SparkleType = allSparkleTypes.first()

    fun getSparkleById(id: String): SparkleType {
        return allSparkleTypes.find { it.id == id } ?: allSparkleTypes.first()
    }

    // =========================================================================
    // VERIFIED ONLINE PER-SPECIES FORM ELIGIBILITY MAPS (126 CANONICAL ANIIMO)
    // Sources: aniimotools.dev, aniimoguide.com, aniimo.info, aniimoprismana.wiki
    // =========================================================================

    /**
     * 1. Highland Form (Russet Highlands & Crags — includes Chirpi/Tromber/Cornet Wind+Grass shift,
     *    Emberpup/Flameruff/Scorchhowl, Iris/Irisal/Irisalis, Skippy/Pranky/Sherro, Cubbo/Grizbo, etc.)
     */
    private val highlandFormSpecies = setOf(
        "emberpup", "flameruff", "scorchhowl",
        "iris", "irisal", "irisalis",
        "chirpi", "tromber", "cornet",
        "flamerion",
        "skippy", "pranky", "sherro",
        "cubbo", "grizbo",
        "pomegg", "pomawk",
        "manelet", "fentuft", "fenmane",
        "bolty", "blazen",
        "cozite", "bailite"
    )

    /**
     * 2. Mountain Woods Form (Mountain Woods & Timberline Canopy — includes Emberpup line, Iris line,
     *    Flutternym/Gracewing/Somniwing, Budclaw/Shrubclaw/Geoclaw, Budsquire/Thornblade,
     *    Helmut/Pawney/Rookey, Jawling/Helmwhelp/Helgon/Infergon, Squarrel/Squashel, Flamerion, Jaguaraun)
     */
    private val mountainWoodsFormSpecies = setOf(
        "emberpup", "flameruff", "scorchhowl",
        "iris", "irisal", "irisalis",
        "flutternym", "gracewing", "somniwing",
        "budclaw", "shrubclaw", "geoclaw",
        "budsquire", "thornblade",
        "squarrel", "squashel",
        "flamerion",
        "helmut", "pawney", "rookey",
        "jawling", "helmwhelp", "helgon", "infergon",
        "jaguaraun", "jabster"
    )

    /**
     * 3. Snowfield Form (Snowy Weather & Glacial Spire — includes Skippy/Pranky, Glacy,
     *    Baleetle/Waleetle, Helmut/Pawney/Rookey, Bonesky/Fenrier/Glynsera, Sera/Malangel/Malevsera,
     *    Cheekie/Wavwal, Sugarfish/Sugartini, Emberpup/Flameruff/Scorchhowl/Inferlupa)
     */
    private val snowfieldFormSpecies = setOf(
        "skippy", "pranky",
        "glacy",
        "baleetle", "waleetle",
        "helmut", "pawney", "rookey",
        "bonesky", "fenrier", "glynsera",
        "sera", "malangel", "malevsera",
        "cheekie", "wavwal",
        "sugarfish", "sugartini",
        "emberpup", "flameruff", "scorchhowl", "inferlupa"
    )

    /**
     * 4. Flowerfield / Sea of Flowers Form (Blooming Meadow — includes Skippy/Pranky, Flamerion,
     *    Flutternym/Gracewing/Somniwing, Leafy, Iris/Irisal/Irisalis, Budsquire/Thornblade,
     *    Melloblum, Dewy/Fragrancier, Hummin/Witchin, Tuckin, Silklet)
     */
    private val seaOfFlowersFormSpecies = setOf(
        "skippy", "pranky",
        "flamerion",
        "flutternym", "gracewing", "somniwing",
        "leafy",
        "iris", "irisal", "irisalis",
        "budsquire", "thornblade", "melloblum",
        "dewy", "fragrancier",
        "hummin", "witchin",
        "tuckin", "silklet"
    )

    /**
     * 5. Beach Form (Azure Coast & Whisperwake Beaches — Chirpi/Tromber/Cornet, Tubster,
     *    Shelly/Sheldon, Sherro, Susuta/Popota/Piopiota)
     */
    private val beachFormSpecies = setOf(
        "chirpi", "tromber", "cornet",
        "tubster",
        "shelly", "sheldon",
        "sherro",
        "susuta", "popota", "piopiota"
    )

    /**
     * 6. Rainfall Weather Form (Nimbi Water/Wind, Turbo, Stellarys, Bulbly/Veilfloat/Luminelle,
     *    Reefish/Coraliz, Prawnch/Jabster)
     */
    private val rainfallFormSpecies = setOf(
        "nimbi", "turbo", "stellarys",
        "bulbly", "veilfloat", "luminelle",
        "reefish", "coraliz",
        "prawnch", "jabster"
    )

    /**
     * 7. Tempest Storm Form (Whisperwake Isles Storm Weather — Bubbeep/Glameep Grass+Wind,
     *    Jeglid/Jeburst Wind+Water)
     */
    private val tempestFormSpecies = setOf(
        "bubbeep", "glameep",
        "jeglid", "jeburst"
    )

    /**
     * 8. Waterfall Form (Cloudreach Falls — Dewlop/Dewple, Fahloo/Erlath)
     */
    private val waterfallFormSpecies = setOf(
        "dewlop", "dewple",
        "fahloo", "erlath"
    )

    /**
     * 9. Nighttime Lunar Form (Panpanta, Bonesky/Fenrier/Glynsera, Hypnowl/Mesmerowl)
     */
    private val nighttimeFormSpecies = setOf(
        "panpanta",
        "bonesky", "fenrier", "glynsera",
        "hypnowl", "mesmerowl"
    )

    /**
     * 10. Cloudmist & Plateau Forms (Signature weather/biome shifts for Nimbi)
     */
    private val cloudmistFormSpecies = setOf("nimbi")
    private val plateauFormSpecies = setOf("nimbi")

    /**
     * 11. Tidal Wetland Form (Whisperwake Isles coral trenches & wetlands)
     */
    private val tidalWetlandFormSpecies = setOf(
        "dewlop", "dewple", "whirlbite",
        "octoball", "popapus", "gachapus",
        "shelly", "sheldon",
        "reefish", "coraliz",
        "lilipole", "scrubbit"
    )

    /**
     * 12. Prismana Form — Exact online-verified Prismana acquisition methods per species
     *     (Prismana Pact 14, Vein Abundance 4, Egg/RV/Challenge/Event 9, Whisperwake Isles 10,
     *     plus their direct Prismana Flow pre-evolutions).
     */
    private val prismanaAcquisitionBySpecies: Map<String, String> = mapOf(
        // 14 Prismana Pact & Regional Prismana Flow Species
        "blazen" to "Prismana Pact & Russet Highlands Prismana Flow weather event",
        "cornet" to "Prismana Pact & Breezy Plains Prismana Flow weather event",
        "glacy" to "Prismana Pact & Snowfield Prismana Flow weather event",
        "grizbo" to "Prismana Pact & Highland Prismana Flow weather event",
        "hexxin" to "Prismana Pact & Nocturnal Prismana Flow weather event",
        "ignitis" to "Prismana Pact & Scorched Plateau Prismana Flow weather event",
        "luminelle" to "Prismana Pact & Thunder Ridge Prismana Flow weather event",
        "magmarex" to "Prismana Pact & Magma Caldera Prismana Flow weather event",
        "panpanta" to "Prismana Pact & Azure Coast Prismana Flow weather event",
        "pawney" to "Prismana Pact & Mountain Woods Prismana Flow weather event",
        "scorchhowl" to "Prismana Pact & Volcanic Ridge Prismana Flow weather event",
        "stellarys" to "Prismana Pact & Mirror Lake Prismana Flow weather event",
        "thornblade" to "Prismana Pact & Sea of Flowers Prismana Flow weather event",
        "turbo" to "Prismana Pact & Windmill Outpost Prismana Flow weather event",
        // 4 Vein Abundance Weekly Event Species
        "glynsera" to "Vein Abundance Weekly Event (Lunar Spectral Vein)",
        "melloblum" to "Vein Abundance Weekly Event (Floral Nectar Vein)",
        "waleetle" to "Vein Abundance Weekly Event (Tectonic Ore Vein)",
        "inferlupa" to "Vein Abundance Weekly Event (Bloodmoon Pyre Vein)",
        // 9 Special Unlock Species (Egg Hatching / Battle Pass / RV / Achievements / Trainer Challenges / Legendary)
        "iris" to "Egg Hatching & Nurture in Sea of Flowers Prismana Flow",
        "irisal" to "Evolve Prismana Iris or Nurture during Sea of Flowers Prismana Flow",
        "irisalis" to "Evolve Prismana Irisal / Sea of Flowers Prismana Egg Hatching",
        "fulmintis" to "Apex Legendary Prismana Encounter & Prismana Egg Incubation",
        "fenmane" to "Battle Pass / RV Research Milestone & Thunder Ridge Prismana Flow",
        "infergon" to "Master Trainer Challenge Reward & Caldera Prismana Incubation",
        "sparkelf" to "Special Festival Event & Keepsake Prismana Pact",
        "somniwing" to "Achievement Milestone & Sea of Flowers Prismana Flow",
        "sherro" to "RV Explorer Achievement & Highland Prismana Flow",
        // 10 Whisperwake Isles Prismana Additions
        "jeburst" to "Whisperwake Isles Storm Strait Prismana Flow & Apex Raid Drop",
        "glameep" to "Whisperwake Isles Tempest Highland Prismana Flow",
        "pouchwisp" to "Whisperwake Isles Sunken Galleon Prismana Flow",
        "royalby" to "Whisperwake Isles Sovereign Roost Prismana Flow",
        "gachapus" to "Whisperwake Isles Arcade Cove Prismana Flow & Capsule Jackpot",
        "sugartini" to "Whisperwake Isles Frosted Atoll Prismana Flow",
        "jabster" to "Whisperwake Isles Tidal Boxing Reef Prismana Flow",
        "whirlbite" to "Whisperwake Isles Abyssal Trench Prismana Flow",
        "ekrim" to "Whisperwake Isles Phantom Reef Prismana Flow",
        "dewple" to "Whisperwake Isles Cloudreach Falls Prismana Flow",
        // Pre-evolution stages compatible with Prismana Flow / Nurture into the above 37 lines
        "emberpup" to "Prismana Flow Wild Catch / Nurture (Evolves into Prismana Scorchhowl or Inferlupa)",
        "flameruff" to "Prismana Flow Wild Catch / Evolve from Prismana Emberpup",
        "celestis" to "Prismana Flow Wild Catch / Nurture (Evolves into Prismana Stellarys)",
        "leafy" to "Prismana Flow Wild Catch / Nurture in Sea of Flowers",
        "budclaw" to "Prismana Flow Wild Catch / Nurture in Mountain Woods",
        "shrubclaw" to "Evolve from Prismana Budclaw during Prismana Flow",
        "geoclaw" to "Evolve from Prismana Shrubclaw during Prismana Flow",
        "budsquire" to "Prismana Flow Wild Catch / Nurture (Evolves into Prismana Thornblade or Melloblum)",
        "flutternym" to "Prismana Flow Wild Catch / Nurture (Evolves into Prismana Somniwing)",
        "gracewing" to "Evolve from Prismana Flutternym during Prismana Flow",
        "dewy" to "Prismana Flow Wild Catch / Nurture in Sea of Flowers",
        "fragrancier" to "Evolve from Prismana Dewy during Prismana Flow",
        "wisptis" to "Prismana Egg Hatching / Nurture (Evolves into Prismana Ignitis or Fulmintis)",
        "bolty" to "Prismana Flow Wild Catch (Evolves into Prismana Blazen)",
        "cubbo" to "Prismana Flow Wild Catch (Evolves into Prismana Grizbo)",
        "dewlop" to "Prismana Flow Wild Catch (Evolves into Prismana Dewple or Whirlbite)",
        "sera" to "Prismana Flow Wild Catch / Egg Incubation",
        "malangel" to "Evolve from Prismana Sera during Snowfield Prismana Flow",
        "malevsera" to "Apex Glacial Spire Prismana Encounter & Evolve from Prismana Malangel",
        "manelet" to "Prismana Flow Wild Catch (Evolves into Prismana Fenmane)",
        "fentuft" to "Evolve from Prismana Manelet during Thunder Prismana Flow",
        "dazmand" to "Radiant Sanctum Prismana Flow Encounter",
        "octoball" to "Whisperwake Prismana Flow Catch (Evolves into Prismana Gachapus)"
    )

    private val prismanaEligibleSpecies: Set<String> = prismanaAcquisitionBySpecies.keys

    /**
     * 13. Evolution-locked final stages where evolving an Umbral pre-evolution REMOVES the dark Umbral
     *     cosmetic coat (while keeping the Guaranteed Perfect Innate Potential 6x Max IV stats!).
     */
    private val evolutionStripsUmbralCoatSpecies = setOf(
        "scorchhowl", "inferlupa", "stellarys", "cornet", "irisalis",
        "shrubclaw", "geoclaw", "flamerion", "gracewing", "somniwing",
        "thornblade", "melloblum", "pomawk", "fragrancier", "fenrier",
        "glynsera", "blazen", "squashel", "piopiota", "sheldon",
        "sherro", "grizbo", "dewple", "whirlbite", "malevsera",
        "fenmane", "dazmand", "gachapus",
        "witchin", "eklue", "waleetle", "bouldus", "pawney", "rookey",
        "helgon", "infergon", "magmarex", "minespine", "luminelle", "erlath",
        "coraliz", "mesmerowl", "pouchwisp", "glameep", "scrubbit", "ekrim", "sugartini"
    )

    /**
     * Returns the exact per-species Form & Rarity Axis availability for an Aniimo.
     */
    fun getFormAvailability(aniimo: Aniimo): SpeciesFormAvailability {
        val key = aniimo.id.lowercase().trim()
        val regionalCategories = mutableListOf<FormCategory>()
        if (highlandFormSpecies.contains(key)) regionalCategories.add(FormCategory.HIGHLAND)
        if (mountainWoodsFormSpecies.contains(key)) regionalCategories.add(FormCategory.MOUNTAIN_WOODS)
        if (snowfieldFormSpecies.contains(key)) regionalCategories.add(FormCategory.SNOWFIELD)
        if (seaOfFlowersFormSpecies.contains(key)) regionalCategories.add(FormCategory.SEA_OF_FLOWERS)
        if (beachFormSpecies.contains(key)) regionalCategories.add(FormCategory.BEACH)
        if (rainfallFormSpecies.contains(key)) regionalCategories.add(FormCategory.RAINFALL)
        if (tempestFormSpecies.contains(key)) regionalCategories.add(FormCategory.TEMPEST)
        if (waterfallFormSpecies.contains(key)) regionalCategories.add(FormCategory.WATERFALL)
        if (nighttimeFormSpecies.contains(key)) regionalCategories.add(FormCategory.NIGHTTIME)
        if (cloudmistFormSpecies.contains(key)) regionalCategories.add(FormCategory.CLOUDMIST)
        if (plateauFormSpecies.contains(key)) regionalCategories.add(FormCategory.PLATEAU)
        if (tidalWetlandFormSpecies.contains(key)) regionalCategories.add(FormCategory.TIDAL_WETLAND)

        val prismanaSource = prismanaAcquisitionBySpecies[key]
        val hasPrismana = prismanaSource != null
        val hasUmbralCoat = !evolutionStripsUmbralCoatSpecies.contains(key)

        val prismanaSummary = if (hasPrismana) {
            "Verified Prismana Species • Unlock Method: $prismanaSource"
        } else {
            "No Prismana Form in-game (${aniimo.name} does not have a Prismana variant in the official Aniilog)"
        }

        val umbralNote = if (hasUmbralCoat) {
            "1 in 3,000 Wild Surge / Operation: Egg Heist catch roll • Guarantees Perfect Innate Potential (6x Max IVs). Evolving removes the dark Umbral coat while keeping Perfect Potential!"
        } else {
            "Evolved Stage Rule: Evolving an Umbral pre-evolution into ${aniimo.name} removes the dark Umbral cosmetic coat, though ${aniimo.name} permanently retains its Guaranteed Perfect Innate Potential (6x Max IVs)!"
        }

        return SpeciesFormAvailability(
            aniimoId = aniimo.id,
            hasRegionalForm = regionalCategories.isNotEmpty(),
            regionalFormCategories = regionalCategories,
            hasPrismanaForm = hasPrismana,
            prismanaSourceSummary = prismanaSummary,
            hasUmbralCoatRoll = hasUmbralCoat,
            umbralMechanicsNote = umbralNote
        )
    }

    /**
     * Returns ONLY the authentic in-game Forms & Variants verified online for this specific Aniimo.
     */
    fun getRegionalVariantsForAniimo(aniimo: Aniimo): List<RegionalVariant> {
        val key = aniimo.id.lowercase().trim()
        val availability = getFormAvailability(aniimo)
        val forms = mutableListOf<RegionalVariant>()

        // 1. Standard Base Form
        val nativeZone = if (aniimo.region == GameRegion.WHISPERWAKE_ISLES) {
            "Whisperwake Isles (Native Habitat)"
        } else {
            "Breezy Plains (Native Habitat)"
        }
        forms.add(
            RegionalVariant(
                id = "${aniimo.id}_standard",
                variantName = "Standard ${aniimo.name}",
                shortBadge = "Standard Form",
                regionName = nativeZone,
                primaryElement = aniimo.element,
                secondaryElement = aniimo.secondaryElement,
                coatPaletteSummary = if (aniimo.secondaryElement != null) {
                    "Official in-game Standard Form of ${aniimo.name} with its natural ${aniimo.element.displayName} / ${aniimo.secondaryElement?.displayName} dual-element palette and baseline attributes."
                } else {
                    "Official in-game Standard Form of ${aniimo.name} with its natural ${aniimo.element.displayName} palette and baseline attributes."
                },
                traitAdaptation = if (availability.hasRegionalForm || availability.hasPrismanaForm) {
                    "Baseline Species Form • ${availability.regionalSummaryBadge}"
                } else {
                    "Standard Form Only Species (${aniimo.name} has no alternate Regional or Prismana form in-game)."
                },
                statModifierSummary = if (aniimo.secondaryElement != null) {
                    "Standard Base Stats • Native ${aniimo.element.displayName} / ${aniimo.secondaryElement?.displayName} Affinity"
                } else {
                    "Standard Base Stats • Native ${aniimo.element.displayName} Affinity"
                },
                primaryTint = aniimo.element.primaryColor,
                secondaryTint = aniimo.element.glowColor,
                accentTint = Color(0xFFE2E8F0),
                isNativeDefault = true,
                category = FormCategory.STANDARD,
                acquisitionMethod = if (aniimo.stage == EvolutionStage.LUMIN || aniimo.stage == EvolutionStage.STANDALONE) {
                    "Wild Catch in $nativeZone"
                } else {
                    aniimo.evolutionRequirement ?: "Evolve from pre-evolution"
                }
            )
        )

        // 2. Highland Form
        if (highlandFormSpecies.contains(key)) {
            val isChirpiLine = key == "chirpi" || key == "tromber" || key == "cornet"
            val highlandSecElement = when {
                isChirpiLine -> Element.GRASS
                aniimo.element == Element.EARTH -> Element.FIRE
                aniimo.element == Element.FIRE -> Element.EARTH
                aniimo.element == Element.LIGHTNING -> Element.WIND
                else -> Element.EARTH
            }
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_highland",
                    variantName = "Highland Form ${aniimo.name}",
                    shortBadge = "Highland Form",
                    regionName = "Russet Highlands (Highland Plateau & Crags)",
                    primaryElement = aniimo.element,
                    secondaryElement = highlandSecElement,
                    coatPaletteSummary = if (isChirpiLine) {
                        "Verified Highland Form of ${aniimo.name}! Gains dual Wind/Grass elemental affinity with highland sage-green and russet plumage."
                    } else {
                        "Verified Highland Form of ${aniimo.name}! Features rugged russet-ochre and highland-gold coat markings shaped by the Russet Highlands."
                    },
                    traitAdaptation = "Highland Adaptation: Evolving or encountering ${aniimo.name} in the Russet Highlands grants +12% BREAK & +8% PDEF.",
                    statModifierSummary = "Highland Form • +12% BREAK / +8% PDEF",
                    primaryTint = Color(0xFFF59E0B),
                    secondaryTint = Color(0xFFD97706),
                    accentTint = Color(0xFFFEF3C7),
                    category = FormCategory.HIGHLAND,
                    acquisitionMethod = "Evolve or catch in Russet Highlands"
                )
            )
        }

        // 3. Mountain Woods Form
        if (mountainWoodsFormSpecies.contains(key)) {
            val woodsSecElement = when (aniimo.element) {
                Element.GRASS -> Element.EARTH
                Element.EARTH -> Element.GRASS
                else -> Element.GRASS
            }
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_mountain_woods",
                    variantName = "Mountain Woods ${aniimo.name}",
                    shortBadge = "Mountain Woods",
                    regionName = "Mountain Woods & Timberline Canopy",
                    primaryElement = aniimo.element,
                    secondaryElement = woodsSecElement,
                    coatPaletteSummary = "Verified Mountain Woods Form of ${aniimo.name}! Features deep timber-moss green and bark-amber camouflage adapted to forested mountain ridges.",
                    traitAdaptation = "Timberline Stalker: Grants +12% Skill Haste & +10% ATK when fighting in forested or campaign zones.",
                    statModifierSummary = "Mountain Woods Form • +10% ATK / +12% REGEN",
                    primaryTint = Color(0xFF10B981),
                    secondaryTint = Color(0xFF059669),
                    accentTint = Color(0xFFA7F3D0),
                    category = FormCategory.MOUNTAIN_WOODS,
                    acquisitionMethod = "Evolve or catch in Mountain Woods biome"
                )
            )
        }

        // 4. Snowfield Form
        if (snowfieldFormSpecies.contains(key)) {
            val snowSecElement = if (aniimo.element == Element.ICE) Element.WATER else Element.ICE
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_snowfield",
                    variantName = "Snowfield Form ${aniimo.name}",
                    shortBadge = "Snowfield Form",
                    regionName = "Snowfield & Glacial Spire (Snowy Weather)",
                    primaryElement = aniimo.element,
                    secondaryElement = snowSecElement,
                    coatPaletteSummary = "Verified Snowfield Form of ${aniimo.name}! Unlocked in Snowfields and Snowy weather, shifting its coat to frost-white and glacial cyan.",
                    traitAdaptation = "Snowfield Frost Coat: Regional snow variant with +15% Crit DMG against chilled or staggered targets.",
                    statModifierSummary = "Snowfield Form • +15% CRIT DMG / +10% MDEF",
                    primaryTint = Color(0xFF38BDF8),
                    secondaryTint = Color(0xFF06B6D4),
                    accentTint = Color(0xFFE0F2FE),
                    category = FormCategory.SNOWFIELD,
                    acquisitionMethod = "Spawn or evolve in Snowfield / Snowy Weather"
                )
            )
        }

        // 5. Flowerfield / Sea of Flowers Form
        if (seaOfFlowersFormSpecies.contains(key)) {
            val floralSecElement = if (aniimo.element == Element.GRASS) Element.LIGHT else Element.GRASS
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_sea_of_flowers",
                    variantName = "Flowerfield Form ${aniimo.name}",
                    shortBadge = "Flowerfield Form",
                    regionName = "Sea of Flowers (Flowerfield Biome)",
                    primaryElement = aniimo.element,
                    secondaryElement = floralSecElement,
                    coatPaletteSummary = "Verified Flowerfield / Sea of Flowers Form of ${aniimo.name}! Features vibrant blossom-pink, lavender, and sunlit petal accents.",
                    traitAdaptation = "Floral Bloom Resonance: +14% Energy Regen & +10% Elemental Skill DMG.",
                    statModifierSummary = "Flowerfield Form • +14% REGEN / +10% ATK",
                    primaryTint = Color(0xFFF472B6),
                    secondaryTint = Color(0xFFEC4899),
                    accentTint = Color(0xFFFCE7F3),
                    category = FormCategory.SEA_OF_FLOWERS,
                    acquisitionMethod = "Evolve or catch in the Sea of Flowers (Flowerfield)"
                )
            )
        }

        // 6. Beach Form
        if (beachFormSpecies.contains(key)) {
            val beachSecElement = if (aniimo.element == Element.WATER) Element.WIND else Element.WATER
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_beach",
                    variantName = "Beach Form ${aniimo.name}",
                    shortBadge = "Beach Form",
                    regionName = "Azure Coast & Whisperwake Beaches",
                    primaryElement = aniimo.element,
                    secondaryElement = beachSecElement,
                    coatPaletteSummary = "Verified Beach Form of ${aniimo.name}! Sun-bleached coral, sandy gold, and tropical turquoise markings adapted to coastal shorelines.",
                    traitAdaptation = "Coastal Breeze: +12% Movement Speed & +12% Energy Regen in coastal and open arenas.",
                    statModifierSummary = "Beach Form • +12% REGEN / +10% HP",
                    primaryTint = Color(0xFF38BDF8),
                    secondaryTint = Color(0xFFFBBF24),
                    accentTint = Color(0xFFE0F2FE),
                    category = FormCategory.BEACH,
                    acquisitionMethod = "Catch or evolve along Azure Coast / Whisperwake Beaches"
                )
            )
        }

        // 7. Rainfall Form
        if (rainfallFormSpecies.contains(key)) {
            val rainSecElement = if (key == "nimbi") Element.WIND else Element.WATER
            val rainPrimElement = if (key == "nimbi") Element.WATER else aniimo.element
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_rainfall",
                    variantName = "Rainfall Form ${aniimo.name}",
                    shortBadge = "Rainfall Form",
                    regionName = "Rainfall Weather Phenomenon",
                    primaryElement = rainPrimElement,
                    secondaryElement = rainSecElement,
                    coatPaletteSummary = if (key == "nimbi") {
                        "Verified Rainfall Form of Nimbi! Shifts Nimbi into a dual Water/Wind stormcloud form heavy with rain."
                    } else {
                        "Verified Rainfall Form of ${aniimo.name}! Awakened during active Rainfall weather with luminous rain-slicked hydro accents."
                    },
                    traitAdaptation = "Rainstorm Conduction: +16% Energy Regen and +12% Skill Damage during rain or alongside Water allies.",
                    statModifierSummary = "Rainfall Form • +16% REGEN / +12% SKILL DMG",
                    primaryTint = Color(0xFF60A5FA),
                    secondaryTint = Color(0xFF2563EB),
                    accentTint = Color(0xFFDBEAFE),
                    category = FormCategory.RAINFALL,
                    acquisitionMethod = "Encounter or evolve during active Rainfall weather"
                )
            )
        }

        // 8. Tempest Form (Whisperwake Isles Storm Weather)
        if (tempestFormSpecies.contains(key)) {
            val tempestSecElement = if (aniimo.element == Element.GRASS) Element.WIND else Element.WIND
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_tempest",
                    variantName = "Tempest Form ${aniimo.name}",
                    shortBadge = "Tempest Form",
                    regionName = "Whisperwake Isles (Tempest Storm Weather)",
                    primaryElement = aniimo.element,
                    secondaryElement = tempestSecElement,
                    coatPaletteSummary = "Verified Whisperwake Isles Tempest Form of ${aniimo.name}! Infuses ${aniimo.name} with gale-force Wind affinity and storm-charged markings.",
                    traitAdaptation = "Tempest Surge: Grants dual-element Wind resonance, +15% Attack Speed, and +14% Break Efficiency.",
                    statModifierSummary = "Tempest Form • +14% BREAK / +15% HASTE",
                    primaryTint = Color(0xFF34D399),
                    secondaryTint = Color(0xFF059669),
                    accentTint = Color(0xFFD1FAE5),
                    category = FormCategory.TEMPEST,
                    acquisitionMethod = "Encounter or evolve during Tempest weather in Whisperwake Isles"
                )
            )
        }

        // 9. Waterfall Form
        if (waterfallFormSpecies.contains(key)) {
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_waterfall",
                    variantName = "Waterfall Form ${aniimo.name}",
                    shortBadge = "Waterfall Form",
                    regionName = "Cloudreach Falls & Cascading Sanctuary",
                    primaryElement = aniimo.element,
                    secondaryElement = Element.ICE,
                    coatPaletteSummary = "Verified Waterfall Form of ${aniimo.name}! Shaped by high-pressure mountain cascades with mist-white and aquamarine patterns.",
                    traitAdaptation = "Cascade Pressure: +15% Core Skill Multiplier and +12% Break Efficiency.",
                    statModifierSummary = "Waterfall Form • +15% CORE DMG / +12% BREAK",
                    primaryTint = Color(0xFF22D3EE),
                    secondaryTint = Color(0xFF0891B2),
                    accentTint = Color(0xFFCFFAFE),
                    category = FormCategory.WATERFALL,
                    acquisitionMethod = "Encounter or evolve beneath Cloudreach Falls"
                )
            )
        }

        // 10. Nighttime Form
        if (nighttimeFormSpecies.contains(key)) {
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_nighttime",
                    variantName = "Nighttime Form ${aniimo.name}",
                    shortBadge = "Nighttime Form",
                    regionName = "Nocturnal Encounter (20:00 – 05:00 In-Game Time)",
                    primaryElement = aniimo.element,
                    secondaryElement = Element.DARK,
                    coatPaletteSummary = "Verified Nighttime Form of ${aniimo.name}! Features bioluminescent starlight markings that glow exclusively at night.",
                    traitAdaptation = "Lunar Bioluminescence: +15% Crit Rate and +12% MDEF during night or abyssal encounters.",
                    statModifierSummary = "Nighttime Form • +15% CRIT RATE / +12% MDEF",
                    primaryTint = Color(0xFF818CF8),
                    secondaryTint = Color(0xFF4F46E5),
                    accentTint = Color(0xFFE0E7FF),
                    category = FormCategory.NIGHTTIME,
                    acquisitionMethod = "Catch or evolve at Night (20:00 – 05:00)"
                )
            )
        }

        // 11. Cloudmist Form (Nimbi specialty)
        if (cloudmistFormSpecies.contains(key)) {
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_cloudmist",
                    variantName = "Cloudmist Form ${aniimo.name}",
                    shortBadge = "Cloudmist Form",
                    regionName = "High-Altitude Cloudmist Ridge",
                    primaryElement = aniimo.element,
                    secondaryElement = Element.ICE,
                    coatPaletteSummary = "Verified Cloudmist Form of ${aniimo.name}! Condensed alpine fog gives ${aniimo.name} a pearlescent mist body.",
                    traitAdaptation = "Alpine Mist Veil: Grants +18% Evasion and +15% Energy Regen.",
                    statModifierSummary = "Cloudmist Form • +15% REGEN / +18% EVASION",
                    primaryTint = Color(0xFFE2E8F0),
                    secondaryTint = Color(0xFF94A3B8),
                    accentTint = Color(0xFFFFFFFF),
                    category = FormCategory.CLOUDMIST,
                    acquisitionMethod = "Encounter in Cloudmist weather at high altitude"
                )
            )
        }

        // 12. Plateau Form (Nimbi specialty)
        if (plateauFormSpecies.contains(key)) {
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_plateau",
                    variantName = "Plateau Form ${aniimo.name}",
                    shortBadge = "Plateau Form",
                    regionName = "Sunbaked Plateau & Sandstone Mesa",
                    primaryElement = aniimo.element,
                    secondaryElement = Element.EARTH,
                    coatPaletteSummary = "Verified Plateau Form of ${aniimo.name}! Warm sandstone-amber thermal updraft form native to dry plateaus.",
                    traitAdaptation = "Thermal Updraft: +14% ATK and +12% PDEF.",
                    statModifierSummary = "Plateau Form • +14% ATK / +12% PDEF",
                    primaryTint = Color(0xFFFB923C),
                    secondaryTint = Color(0xFFEA580C),
                    accentTint = Color(0xFFFFEDD5),
                    category = FormCategory.PLATEAU,
                    acquisitionMethod = "Encounter on the Sunbaked Plateau"
                )
            )
        }

        // 13. Tidal Wetland Form
        if (tidalWetlandFormSpecies.contains(key)) {
            val tidalSecElement = if (aniimo.element == Element.WATER) Element.ICE else Element.WATER
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_tidal_wetland",
                    variantName = "Tidal Wetland ${aniimo.name}",
                    shortBadge = "Tidal Form",
                    regionName = "Whisperwake Isles (Coral Trench & Wetlands)",
                    primaryElement = aniimo.element,
                    secondaryElement = tidalSecElement,
                    coatPaletteSummary = "Verified Tidal Wetland Form of ${aniimo.name}! Features bioluminescent reef-teal fins and pelagic markings.",
                    traitAdaptation = "Wetland Current: +14% BREAK Efficiency & +10% Max HP in aquatic/raid encounters.",
                    statModifierSummary = "Tidal Form • +14% BREAK / +10% HP",
                    primaryTint = Color(0xFF06B6D4),
                    secondaryTint = Color(0xFF0284C7),
                    accentTint = Color(0xFFCFFAFE),
                    category = FormCategory.TIDAL_WETLAND,
                    acquisitionMethod = "Encounter or evolve in Whisperwake Wetlands"
                )
            )
        }

        // 14. Prismana Form (ONLY for verified Prismana-eligible species!)
        if (availability.hasPrismanaForm) {
            val prismanaPrimElement = if (key == "inferlupa") Element.FIRE else aniimo.element
            val prismanaSecElement = when {
                key == "inferlupa" -> Element.DARK
                aniimo.secondaryElement != null -> aniimo.secondaryElement
                aniimo.element == Element.LIGHT -> Element.LIGHTNING
                aniimo.element == Element.LIGHTNING -> Element.LIGHT
                else -> Element.LIGHT
            }
            val unlockMethod = prismanaAcquisitionBySpecies[key]
                ?: "Catch during regional Prismana Flow weather, Nurture, or claim via Prismana Pact"
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_prismana",
                    variantName = "Prismana ${aniimo.name}",
                    shortBadge = "Prismana Form",
                    regionName = unlockMethod,
                    primaryElement = prismanaPrimElement,
                    secondaryElement = prismanaSecElement,
                    coatPaletteSummary = if (prismanaSecElement != null) {
                        "Official Rainbow-Tier Prismana Form of ${aniimo.name}! Features prism-refractive crystal highlights, authentic ${prismanaPrimElement.displayName} / ${prismanaSecElement.displayName} elemental affinity, and Prismana moveset scaling."
                    } else {
                        "Official Rainbow-Tier Prismana Form of ${aniimo.name}! Features prism-refractive crystal highlights, ${prismanaPrimElement.displayName} affinity, and Prismana moveset scaling."
                    },
                    traitAdaptation = "Prismana Flow Mutation: Unlocks Prismana rainbow aura and +18% Burst Window DMG during boss Stagger.",
                    statModifierSummary = "Prismana Tier • +18% BURST / +12% ATK",
                    primaryTint = Color(0xFFA855F7),
                    secondaryTint = Color(0xFFFACC15),
                    accentTint = Color(0xFFF3E8FF),
                    category = FormCategory.PRISMANA,
                    acquisitionMethod = unlockMethod
                )
            )
        }

        // 15. Umbral Form / Catch Roll (ONLY for wild-catchable / Egg Heist stages where the coat is active)
        if (availability.hasUmbralCoatRoll) {
            forms.add(
                RegionalVariant(
                    id = "${aniimo.id}_umbral",
                    variantName = "Umbral ${aniimo.name}",
                    shortBadge = "Umbral Form",
                    regionName = "Wild Surge (1/3,000 Roll) & Operation: Egg Heist",
                    primaryElement = aniimo.element,
                    secondaryElement = Element.DARK,
                    coatPaletteSummary = "In-game Umbral catch status on ${aniimo.name}! Features a dark cosmic-flame coat rolled on catch (1 in 3,000 odds). Note: Evolving or changing ${aniimo.name}'s form removes this dark coat while keeping its Perfect Innate Potential!",
                    traitAdaptation = "Guaranteed Perfect Innate Potential (6x Max IVs across HP, ATK, BREAK, PDEF, MDEF, REGEN)!",
                    statModifierSummary = "1/3,000 Roll • GUARANTEED PERFECT INNATE POTENTIAL",
                    primaryTint = Color(0xFFF43F5E),
                    secondaryTint = Color(0xFF6366F1),
                    accentTint = Color(0xFFFFE4E6),
                    category = FormCategory.UMBRAL,
                    acquisitionMethod = "1 in 3,000 roll on catch in the wild or Operation: Egg Heist"
                )
            )
        }

        return forms
    }

    /**
     * Species-specific in-game Sparkling palette overrides and descriptions.
     */
    private data class SpeciesSparkleProfile(
        val signatureCoatDesc: String,
        val prismanaFullBodyDesc: String,
        val eyeMarkingDesc: String,
        val baseHueAnchor: Float,
        val signaturePrimary: Color,
        val signatureSecondary: Color,
        val signatureAccent: Color
    )

    private val customSpeciesProfiles: Map<String, SpeciesSparkleProfile> = mapOf(
        "fulmintis" to SpeciesSparkleProfile(
            signatureCoatDesc = "Mantis thorax plates shift from emerald-cyan to Royal Amethyst-Violet with 24k Gold scythe blades",
            prismanaFullBodyDesc = "Sparkling starlight motes stack on top of Fulmintis's active form (Standard or Prismana) with golden scythe glints",
            eyeMarkingDesc = "Diamond-Cyan compound eyes with golden wing-vein lightning",
            baseHueAnchor = 48f,
            signaturePrimary = Color(0xFFA855F7),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFF38BDF8)
        ),
        "wisptis" to SpeciesSparkleProfile(
            signatureCoatDesc = "Nymph antennae and forelegs shift to pastel Lavender-Gold with glowing static tips",
            prismanaFullBodyDesc = "Sparkling particle coat layered over Wisptis's nymph carapace with twin golden arc feelers",
            eyeMarkingDesc = "Luminous Amber-Cyan eyes",
            baseHueAnchor = 44f,
            signaturePrimary = Color(0xFFC084FC),
            signatureSecondary = Color(0xFFFDE047),
            signatureAccent = Color(0xFF38BDF8)
        ),
        "emberpup" to SpeciesSparkleProfile(
            signatureCoatDesc = "Orange-red puppy fur shifts to Azure Blue-Flame with white-gold tail embers",
            prismanaFullBodyDesc = "Sparkling star-motes orbit Emberpup's flame collar across Standard, Snowfield, Prismana, or Umbral forms",
            eyeMarkingDesc = "Sapphire-Blue flame pupils & golden paw pads",
            baseHueAnchor = 145f,
            signaturePrimary = Color(0xFF38BDF8),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFFBAE6FD)
        ),
        "flameruff" to SpeciesSparkleProfile(
            signatureCoatDesc = "Fiery mane and ruff shift to Cobalt Bluefire with silver-white fangs and claws",
            prismanaFullBodyDesc = "Sparkling star-motes shimmer through Flameruff's flame ruff",
            eyeMarkingDesc = "Glowing Cyan-Gold predator eyes",
            baseHueAnchor = 148f,
            signaturePrimary = Color(0xFF0EA5E9),
            signatureSecondary = Color(0xFFFDE047),
            signatureAccent = Color(0xFFE0F2FE)
        ),
        "scorchhowl" to SpeciesSparkleProfile(
            signatureCoatDesc = "Alpha wolf pelt shifts to Silver-Charcoal with blazing Azure Bluefire back-spines",
            prismanaFullBodyDesc = "Exhibition Wall Sparkling coat stacks cleanly with Scorchhowl's Standard, Snowfield, or Prismana form",
            eyeMarkingDesc = "Piercing Blue-Starlight eyes & molten gold claw marks",
            baseHueAnchor = 152f,
            signaturePrimary = Color(0xFF38BDF8),
            signatureSecondary = Color(0xFFF59E0B),
            signatureAccent = Color(0xFFFFFFFF)
        ),
        "inferlupa" to SpeciesSparkleProfile(
            signatureCoatDesc = "Nocturnal dark-fire pelt shifts to Bloodmoon Crimson & Astral Violet",
            prismanaFullBodyDesc = "Sparkling star-motes trace Inferlupa's lunar markings and tail flames",
            eyeMarkingDesc = "Glowing Magenta-Gold lunar markings",
            baseHueAnchor = 195f,
            signaturePrimary = Color(0xFFE879F9),
            signatureSecondary = Color(0xFFEF4444),
            signatureAccent = Color(0xFFF5D0FE)
        ),
        "leafy" to SpeciesSparkleProfile(
            signatureCoatDesc = "Verdant leaf ear-crests and tail shift to Autumn Sakura Pink & Golden Maple",
            prismanaFullBodyDesc = "Sparkling dew-motes shimmer across Leafy's Standard, Sea of Flowers, Prismana, or Umbral form",
            eyeMarkingDesc = "Warm Golden-Rose eyes & shimmering pink leaf veins",
            baseHueAnchor = 155f,
            signaturePrimary = Color(0xFFF472B6),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFFFBCFE8)
        ),
        "budclaw" to SpeciesSparkleProfile(
            signatureCoatDesc = "Vine gauntlets and floral bud shift to Cherry-Blossom Rose with golden thorns",
            prismanaFullBodyDesc = "Sparkling particle coat highlights Budclaw's vine claws across Mountain Woods and Prismana forms",
            eyeMarkingDesc = "Emerald-Gold slit eyes & rose-quartz claws",
            baseHueAnchor = 160f,
            signaturePrimary = Color(0xFFEC4899),
            signatureSecondary = Color(0xFFF59E0B),
            signatureAccent = Color(0xFFFCE7F3)
        ),
        "thornblade" to SpeciesSparkleProfile(
            signatureCoatDesc = "Dual leaf-swords shift to Sakura-Crimson & Platinum White knight foliage",
            prismanaFullBodyDesc = "Stacks Exhibition Wall Sparkling glints onto Thornblade's Standard, Mountain Woods, Sea of Flowers, or Prismana form",
            eyeMarkingDesc = "Radiant Gold visor eyes & crimson petal slash trail",
            baseHueAnchor = 165f,
            signaturePrimary = Color(0xFFFB7185),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFFFFE4E6)
        ),
        "dewlop" to SpeciesSparkleProfile(
            signatureCoatDesc = "Aqua-blue sea bunny ears shift to Coral Rose-Pink & Pearlescent Mint",
            prismanaFullBodyDesc = "Sparkling bubble-stars shimmer across Dewlop's Standard, Tidal, Prismana, or Umbral form",
            eyeMarkingDesc = "Starlight Ruby eyes & glowing pearl collar",
            baseHueAnchor = 115f,
            signaturePrimary = Color(0xFFF472B6),
            signatureSecondary = Color(0xFF2DD4BF),
            signatureAccent = Color(0xFFFDF2F8)
        ),
        "whirlbite" to SpeciesSparkleProfile(
            signatureCoatDesc = "Deep-sea shark hide shifts to Abyssal Crimson-Slate with neon gold dorsal fins",
            prismanaFullBodyDesc = "Sparkling starlight glints trace Whirlbite's extendable jaw and dorsal fin",
            eyeMarkingDesc = "Molten Gold jaws & glowing crimson gill slits",
            baseHueAnchor = 128f,
            signaturePrimary = Color(0xFFEF4444),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFFFCA5A5)
        ),
        "gachapus" to SpeciesSparkleProfile(
            signatureCoatDesc = "Octopus capsule dome and tentacles shift to 24k Golden Jackpot & Emerald Mint",
            prismanaFullBodyDesc = "Sparkling jackpot stars orbit Gachapus's capsule dome across Standard, Tidal, and Prismana forms",
            eyeMarkingDesc = "Flashing Lucky-7 Gold star eyes",
            baseHueAnchor = 135f,
            signaturePrimary = Color(0xFFFACC15),
            signatureSecondary = Color(0xFF10B981),
            signatureAccent = Color(0xFFFEF08A)
        ),
        "irisalis" to SpeciesSparkleProfile(
            signatureCoatDesc = "Empress petal gown shifts to Starlight Violet & Silver-Gold",
            prismanaFullBodyDesc = "Sparkling star-motes illuminate Irisalis's floral clones across Standard, Sea of Flowers, and Prismana forms",
            eyeMarkingDesc = "Luminous Violet-Gold crown & twin phantom glow",
            baseHueAnchor = 130f,
            signaturePrimary = Color(0xFFD8B4FE),
            signatureSecondary = Color(0xFFFACC15),
            signatureAccent = Color(0xFFFAF5FF)
        ),
        "grizbo" to SpeciesSparkleProfile(
            signatureCoatDesc = "Mountain bear fur shifts to Silver-Alabaster with Golden Quartz shoulder crystals",
            prismanaFullBodyDesc = "Sparkling starlight glints enhance Grizbo's Standard, Highland, or Prismana form",
            eyeMarkingDesc = "Blazing Ruby-Gold berserker eyes & glowing crystal claws",
            baseHueAnchor = 85f,
            signaturePrimary = Color(0xFFFACC15),
            signatureSecondary = Color(0xFF34D399),
            signatureAccent = Color(0xFFFEF9C3)
        ),
        "malevsera" to SpeciesSparkleProfile(
            signatureCoatDesc = "Frost-scythe wings and dress shift to Blood-Rose Crimson & Obsidian Ice",
            prismanaFullBodyDesc = "Sparkling ice-stars shimmer around Malevsera's Standard, Snowfield, or Prismana form",
            eyeMarkingDesc = "Piercing Crimson-Gold demonic state eyes",
            baseHueAnchor = 140f,
            signaturePrimary = Color(0xFFF43F5E),
            signatureSecondary = Color(0xFFE2E8F0),
            signatureAccent = Color(0xFFFFE4E6)
        )
    )

    /**
     * Computes the exact in-game Sparkling appearance metadata for a specific Aniimo + SparkleType
     * (+ optional active Form).
     */
    fun getSpeciesSparkleLook(
        aniimo: Aniimo,
        sparkleType: SparkleType,
        regionalVariant: RegionalVariant? = null
    ): SpeciesSparkleLook {
        val idKey = aniimo.id.lowercase()
        val custom = customSpeciesProfiles[idKey]

        val elementBasedAnchor = when (aniimo.element) {
            Element.FIRE -> 145f
            Element.WATER -> 120f
            Element.GRASS -> 155f
            Element.LIGHTNING -> 48f
            Element.ICE -> 135f
            Element.EARTH -> 85f
            Element.LIGHT -> 140f
            Element.DARK -> 110f
            Element.WIND -> 130f
            else -> 120f
        }

        val speciesAnchor = custom?.baseHueAnchor ?: elementBasedAnchor
        val variantPrefix = if (regionalVariant != null && !regionalVariant.isNativeDefault) {
            "${regionalVariant.shortBadge} "
        } else {
            ""
        }

        val inGameTitle = when (sparkleType.id) {
            "prototype" -> "Sparkling Prototype • $variantPrefix${aniimo.name}"
            "dazzling" -> "Dazzling Sparkling • $variantPrefix${aniimo.name}"
            "shadow" -> "Shadow Sparkling • $variantPrefix${aniimo.name}"
            else -> "Sparkling ${sparkleType.code} • $variantPrefix${aniimo.name}"
        }

        val baseCoatLook = when (sparkleType.id) {
            "prototype" -> "Preserves ${aniimo.name}'s exact in-game ${regionalVariant?.shortBadge ?: "Standard"} coat colors while adding the glittering Sparkling star-mote & prism-glint coat."
            "dazzling" -> "Hidden Dazzling Sparkling coat (1% Normal Egg Cube / 0.5% Sigil): bright champagne-gold & diamond-white sheen across ${aniimo.name}."
            "shadow" -> "Hidden Shadow Sparkling coat (1% Egg Heist Cube / 0.5% Sigil): dark obsidian-indigo coat with abyssal violet sparkles across ${aniimo.name}."
            "type_1" -> custom?.signatureCoatDesc
                ?: "${aniimo.name}'s primary ${aniimo.element.displayName} coat shifts to ${sparkleType.name} while preserving natural eye & outline contrast."
            else -> custom?.let { "${it.signatureCoatDesc} (${sparkleType.code}: ${sparkleType.coatEffect})" }
                ?: "${aniimo.name} exhibits ${sparkleType.coatEffect} with ${sparkleType.auraEffect}."
        }

        val prismanaLook = custom?.prismanaFullBodyDesc
            ?: "In-game Sparkling is an independent cosmetic axis (1/20,000 wild) that stacks on top of ${aniimo.name}'s ${regionalVariant?.shortBadge ?: "Standard Form"}."

        val eyeGlow = custom?.eyeMarkingDesc
            ?: "Natural 3D eye clarity preserved with ${sparkleType.code} starlight motes"

        return SpeciesSparkleLook(
            aniimoId = aniimo.id,
            sparkleTypeId = sparkleType.id,
            inGameTitle = inGameTitle,
            baseCoatLook = baseCoatLook,
            prismanaBodyLook = prismanaLook,
            eyeAndMarkingGlow = eyeGlow,
            signatureColorPrimary = if (sparkleType.id == "type_1" && custom != null) custom.signaturePrimary else sparkleType.primaryColor,
            signatureColorSecondary = if (sparkleType.id == "type_1" && custom != null) custom.signatureSecondary else sparkleType.secondaryColor,
            signatureColorAccent = if (sparkleType.id == "type_1" && custom != null) custom.signatureAccent else sparkleType.accentColor,
            speciesHueAnchor = speciesAnchor
        )
    }

    /**
     * Builds the species-aware ColorMatrix for rendering the Aniimo's authentic in-game
     * Sparkling look without distorting facial features or natural 3D shading.
     */
    fun createInGameSparkleColorMatrix(
        aniimo: Aniimo,
        sparkleType: SparkleType,
        regionalVariant: RegionalVariant? = null,
        isPrismanaFullBodyShift: Boolean = true
    ): ColorMatrix {
        val variantShift = if (regionalVariant != null && !regionalVariant.isNativeDefault) {
            regionalVariant.hueShift * 0.45f
        } else {
            0f
        }

        // Sparkling Prototype keeps natural colors (or just the active Regional/Prismana form tint)
        if (sparkleType.id == "prototype") {
            return regionalVariant?.toVariantColorMatrix() ?: ColorMatrix()
        }

        val look = getSpeciesSparkleLook(aniimo, sparkleType, regionalVariant)
        val effectiveOffset = (look.speciesHueAnchor * 0.30f) + variantShift
        val rawMatrix = sparkleType.createColorMatrix(customHueOffset = effectiveOffset)

        if (isPrismanaFullBodyShift) {
            return rawMatrix
        }

        // Subtle Accent-Only Sparkling mode: keeps 75% natural coat + 25% Sparkling tint
        val values = rawMatrix.values
        val blended = floatArrayOf(
            0.72f + values[0] * 0.28f, values[1] * 0.28f,         values[2] * 0.28f,         0f, values[4] * 0.35f,
            values[5] * 0.28f,         0.72f + values[6] * 0.28f, values[7] * 0.28f,         0f, values[9] * 0.35f,
            values[10] * 0.28f,        values[11] * 0.28f,        0.72f + values[12] * 0.28f, 0f, values[14] * 0.35f,
            0f,                        0f,                        0f,                        1f, 0f
        )
        return ColorMatrix(blended)
    }
}
