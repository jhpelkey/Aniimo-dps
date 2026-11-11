package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.Element
import com.example.ui.components.AniimoCard
import com.example.viewmodel.AniimoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RosterScreen(
    aniimos: List<Aniimo>,
    selectedElement: Element?,
    selectedStageFilter: AniimoViewModel.StageFilter = AniimoViewModel.StageFilter.ALL,
    selectedRegionFilter: com.example.data.model.GameRegion = com.example.data.model.GameRegion.ALL,
    selectedSortOption: AniimoViewModel.AniimoSortOption = AniimoViewModel.AniimoSortOption.NUMBER,
    searchQuery: String,
    onSelectElement: (Element?) -> Unit,
    onSelectStageFilter: (AniimoViewModel.StageFilter) -> Unit = {},
    onSelectRegionFilter: (com.example.data.model.GameRegion) -> Unit = {},
    onSelectSortOption: (AniimoViewModel.AniimoSortOption) -> Unit = {},
    onSearchQueryChange: (String) -> Unit,
    onAniimoClick: (Aniimo) -> Unit,
    onNavigateToSparkles: (() -> Unit)? = null,
    communityVotes: Map<String, com.example.data.local.CommunityTierVoteEntity> = emptyMap(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E293B),
                            Color(0xFF0A0F1D)
                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Aniimo Aniilog Database",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "All Aniimos • Pre-Evolutions • BiS Builds",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${aniimos.size} Listed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_aniimo_input"),
                    placeholder = { Text("Search by name, #No, pre-evo, or skill...", color = Color(0xFF64748B)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF94A3B8)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF94A3B8))
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color(0xFF131D33),
                        unfocusedContainerColor = Color(0xFF131D33),
                        focusedBorderColor = Color(0xFF38BDF8),
                        unfocusedBorderColor = Color(0xFF1E293B)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Game Region Filter Chips Row (Breezy Plains 195 forms vs Whisperwake Isles 42/54 forms)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Region:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(com.example.data.model.GameRegion.values()) { region ->
                            val isSelected = selectedRegionFilter == region
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectRegionFilter(region) },
                                label = {
                                    Text(
                                        when (region) {
                                            com.example.data.model.GameRegion.ALL -> "All Zones (237+)"
                                            com.example.data.model.GameRegion.BREEZY_PLAINS -> "🌿 Breezy Plains (195)"
                                            com.example.data.model.GameRegion.WHISPERWAKE_ISLES -> "🌊 Whisperwake (42 / 54)"
                                        }
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = when (region) {
                                        com.example.data.model.GameRegion.ALL -> Color(0xFF38BDF8)
                                        com.example.data.model.GameRegion.BREEZY_PLAINS -> Color(0xFF10B981)
                                        com.example.data.model.GameRegion.WHISPERWAKE_ISLES -> Color(0xFF06B6D4)
                                    },
                                    selectedLabelColor = Color(0xFF0F172A),
                                    containerColor = Color(0xFF131D33),
                                    labelColor = Color(0xFFCBD5E1)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = Color(0xFF1E293B),
                                    selectedBorderColor = Color(0xFF38BDF8)
                                ),
                                modifier = Modifier.testTag("region_filter_${region.id}")
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Evolution Stage Filter Chips Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AniimoViewModel.StageFilter.values()) { filter ->
                        FilterChip(
                            selected = selectedStageFilter == filter,
                            onClick = { onSelectStageFilter(filter) },
                            label = { Text(filter.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = when (filter) {
                                    AniimoViewModel.StageFilter.ALL -> Color(0xFF38BDF8)
                                    AniimoViewModel.StageFilter.PRE_EVOS -> Color(0xFF14B8A6)
                                    AniimoViewModel.StageFilter.FINAL_EVOS -> Color(0xFFF59E0B)
                                    AniimoViewModel.StageFilter.STANDALONE -> Color(0xFF818CF8)
                                },
                                selectedLabelColor = Color(0xFF0F172A),
                                containerColor = Color(0xFF131D33),
                                labelColor = Color(0xFF94A3B8)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedStageFilter == filter,
                                borderColor = Color(0xFF1E293B),
                                selectedBorderColor = Color(0xFF38BDF8)
                            ),
                            modifier = Modifier.testTag("stage_filter_${filter.name.lowercase()}")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Element Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedElement == null,
                            onClick = { onSelectElement(null) },
                            label = { Text("All Elements") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF38BDF8),
                                selectedLabelColor = Color(0xFF0F172A),
                                containerColor = Color(0xFF131D33),
                                labelColor = Color(0xFF94A3B8)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedElement == null,
                                borderColor = Color(0xFF1E293B),
                                selectedBorderColor = Color(0xFF38BDF8)
                            ),
                            modifier = Modifier.testTag("element_filter_all")
                        )
                    }

                    items(Element.values()) { element ->
                        FilterChip(
                            selected = selectedElement == element,
                            onClick = { onSelectElement(element) },
                            label = { Text(element.displayName) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(element.primaryColor)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = element.primaryColor,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFF131D33),
                                labelColor = Color(0xFF94A3B8)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedElement == element,
                                borderColor = Color(0xFF1E293B),
                                selectedBorderColor = element.primaryColor
                            ),
                            modifier = Modifier.testTag("element_filter_${element.name.lowercase()}")
                        )
                    }
                }
            }
        }

        // Sort Control Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .border(width = 0.5.dp, color = Color(0xFF1E293B))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = "Sort By",
                    tint = Color(0xFF38BDF8),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sort:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8)
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(AniimoViewModel.AniimoSortOption.values()) { option ->
                    val isSelected = selectedSortOption == option
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) Color(0xFF38BDF8).copy(alpha = 0.2f) else Color(0xFF131D33)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFF38BDF8) else Color(0xFF1E293B),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectSortOption(option) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("sort_option_${option.name.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isSelected) {
                                Text(
                                    text = "✓ ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF38BDF8)
                                )
                            }
                            Text(
                                text = option.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color(0xFF38BDF8) else Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }
        }

        // Aniimo Roster List
        if (aniimos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No Aniimos Found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try switching stage or sort filters (e.g. All Forms or Aniimo #) or resetting element filter.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("aniimo_list"),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(aniimos, key = { it.id }) { aniimo ->
                    AniimoCard(
                        aniimo = aniimo,
                        onClick = { onAniimoClick(aniimo) },
                        communityVote = communityVotes[aniimo.id]
                    )
                }
            }
        }
    }
}
