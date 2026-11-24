package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.ServiceCategory
import com.example.ui.components.SecondaryButton
import com.example.ui.components.SectionHeader
import com.example.ui.components.ServiceCard
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServicesScreen(
    uiState: BellaUiState,
    onSelectCategory: (ServiceCategory) -> Unit,
    onResetFilters: () -> Unit,
    onServiceClick: (String) -> Unit,
    onBookServiceClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredServices = uiState.filteredServices

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BellaIvory)
            .statusBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Text(
                        text = stringResource(R.string.services_eyebrow),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.5.sp,
                            letterSpacing = 1.6.sp,
                            color = BellaMuted
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.services_title),
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = CormorantGaramondFamily,
                            fontSize = 33.sp,
                            color = BellaInk
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.services_subtitle),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.5.sp,
                            color = BellaMuted
                        )
                    )
                }
            }

            // Category Filter Chips (FlowRow matching Figma 2-row layout)
            item {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ServiceCategory.entries.forEach { category ->
                        val isSelected = uiState.selectedCategory == category
                        val bgColor by animateColorAsState(
                            targetValue = if (isSelected) BellaRose else BellaWhite,
                            label = "chipBg"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) BellaWhite else BellaInk,
                            label = "chipText"
                        )

                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = bgColor,
                            border = BorderStroke(1.dp, if (isSelected) BellaRose else BellaBorder),
                            modifier = Modifier
                                .clip(RoundedCornerShape(22.dp))
                                .clickable { onSelectCategory(category) }
                                .testTag("filter_chip_${category.name}")
                        ) {
                            Text(
                                text = category.label,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                                    color = textColor
                                ),
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }

            item {
                SectionHeader(
                    title = stringResource(R.string.services_section_title),
                    trailingCaption = "${filteredServices.size} xizmat",
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
            }

            if (filteredServices.isEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = BellaWhite,
                        border = BorderStroke(1.dp, BellaBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Mos xizmatlar topilmadi",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = CormorantGaramondFamily,
                                    color = BellaInk
                                )
                            )
                            Text(
                                text = "Qidiruv so‘rovini o‘zgartiring yoki barcha parvarishlarni ko‘ring.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted)
                            )
                            SecondaryButton(
                                text = "Filtrlarni tozalash",
                                onClick = onResetFilters
                            )
                        }
                    }
                }
            } else {
                items(filteredServices, key = { it.id }) { service ->
                    ServiceCard(
                        service = service,
                        onClick = { onServiceClick(service.id) },
                        onBookClick = { onBookServiceClick(service.id) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}
