package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.FaceRetouchingNatural
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.GalleryItem
import com.example.data.models.ServiceCategory
import com.example.ui.components.FeaturedServiceCard
import com.example.ui.components.GalleryCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.SpecialistCard
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaSand
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

private data class HomeCategoryTile(
    val title: String,
    val category: ServiceCategory,
    val icon: ImageVector,
    val isHighlighted: Boolean = false
)

private val homeCategories = listOf(
    HomeCategoryTile("Soch\nturmagi", ServiceCategory.HAIR, Icons.Outlined.ContentCut, true),
    HomeCategoryTile("Manikyur\npedikyur", ServiceCategory.NAILS, Icons.Outlined.Brush),
    HomeCategoryTile("Pardoz", ServiceCategory.MAKEUP, Icons.Outlined.AutoAwesome),
    HomeCategoryTile("Yuz\nparvarishi", ServiceCategory.FACIAL, Icons.Outlined.FaceRetouchingNatural),
    HomeCategoryTile("Qosh va\nkiprik", ServiceCategory.BROWS, Icons.Outlined.Visibility)
)

@Composable
fun HomeScreen(
    uiState: BellaUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onOfferClick: () -> Unit,
    onCategoryClick: (ServiceCategory) -> Unit,
    onSeeAllServicesClick: () -> Unit,
    onServiceClick: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onProfileClick: () -> Unit,
    onSeeAllSpecialistsClick: () -> Unit,
    onSpecialistClick: (String) -> Unit,
    onBookSpecialistClick: (String) -> Unit,
    onGalleryItemClick: (GalleryItem) -> Unit,
    modifier: Modifier = Modifier
) {
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
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            // Greeting Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.home_eyebrow),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.5.sp,
                                letterSpacing = 1.6.sp,
                                color = BellaMuted
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.home_greeting),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 31.sp,
                                color = BellaInk
                            )
                        )
                    }
                    Image(
                        painter = painterResource(id = uiState.user.avatarResId),
                        contentDescription = uiState.user.fullName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onProfileClick)
                            .testTag("home_profile_avatar")
                    )
                }
            }

            // Search Bar
            item {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = BellaWhite,
                    border = BorderStroke(1.dp, BellaBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("home_search_bar")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Qidirish",
                            tint = BellaMuted,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (uiState.searchQuery.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.home_search_hint),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = BellaMuted,
                                        fontSize = 14.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            BasicTextField(
                                value = uiState.searchQuery,
                                onValueChange = onSearchQueryChange,
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    color = BellaInk,
                                    fontSize = 14.sp
                                ),
                                cursorBrush = SolidColor(BellaRose),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(onSearch = { onSearchSubmit() }),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchQueryChange("") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Tozalash",
                                    tint = BellaMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Promo Banner Card (BELLA20)
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = BellaBlush,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(176.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .clickable(onClick = onOfferClick)
                        .testTag("home_promo_banner")
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .weight(0.58f)
                                .fillMaxHeight()
                                .padding(start = 18.dp, top = 16.dp, bottom = 16.dp, end = 10.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.home_offer_eyebrow),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        letterSpacing = 1.4.sp,
                                        color = BellaMuted
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = stringResource(R.string.home_offer_title),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = CormorantGaramondFamily,
                                        fontSize = 23.sp,
                                        lineHeight = 26.sp,
                                        color = BellaInk
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = stringResource(R.string.home_offer_subtitle),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.5.sp,
                                        color = BellaMuted
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.home_offer_cta),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = BellaInk
                                    )
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    tint = BellaRose,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Image(
                            painter = painterResource(id = R.drawable.img_facial_glow_1795536605943),
                            contentDescription = "Ilk tashrif taklifi",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(0.42f)
                                .fillMaxHeight()
                        )
                    }
                }
            }

            // Categories Row ("Parvarishni tanlang")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.home_categories_title),
                        actionText = stringResource(R.string.see_all),
                        onActionClick = onSeeAllServicesClick
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        homeCategories.forEach { item ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { onCategoryClick(item.category) }
                                    .padding(vertical = 4.dp)
                                    .testTag("home_category_${item.category.name}")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (item.isHighlighted) BellaBlush else BellaSand),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title.replace("\n", " "),
                                        tint = BellaRose,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        lineHeight = 14.sp,
                                        color = BellaInk.copy(alpha = 0.85f)
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            // Featured Services ("Siz sevgan xizmatlar")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.home_featured_title),
                        actionText = stringResource(R.string.see_all),
                        onActionClick = onSeeAllServicesClick
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.featuredServices.take(2).forEach { service ->
                            FeaturedServiceCard(
                                service = service,
                                isFavorite = uiState.favoriteServiceIds.contains(service.id),
                                onCardClick = { onServiceClick(service.id) },
                                onToggleFavorite = { onToggleFavorite(service.id) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Below the fold: Specialists ("Mutaxassislarimiz")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.home_specialists_title),
                        actionText = stringResource(R.string.see_all),
                        onActionClick = onSeeAllSpecialistsClick
                    )
                    uiState.specialists.filter { !it.isAnyOption }.forEach { specialist ->
                        SpecialistCard(
                            specialist = specialist,
                            onClick = { onSpecialistClick(specialist.id) },
                            onBookClick = { onBookSpecialistClick(specialist.id) }
                        )
                    }
                }
            }

            // Below the fold: Studio Gallery ("Studiya galereyasi")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.home_gallery_title),
                        trailingCaption = "${uiState.galleryItems.size} ta surat"
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.galleryItems, key = { it.id }) { galleryItem ->
                            GalleryCard(
                                item = galleryItem,
                                onClick = { onGalleryItemClick(galleryItem) },
                                modifier = Modifier.width(136.dp)
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}
