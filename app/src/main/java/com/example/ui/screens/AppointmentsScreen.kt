package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Appointment
import com.example.data.models.AppointmentStatus
import com.example.ui.components.AppointmentCard
import com.example.ui.components.BellaTopBar
import com.example.ui.components.PrimaryButton
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaStatusCancelledText
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

private enum class AppointmentTab(val label: String) {
    UPCOMING("Kutilayotgan"),
    PAST("O‘tgan"),
    CANCELLED("Bekor qilingan")
}

@Composable
fun AppointmentsScreen(
    uiState: BellaUiState,
    onBackClick: () -> Unit,
    onCancelAppointment: (String) -> Unit,
    onBookNewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(AppointmentTab.UPCOMING) }
    var selectedAppointment by remember { mutableStateOf<Appointment?>(null) }

    val displayedAppointments = when (selectedTab) {
        AppointmentTab.UPCOMING -> uiState.upcomingAppointments
        AppointmentTab.PAST -> uiState.pastAppointments
        AppointmentTab.CANCELLED -> uiState.cancelledAppointments
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
                title = "Tashriflarim",
                onBackClick = onBackClick
            )

            // Segmented Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppointmentTab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) BellaRose else BellaWhite,
                        label = "aptTabBg"
                    )
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) BellaWhite else BellaInk,
                        label = "aptTabText"
                    )
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = bgColor,
                        border = BorderStroke(1.dp, if (isSelected) BellaRose else BellaBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedTab = tab }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp)
                        ) {
                            Text(
                                text = tab.label,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (displayedAppointments.isEmpty()) {
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
                                    imageVector = Icons.Outlined.CalendarToday,
                                    contentDescription = null,
                                    tint = BellaRose,
                                    modifier = Modifier.size(34.dp)
                                )
                                Text(
                                    text = "Bu bo‘limda tashriflar yo‘q",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontFamily = CormorantGaramondFamily,
                                        color = BellaInk
                                    )
                                )
                                Text(
                                    text = "O‘zingizga mos parvarish va qulay vaqtni tanlab yoziling.",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                PrimaryButton(
                                    text = "Yangi tashrif belgilash",
                                    onClick = onBookNewClick
                                )
                            }
                        }
                    }
                } else {
                    items(displayedAppointments, key = { it.id }) { appointment ->
                        AppointmentCard(
                            appointment = appointment,
                            onClick = { selectedAppointment = appointment }
                        )
                    }
                }
            }
        }
    }

    selectedAppointment?.let { appointment ->
        AlertDialog(
            onDismissRequest = { selectedAppointment = null },
            containerColor = BellaIvory,
            title = {
                Text(
                    text = appointment.serviceName,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = CormorantGaramondFamily,
                        color = BellaInk
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Buyurtma kodi: #${appointment.bookingCode}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BellaRose,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "Holat: ${appointment.status.label}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk)
                    )
                    Text(
                        text = "Mutaxassis: ${appointment.specialistName}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk)
                    )
                    Text(
                        text = "Sana va vaqt: ${appointment.dateLabel} · ${appointment.timeSlot}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk)
                    )
                    Text(
                        text = "Mijoz: ${appointment.customerName} (${appointment.customerPhone})",
                        style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                    )
                    Text(
                        text = "Narxi: $${appointment.priceUsd} (Studiyada to‘lanadi)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BellaInk,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedAppointment = null }) {
                    Text("Yopish", color = BellaRose)
                }
            },
            dismissButton = {
                if (appointment.status == AppointmentStatus.UPCOMING) {
                    TextButton(
                        onClick = {
                            onCancelAppointment(appointment.id)
                            selectedAppointment = null
                        }
                    ) {
                        Text("Bekor qilish", color = BellaStatusCancelledText)
                    }
                }
            }
        )
    }
}
