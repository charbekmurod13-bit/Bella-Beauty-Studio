package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Review
import com.example.data.models.Service
import com.example.ui.components.BellaTopBar
import com.example.ui.components.PrimaryButton
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaGold
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaSand
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily

@Composable
fun ServiceDetailsScreen(
    service: Service?,
    reviews: List<Review>,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onBookNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (service == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BellaIvory)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Xizmat topilmadi", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))
                PrimaryButton(
                    text = "Orqaga qaytish",
                    onClick = onBackClick,
                    modifier = Modifier.widthIn(max = 220.dp)
                )
            }
        }
        return
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BellaIvory)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
        ) {
            BellaTopBar(
                title = service.category.bookingSubtitleLabel,
                onBackClick = onBackClick,
                trailingAction = {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.testTag("details_favorite_button")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Sevimlilar",
                            tint = if (isFavorite) BellaRose else BellaInk
                        )
                    }
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Image(
                    painter = painterResource(id = service.imageResId),
                    contentDescription = service.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.42f)
                        .clip(RoundedCornerShape(24.dp))
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = service.name,
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 30.sp,
                                color = BellaInk
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "$${service.priceUsd}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 28.sp,
                                color = BellaRose,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BellaBlush
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = null,
                                    tint = BellaRose,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${service.durationMinutes} daq.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = BellaInk,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Reyting",
                                tint = BellaGold,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${service.rating} (${service.reviewCount} sharh)",
                                style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                            )
                        }
                    }
                }

                Text(
                    text = service.fullDescription,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = BellaInk.copy(alpha = 0.85f),
                        lineHeight = 23.sp
                    )
                )

                // Benefits Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BellaWhite,
                    border = BorderStroke(1.dp, BellaBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Parvarish afzalliklari",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = BellaInk,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        service.benefits.forEach { benefit ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = null,
                                    tint = BellaRose,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = benefit,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk)
                                )
                            }
                        }
                    }
                }

                // Preparation Note
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = BellaSand
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = BellaRose,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = service.preparationNote,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = BellaInk.copy(alpha = 0.85f),
                                lineHeight = 18.sp
                            )
                        )
                    }
                }

                if (reviews.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Mijozlar fikri",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 22.sp,
                                color = BellaInk
                            )
                        )
                        reviews.forEach { review ->
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = BellaWhite,
                                border = BorderStroke(1.dp, BellaBorder)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = review.authorName,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = BellaInk
                                            )
                                        )
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = BellaGold,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = review.rating.toString(),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = review.comment,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = BellaMuted,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Surface(
                color = BellaWhite,
                shadowElevation = 6.dp
            ) {
                Column {
                    HorizontalDivider(thickness = 1.dp, color = BellaBorder)
                    Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                        PrimaryButton(
                            text = "Ushbu xizmatga yozilish · $${service.priceUsd}",
                            onClick = onBookNowClick,
                            testTag = "service_details_book_button"
                        )
                    }
                }
            }
        }
    }
}
