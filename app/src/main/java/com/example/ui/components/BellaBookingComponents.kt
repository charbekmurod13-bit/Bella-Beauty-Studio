package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Appointment
import com.example.data.models.BookingDayOption
import com.example.data.models.Service
import com.example.data.models.Specialist
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaDisabledText
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaSand
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily

@Composable
fun BookingSpecialistChipCard(
    specialist: Specialist,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) BellaBlush else BellaWhite,
        label = "specialistBg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) BellaRose else BellaBorder,
        label = "specialistBorder"
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(if (isSelected) 1.2.dp else 1.dp, borderColor),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("booking_specialist_${specialist.id}")
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (specialist.imageResId != null) {
                    Image(
                        painter = painterResource(id = specialist.imageResId),
                        contentDescription = specialist.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) BellaWhite.copy(alpha = 0.7f) else BellaBlush.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.GridView,
                            contentDescription = specialist.name,
                            tint = BellaRose,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = specialist.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BellaInk
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = specialist.roleTitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.5.sp,
                    color = BellaMuted
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DateSelector(
    days: List<BookingDayOption>,
    selectedDayId: String,
    onSelectDay: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        days.forEach { day ->
            val isSelected = day.id == selectedDayId
            val bgColor by animateColorAsState(
                targetValue = when {
                    isSelected -> BellaRose
                    else -> BellaWhite
                },
                label = "dayBg"
            )
            val borderColor = if (isSelected) BellaRose else BellaBorder

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = bgColor,
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable(enabled = day.isAvailable) { onSelectDay(day.id) }
                    .testTag("date_day_${day.dayNumber}")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = day.dayShort,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = when {
                                isSelected -> BellaWhite.copy(alpha = 0.85f)
                                !day.isAvailable -> BellaDisabledText
                                else -> BellaMuted
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = day.dayNumber,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = when {
                                isSelected -> BellaWhite
                                !day.isAvailable -> BellaDisabledText
                                else -> BellaInk
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TimeSlotGrid(
    slots: List<String>,
    selectedSlot: String,
    onSelectSlot: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (slots.isEmpty()) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = BellaWhite,
            border = BorderStroke(1.dp, BellaBorder),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Tanlangan sana uchun bo‘sh vaqt qolmagan. Iltimos, boshqa kunni tanlang.",
                style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted),
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        slots.chunked(2).forEach { rowSlots ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowSlots.forEach { slot ->
                    val isSelected = slot == selectedSlot
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) BellaRose else BellaWhite,
                        label = "slotBg"
                    )
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = bgColor,
                        border = BorderStroke(1.dp, if (isSelected) BellaRose else BellaBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .clickable { onSelectSlot(slot) }
                            .testTag("time_slot_${slot.replace(":", "_")}")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = slot,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (isSelected) BellaWhite else BellaInk,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingSummary(
    service: Service,
    finalPriceUsd: Int,
    promoApplied: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = BellaSand,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Jami xizmat narxi",
                    style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (promoApplied) "BELLA20 (20% chegirma qo‘llandi)" else "Studiyada to‘lang",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = BellaInk,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Text(
                text = "$$finalPriceUsd",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = CormorantGaramondFamily,
                    fontSize = 26.sp,
                    color = BellaRose,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun ConfirmationCard(
    appointment: Appointment,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = BellaWhite,
        border = BorderStroke(1.dp, BellaBorder.copy(alpha = 0.7f)),
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SIZNING TASHRIFINGIZ",
                    style = MaterialTheme.typography.labelSmall.copy(color = BellaMuted)
                )
                Text(
                    text = "#${appointment.bookingCode}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = BellaRose,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Faqat siz uchun vaqt.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = CormorantGaramondFamily,
                    fontSize = 24.sp,
                    color = BellaInk
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${appointment.serviceName} · ${appointment.customerName}",
                style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
            )
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(thickness = 1.dp, color = BellaBorder.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "Sana va vaqt",
                    tint = BellaRose,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${appointment.shortDateLabel} · ${appointment.timeSlot}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = BellaInk,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "Mutaxassis",
                    tint = BellaRose,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Mutaxassis: ${appointment.specialistName}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = BellaInk,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "To‘lov",
                    style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                )
                Text(
                    text = "Studiyada · $${appointment.priceUsd}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = BellaRose,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
