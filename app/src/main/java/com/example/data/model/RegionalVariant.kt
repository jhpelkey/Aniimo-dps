package com.example.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix

/**
 * Official Form & Variant Categories from the game Aniimo (Pawprint Studio / aniimotools.dev / aniimoguide.com):
 * - STANDARD: The creature's native base form.
 * - HIGHLAND: Regional form shaped by evolving/spawning in the Russet Highlands (e.g., Chirpi Wind/Grass, Grizbo, Scorchhowl, Bailite).
 * - MOUNTAIN_WOODS: Regional form tied to Mountain Woods & Timberline forest biomes (e.g., Thornblade, Geoclaw, Pawney, Rookey, Helgon).
 * - SNOWFIELD: Weather/biome form found in Snowfields & Glacial Spire (e.g., Skippy, Pranky, Glacy, Waleetle, Malevsera, Wavwal).
 * - SEA_OF_FLOWERS: Floral/Flowerfield adaptation native to the Sea of Flowers (e.g., Irisalis, Melloblum, Flamerion, Gracewing, Witchin).
 * - BEACH: Coastal/Beach adaptation (e.g., Chirpi, Tromber, Cornet, Tubster, Shelly, Sheldon, Piopiota).
 * - RAINFALL: Weather-shifted form during Rainfall (e.g., Nimbi Water/Wind, Luminelle, Turbo, Stellarys, Coraliz, Jabster).
 * - TEMPEST: Whisperwake Isles storm weather form (e.g., Glameep Grass/Wind, Jeglid & Jeburst Wind/Water).
 * - WATERFALL: Cloudreach Falls / Waterfall cascade form (e.g., Dewlop, Dewple, Erlath).
 * - NIGHTTIME: Nocturnal / Lunar form (e.g., Panpanta, Bonesky, Fenrier, Glynsera, Mesmerowl).
 * - CLOUDMIST: High-altitude Cloudmist weather form (e.g., Nimbi Cloudmist Form).
 * - PLATEAU: Arid Plateau regional form (e.g., Nimbi Plateau Form).
 * - TIDAL_WETLAND: Pelagic/wetland adaptation found in Whisperwake Isles reefs (e.g., Whirlbite, Gachapus, Scrubbit).
 * - PRISMANA: Rainbow-rare form tier obtainable via Prismana Pact, Prismana Flow, Vein Abundance, Egg Hatching, or Trainer Challenges.
 * - UMBRAL: 1-in-3,000 Wild Surge / Egg Heist catch roll guaranteeing Perfect Innate Potential (6x Max IVs).
 */
enum class FormCategory(
    val id: String,
    val displayName: String,
    val shortBadge: String,
    val badgeColorHex: Long
) {
    STANDARD("standard", "Standard Base Form", "Standard Form", 0xFF38BDF8),
    HIGHLAND("highland", "Highland Regional Form", "Highland Form", 0xFFF59E0B),
    MOUNTAIN_WOODS("mountain_woods", "Mountain Woods Form", "Mountain Woods", 0xFF10B981),
    SNOWFIELD("snowfield", "Snowfield Weather Form", "Snowfield Form", 0xFF67E8F9),
    SEA_OF_FLOWERS("sea_of_flowers", "Flowerfield / Sea of Flowers Form", "Flowerfield Form", 0xFFF472B6),
    BEACH("beach", "Beach / Coastal Form", "Beach Form", 0xFF38BDF8),
    RAINFALL("rainfall", "Rainfall Weather Form", "Rainfall Form", 0xFF60A5FA),
    TEMPEST("tempest", "Tempest Storm Form", "Tempest Form", 0xFF34D399),
    WATERFALL("waterfall", "Waterfall Cascade Form", "Waterfall Form", 0xFF22D3EE),
    NIGHTTIME("nighttime", "Nighttime Lunar Form", "Nighttime Form", 0xFF818CF8),
    CLOUDMIST("cloudmist", "Cloudmist Weather Form", "Cloudmist Form", 0xFFE2E8F0),
    PLATEAU("plateau", "Plateau Regional Form", "Plateau Form", 0xFFFB923C),
    TIDAL_WETLAND("tidal_wetland", "Tidal Wetland Form", "Tidal Form", 0xFF06B6D4),
    PRISMANA("prismana", "Prismana Form (Rainbow Tier)", "Prismana Form", 0xFFA855F7),
    UMBRAL("umbral", "Umbral Roll (Perfect Potential)", "Umbral Form", 0xFFF43F5E)
}

/**
 * Per-species summary of which verified in-game forms and special rolls an Aniimo actually possesses.
 */
data class SpeciesFormAvailability(
    val aniimoId: String,
    val hasRegionalForm: Boolean,
    val regionalFormCategories: List<FormCategory>,
    val hasPrismanaForm: Boolean,
    val prismanaSourceSummary: String,
    val hasUmbralCoatRoll: Boolean,
    val umbralMechanicsNote: String
) {
    val hasHighlandForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.HIGHLAND)
    val hasMountainWoodsForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.MOUNTAIN_WOODS)
    val hasSnowfieldForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.SNOWFIELD)
    val hasSeaOfFlowersForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.SEA_OF_FLOWERS)
    val hasBeachForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.BEACH)
    val hasRainfallForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.RAINFALL)
    val hasTempestForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.TEMPEST)
    val hasWaterfallForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.WATERFALL)
    val hasNighttimeForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.NIGHTTIME)
    val hasCloudmistForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.CLOUDMIST)
    val hasPlateauForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.PLATEAU)
    val hasTidalWetlandForm: Boolean
        get() = regionalFormCategories.contains(FormCategory.TIDAL_WETLAND)
    val hasUmbralForm: Boolean
        get() = hasUmbralCoatRoll
    val evolutionStripsUmbralCoat: Boolean
        get() = !hasUmbralCoatRoll

    val regionalSummaryBadge: String
        get() = if (regionalFormCategories.isEmpty()) {
            "Standard Form Only (No Regional Variant)"
        } else {
            regionalFormCategories.joinToString(" • ") { it.shortBadge }
        }
}

/**
 * Represents an authentic in-game Form or Variant that a specific Aniimo species possesses.
 */
data class RegionalVariant(
    val id: String,
    val variantName: String,
    val shortBadge: String,
    val regionName: String,
    val primaryElement: Element,
    val secondaryElement: Element?,
    val coatPaletteSummary: String,
    val traitAdaptation: String,
    val statModifierSummary: String,
    val primaryTint: Color,
    val secondaryTint: Color,
    val accentTint: Color,
    val hueShift: Float = 0f,
    val saturationMultiplier: Float = 1f,
    val isNativeDefault: Boolean = false,
    val category: FormCategory = FormCategory.STANDARD,
    val acquisitionMethod: String = "Standard Wild Encounter / Evolution"
) {
    fun toVariantColorMatrix(): ColorMatrix = ColorMatrix()
}

/**
 * Legacy data model retained for test compatibility.
 */
data class SpeciesSparkleLook(
    val aniimoId: String,
    val sparkleTypeId: String,
    val inGameTitle: String,
    val baseCoatLook: String,
    val prismanaBodyLook: String,
    val eyeAndMarkingGlow: String,
    val signatureColorPrimary: Color,
    val signatureColorSecondary: Color,
    val signatureColorAccent: Color,
    val speciesHueAnchor: Float
)
