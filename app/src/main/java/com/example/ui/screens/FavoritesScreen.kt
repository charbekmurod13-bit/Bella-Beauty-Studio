package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.FavoriteServiceCard
import com.example.ui.components.PrimaryButton
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

@Composable
fun FavoritesScreen(
    uiState: BellaUiState,
    onServiceClick: (String) -> Unit,
    onRemoveFavorite: (String) -> Unit,
    onBookServiceClick: (String) -> Unit,
    onBrowseServicesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favorites = uiState.favoriteServices

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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text(
                        text = stringResource(R.string.favorites_eyebrow),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.5.sp,
                            letterSpacing = 1.6.sp,
                            color = BellaMuted
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.favorites_title),
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = CormorantGaramondFamily,
                            fontSize = 31.sp,
                            color = BellaInk
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.favorites_subtitle),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.5.sp,
                            color = BellaMuted
                        )
                    )
                }
            }

            if (favorites.isEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = BellaWhite,
                        border = BorderStroke(1.dp, BellaBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = BellaRose,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "Hozircha sevimlilar bo‘sh",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = CormorantGaramondFamily,
                                    color = BellaInk
                                )
                            )
                            Text(
                                text = "O‘zingizga yoqqan parvarishlarni yurakcha orqali saqlab qo‘ying.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            PrimaryButton(
                                text = "Xizmatlarni ko‘rish",
                                onClick = onBrowseServicesClick
                            )
                        }
                    }
                }
            } else {
                items(favorites, key = { it.id }) { service ->
                    FavoriteServiceCard(
                        service = service,
                        onCardClick = { onServiceClick(service.id) },
                        onRemoveFavorite = { onRemoveFavorite(service.id) },
                        onBookClick = { onBookServiceClick(service.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.favorites_footer_quote),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = CormorantGaramondFamily,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            color = BellaMuted
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}
