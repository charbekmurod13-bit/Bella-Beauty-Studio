package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Service
import com.example.data.models.Specialist
import com.example.ui.components.BellaTopBar
import com.example.ui.components.PrimaryButton
import com.example.ui.components.ServiceCard
import com.example.ui.components.SpecialistCard
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaGold
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

@Composable
fun SpecialistsScreen(
    uiState: BellaUiState,
    onBackClick: () -> Unit,
    onSpecialistClick: (String) -> Unit,
    onBookSpecialistClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val team = uiState.specialists.filter { !it.isAnyOption }

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
                title = "Mutaxassislarimiz",
                onBackClick = onBackClick
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Bella Go‘zallik Studiyasining tajribali ustalari bilan tanishing.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted)
                    )
                }
                items(team, key = { it.id }) { specialist ->
                    SpecialistCard(
                        specialist = specialist,
                        onClick = { onSpecialistClick(specialist.id) },
                        onBookClick = { onBookSpecialistClick(specialist.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun SpecialistDetailsScreen(
    specialist: Specialist?,
    availableServices: List<Service>,
    onBackClick: () -> Unit,
    onServiceClick: (String) -> Unit,
    onBookServiceWithSpecialist: (String, String) -> Unit,
    onBookSpecialistClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (specialist == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BellaIvory)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            PrimaryButton(
                text = "Orqaga",
                onClick = onBackClick,
                modifier = Modifier.widthIn(max = 200.dp)
            )
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
                title = specialist.name,
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (specialist.imageResId != null) {
                        Image(
                            painter = painterResource(id = specialist.imageResId),
                            contentDescription = specialist.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(116.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(116.dp)
                                .clip(CircleShape)
                                .background(BellaBlush),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = specialist.name,
                                tint = BellaRose,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = specialist.name,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = CormorantGaramondFamily,
                            fontSize = 30.sp,
                            color = BellaInk
                        )
                    )
                    Text(
                        text = specialist.roleTitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = BellaRose,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BellaWhite,
                            border = BorderStroke(1.dp, BellaBorder)
                        ) {
                            Text(
                                text = "${specialist.experienceYears} yil tajriba",
                                style = MaterialTheme.typography.bodySmall.copy(color = BellaInk),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Reyting",
                                tint = BellaGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${specialist.rating} reyting",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = BellaInk,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                Text(
                    text = specialist.fullBio,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = BellaInk.copy(alpha = 0.85f),
                        lineHeight = 23.sp
                    ),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "Mutaxassis xizmatlari",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = CormorantGaramondFamily,
                        fontSize = 22.sp,
                        color = BellaInk
                    )
                )

                availableServices.forEach { service ->
                    ServiceCard(
                        service = service,
                        onClick = { onServiceClick(service.id) },
                        onBookClick = { onBookServiceWithSpecialist(service.id, specialist.id) }
                    )
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
                            text = "${specialist.name} qabuliga yozilish",
                            onClick = { onBookSpecialistClick(specialist.id) }
                        )
                    }
                }
            }
        }
    }
}
