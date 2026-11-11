package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Aniimo
import com.example.data.model.FormCategory
import com.example.data.repository.SparkleRepository
import com.example.data.repository.formAvailability
import com.example.data.repository.regionalVariants
import com.example.ui.components.AniimoElementBadge
import com.example.ui.components.AniimoFaceAvatar
import com.example.ui.components.SparkleAniimoAvatar

enum class SparkleCategoryFilter(val label: String) {
    ALL("All 13 Sparkles"),
    NUMBERED("Prototype + Types I–X"),
    SPECIAL("Hidden (Dazzling & Shadow)")
}

enum class SpeciesFormFilter(val label: String) {
    ALL("All 75 Aniimo"),
    HIGHLAND("Highland Form (15)"),
    MOUNTAIN_WOODS("Mountain Woods (10)"),
    SNOWFIELD("Snowfield Form (11)"),
    SEA_OF_FLOWERS("Sea of Flowers (11)"),
    TIDAL("Tidal Form (8)"),
    HAS_PRISMANA("Has Prismana (37)"),
    NO_PRISMANA("No Prismana (38)"),
    UMBRAL_COAT("Umbral Coat Roll"),
    STANDARD_ONLY("Standard Form Only")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SparkleShowcaseScreen(
    aniimos: List<Aniimo>,
    initialSelectedAniimoId: String? = null,
    onNavigateToDetail: ((Aniimo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedSpeciesFormFilter by remember { mutableStateOf(SpeciesFormFilter.ALL) }

    val filteredAniimos = remember(aniimos, selectedSpeciesFormFilter) {
        aniimos.filter { aniimo ->
            val avail = aniimo.formAvailability
            when (selectedSpeciesFormFilter) {
                SpeciesFormFilter.ALL -> true
                SpeciesFormFilter.HIGHLAND -> avail.regionalFormCategories.contains(FormCategory.HIGHLAND)
                SpeciesFormFilter.MOUNTAIN_WOODS -> avail.regionalFormCategories.contains(FormCategory.MOUNTAIN_WOODS)
                SpeciesFormFilter.SNOWFIELD -> avail.regionalFormCategories.contains(FormCategory.SNOWFIELD)
                SpeciesFormFilter.SEA_OF_FLOWERS -> avail.regionalFormCategories.contains(FormCategory.SEA_OF_FLOWERS)
                SpeciesFormFilter.TIDAL -> avail.regionalFormCategories.contains(FormCategory.TIDAL_WETLAND)
                SpeciesFormFilter.HAS_PRISMANA -> avail.hasPrismanaForm
                SpeciesFormFilter.NO_PRISMANA -> !avail.hasPrismanaForm
                SpeciesFormFilter.UMBRAL_COAT -> avail.hasUmbralCoatRoll
                SpeciesFormFilter.STANDARD_ONLY -> !avail.hasRegionalForm && !avail.hasPrismanaForm
            }
        }.ifEmpty { aniimos }
    }

    var selectedAniimo by remember(aniimos, initialSelectedAniimoId) {
        val initial = if (initialSelectedAniimoId != null) {
            aniimos.find { it.id == initialSelectedAniimoId } ?: aniimos.firstOrNull()
        } else {
            aniimos.firstOrNull()
        }
        mutableStateOf(initial)
    }

    // Keep selectedAniimo inside filteredAniimos if filter changes
    val currentAniimo = remember(selectedAniimo, filteredAniimos) {
        if (selectedAniimo != null && filteredAniimos.any { it.id == selectedAniimo!!.id }) {
            selectedAniimo
        } else {
            filteredAniimos.firstOrNull() ?: selectedAniimo
        }
    } ?: return

    var selectedSparkle by remember { mutableStateOf(SparkleRepository.defaultSparkle) }
    var selectedCategoryFilter by remember { mutableStateOf(SparkleCategoryFilter.ALL) }
    var isPrismanaFullBodyShift by remember { mutableStateOf(true) }

    val regionalVariants = remember(currentAniimo.id) { currentAniimo.regionalVariants }
    val formAvailability = remember(currentAniimo.id) { currentAniimo.formAvailability }
    var selectedVariant by remember(currentAniimo.id) {
        mutableStateOf(regionalVariants.first())
    }

    val speciesLook = remember(currentAniimo.id, selectedSparkle.id, selectedVariant.id) {
        SparkleRepository.getSpeciesSparkleLook(currentAniimo, selectedSparkle, selectedVariant)
    }

    val filteredSparkles = remember(selectedCategoryFilter) {
        when (selectedCategoryFilter) {
            SparkleCategoryFilter.ALL -> SparkleRepository.allSparkleTypes
            SparkleCategoryFilter.NUMBERED -> SparkleRepository.allSparkleTypes.filter { !it.isSpecial }
            SparkleCategoryFilter.SPECIAL -> SparkleRepository.allSparkleTypes.filter { it.isSpecial }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("sparkle_showcase_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Sparkle Icon",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sparkles & In-Game Forms",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x33FFD700))
                        .border(1.dp, Color(0x66FFD700), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Exhibition Wall",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Authentic Exhibition Wall Sparkling variations (Prototype, Types I–X, Dazzling, Shadow) & true species-specific Forms (Highland, Mountain Woods, Snowfield, Sea of Flowers, 37 Prismana, and Umbral 1/3,000 Perfect Potential).",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Species by In-Game Form Eligibility
        Text(
            text = "FILTER ROSTER BY IN-GAME FORM ELIGIBILITY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF38BDF8),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SpeciesFormFilter.entries.forEach { formFilter ->
                val isSelected = selectedSpeciesFormFilter == formFilter
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedSpeciesFormFilter = formFilter
                    },
                    label = {
                        Text(
                            text = formFilter.label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1E293B),
                        selectedLabelColor = Color(0xFF38BDF8),
                        containerColor = Color(0xFF0F172A),
                        labelColor = Color(0xFF94A3B8)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = Color(0xFF334155),
                        selectedBorderColor = Color(0xFF38BDF8)
                    ),
                    modifier = Modifier.testTag("species_form_filter_${formFilter.name.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Aniimo Picker Ribbon
        Text(
            text = "SELECT ANIIMO (${filteredAniimos.size} MATCHING)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredAniimos.forEach { aniimo ->
                val isSelected = aniimo.id == currentAniimo.id
                val avail = aniimo.formAvailability
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A))
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF334155),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedAniimo = aniimo }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("sparkle_select_aniimo_${aniimo.id}")
                ) {
                    AniimoFaceAvatar(
                        aniimo = aniimo,
                        size = 46.dp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = aniimo.name,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AniimoElementBadge(
                            element = aniimo.element,
                            compact = true
                        )
                        aniimo.secondaryElement?.let { sec ->
                            AniimoElementBadge(
                                element = sec,
                                compact = true
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = when {
                            avail.regionalFormCategories.isNotEmpty() && avail.hasPrismanaForm ->
                                "${avail.regionalFormCategories.first().shortBadge} + Prismana"
                            avail.regionalFormCategories.isNotEmpty() ->
                                avail.regionalFormCategories.first().shortBadge
                            avail.hasPrismanaForm -> "Prismana Eligible"
                            else -> "Standard Only"
                        },
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (avail.hasPrismanaForm) Color(0xFFD8B4FE) else Color(0xFF64748B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Species-Specific In-Game Forms & Eligibility Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("species_form_eligibility_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D33)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentAniimo.name.uppercase()} IN-GAME FORMS (${regionalVariants.size} AVAILABLE)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF38BDF8)
                    )
                    Text(
                        text = if (formAvailability.hasPrismanaForm) "✓ Prismana Eligible" else "✗ No Prismana Form",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (formAvailability.hasPrismanaForm) Color(0xFFD8B4FE) else Color(0xFF94A3B8)
                    )
                }

                // Form selector chips ONLY for the forms this Aniimo actually has
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    regionalVariants.forEach { variant ->
                        val isVarSelected = variant.id == selectedVariant.id
                        FilterChip(
                            selected = isVarSelected,
                            onClick = { selectedVariant = variant },
                            label = {
                                Text(
                                    text = variant.shortBadge,
                                    fontSize = 11.sp,
                                    fontWeight = if (isVarSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = variant.primaryTint.copy(alpha = 0.22f),
                                selectedLabelColor = variant.accentTint,
                                containerColor = Color(0xFF0F172A),
                                labelColor = Color(0xFF94A3B8)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isVarSelected,
                                borderColor = Color(0xFF334155),
                                selectedBorderColor = variant.primaryTint
                            ),
                            modifier = Modifier.testTag("sparkle_regional_variant_${variant.id}")
                        )
                    }
                }

                // Species Form Breakdown Badges
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F172A))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "• Regional Biome Form: ${formAvailability.regionalSummaryBadge}",
                        fontSize = 11.sp,
                        color = if (formAvailability.hasRegionalForm) Color(0xFFFDE68A) else Color(0xFF94A3B8),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "• Prismana Form: ${formAvailability.prismanaSourceSummary}",
                        fontSize = 11.sp,
                        color = if (formAvailability.hasPrismanaForm) Color(0xFFD8B4FE) else Color(0xFF94A3B8)
                    )
                    Text(
                        text = "• Umbral Mechanics: ${formAvailability.umbralMechanicsNote}",
                        fontSize = 10.sp,
                        color = Color(0xFFFCA5A5),
                        lineHeight = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Featured Sparkle Stage Card (Side-by-Side In-Game Comparison + Authentic Species Look)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("sparkle_featured_stage"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                Brush.linearGradient(
                    listOf(speciesLook.signatureColorPrimary, speciesLook.signatureColorSecondary)
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Badge Bar in Stage
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(speciesLook.signatureColorPrimary.copy(alpha = 0.2f))
                            .border(1.dp, speciesLook.signatureColorPrimary.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = selectedSparkle.badgeLabel,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = speciesLook.signatureColorAccent
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Odds: ",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                        Text(
                            text = selectedSparkle.odds,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF1F5F9)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Side-by-Side Comparison: Non-Sparkling Form vs. Exhibition Wall Sparkling Look
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Non-Sparkling Form Model
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AniimoFaceAvatar(
                            aniimo = currentAniimo,
                            size = 76.dp,
                            regionalVariant = selectedVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Normal (${selectedVariant.shortBadge})",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "➔",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = speciesLook.signatureColorAccent
                    )

                    // Authentic In-Game Exhibition Wall Sparkling Model
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SparkleAniimoAvatar(
                            aniimo = currentAniimo,
                            sparkleType = selectedSparkle,
                            regionalVariant = selectedVariant,
                            isPrismanaFullBodyShift = isPrismanaFullBodyShift,
                            size = 104.dp,
                            showAura = true,
                            animateGlow = true
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Sparkling (${selectedSparkle.code})",
                            fontSize = 11.sp,
                            color = speciesLook.signatureColorAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle between Exhibition Wall Coat Shift vs Subtle Accent + Star-Motes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF131D33))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isPrismanaFullBodyShift) speciesLook.signatureColorPrimary.copy(alpha = 0.25f)
                                else Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = if (isPrismanaFullBodyShift) speciesLook.signatureColorPrimary else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { isPrismanaFullBodyShift = true }
                            .padding(vertical = 8.dp)
                            .testTag("toggle_sparkle_prismana_shift"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Exhibition Coat Palette",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isPrismanaFullBodyShift) Color.White else Color(0xFF94A3B8)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (!isPrismanaFullBodyShift) speciesLook.signatureColorPrimary.copy(alpha = 0.25f)
                                else Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = if (!isPrismanaFullBodyShift) speciesLook.signatureColorPrimary else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { isPrismanaFullBodyShift = false }
                            .padding(vertical = 8.dp)
                            .testTag("toggle_sparkle_base_aura"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Natural Coat + Sparkle Motes",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isPrismanaFullBodyShift) Color.White else Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title and Species-Specific In-Game Sparkling Name
                Text(
                    text = "${selectedVariant.variantName} • ${selectedSparkle.code}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = speciesLook.inGameTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = speciesLook.signatureColorAccent
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Authentic Species In-Game Sparkling Coat & Acquisition Breakdown Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF131D33))
                        .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "EXHIBITION WALL & ACQUISITION ODDS (${selectedSparkle.code.uppercase()})",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = speciesLook.signatureColorPrimary
                    )
                    Text(
                        text = "• Coat Look: ${speciesLook.baseCoatLook}",
                        fontSize = 12.sp,
                        color = Color(0xFFF1F5F9),
                        lineHeight = 16.sp
                    )
                    Text(
                        text = "• Active Form (${selectedVariant.shortBadge}): ${selectedVariant.coatPaletteSummary}",
                        fontSize = 11.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 15.sp
                    )
                    Text(
                        text = "• How to Obtain Form: ${selectedVariant.acquisitionMethod}",
                        fontSize = 11.sp,
                        color = Color(0xFF34D399),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "• Sparkling Cube Rates: Normal Egg (${selectedSparkle.cubeNormalOdds}) • Egg Heist (${selectedSparkle.cubeEggHeistOdds}) • Sigil/Pigment (${selectedSparkle.sigilPigmentOdds})",
                        fontSize = 10.sp,
                        color = Color(0xFFFDE68A),
                        lineHeight = 14.sp
                    )
                }

                if (onNavigateToDetail != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigateToDetail(currentAniimo) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sparkle_view_build_guide_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "View ${currentAniimo.name} Forms & Build Guide",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Sparkle Category Filters
        Text(
            text = "ALL 13 EXHIBITION WALL SPARKLING VARIATIONS (${currentAniimo.name.uppercase()})",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SparkleCategoryFilter.values().forEach { filter ->
                val isSelected = selectedCategoryFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategoryFilter = filter },
                    label = {
                        Text(
                            text = filter.label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1E293B),
                        selectedLabelColor = Color(0xFF38BDF8),
                        containerColor = Color(0xFF0F172A),
                        labelColor = Color(0xFF94A3B8)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) Color(0xFF38BDF8) else Color(0xFF334155),
                        selectedBorderColor = Color(0xFF38BDF8)
                    ),
                    modifier = Modifier.testTag("filter_chip_${filter.name.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sparkles List / Grid Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredSparkles.forEach { sparkle ->
                val isSelected = sparkle.id == selectedSparkle.id
                val itemSpeciesLook = SparkleRepository.getSpeciesSparkleLook(currentAniimo, sparkle, selectedVariant)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedSparkle = sparkle }
                        .testTag("sparkle_card_${sparkle.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFF1E293B) else Color(0xFF0F172A)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) itemSpeciesLook.signatureColorPrimary else Color(0xFF1E293B)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SparkleAniimoAvatar(
                            aniimo = currentAniimo,
                            sparkleType = sparkle,
                            regionalVariant = selectedVariant,
                            isPrismanaFullBodyShift = isPrismanaFullBodyShift,
                            size = 54.dp,
                            showAura = false,
                            animateGlow = isSelected
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${sparkle.code} • ${sparkle.name}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else Color(0xFFF1F5F9)
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(itemSpeciesLook.signatureColorPrimary.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = sparkle.odds,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = itemSpeciesLook.signatureColorAccent
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = itemSpeciesLook.inGameTitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = itemSpeciesLook.signatureColorPrimary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = itemSpeciesLook.baseCoatLook,
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                maxLines = 2,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // In-Game Forms & Sparkling Reference Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1F2937))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "In-Game Forms, Prismana, Umbral & Sparkling Rules",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Species-Specific Regional Forms: Only specific species have Highland Form (15), Mountain Woods Form (10), Snowfield Form (11), Sea of Flowers Form (11), or Tidal Form (8).\n" +
                           "• Prismana Form (37 Species Only): Only 37 Aniimo have a rainbow-tier Prismana Form via Prismana Flow, Nurture, or Prismana Pact—the other 38 do not.\n" +
                           "• Umbral Roll (1 in 3,000): Wild Surge / Egg Heist catch status that guarantees Perfect Innate Potential (6x Max IVs). Evolving or changing form removes the dark Umbral coat while keeping Perfect Potential!\n" +
                           "• 13 Exhibition Wall Sparkles: Sparkling Prototype + Types I–X (99% Cube pool) + Dazzling (1% Normal Egg Cube) + Shadow (1% Egg Heist Cube).",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8),
                    lineHeight = 16.sp
                )
            }
        }
    }
}
