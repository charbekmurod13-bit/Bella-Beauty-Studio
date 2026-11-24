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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.BellaTopBar
import com.example.ui.components.BookingSpecialistChipCard
import com.example.ui.components.BookingSummary
import com.example.ui.components.CustomTextField
import com.example.ui.components.DateSelector
import com.example.ui.components.PrimaryButton
import com.example.ui.components.SectionHeader
import com.example.ui.components.TimeSlotGrid
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaSand
import com.example.ui.theme.BellaStatusCancelledText
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    uiState: BellaUiState,
    onBackClick: () -> Unit,
    onSelectService: (String) -> Unit,
    onSelectSpecialist: (String) -> Unit,
    onSelectDay: (String) -> Unit,
    onSelectTime: (String) -> Unit,
    onTogglePromo: (Boolean) -> Unit,
    onCustomerNameChange: (String) -> Unit,
    onCustomerPhoneChange: (String) -> Unit,
    onCustomerEmailChange: (String) -> Unit,
    onConfirmBooking: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedService = uiState.selectedBookingService
    val selectedSpecialist = uiState.selectedBookingSpecialist
    val selectedDay = uiState.selectedBookingDay
    val availableSlots = uiState.getAvailableTimeSlotsForSelectedDay()

    var showServicePicker by remember { mutableStateOf(false) }
    var showPersonalInfoSheet by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BellaIvory)
            .statusBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp)
        ) {
            BellaTopBar(
                title = stringResource(R.string.booking_title),
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = stringResource(R.string.booking_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.5.sp,
                        color = BellaMuted
                    ),
                    modifier = Modifier.padding(top = 0.dp)
                )

                // Selected Service Selector Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = BellaWhite,
                    border = BorderStroke(1.dp, BellaBorder.copy(alpha = 0.7f)),
                    shadowElevation = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { showServicePicker = true }
                        .testTag("booking_service_selector")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(BellaBlush),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = selectedService.name,
                                tint = BellaRose,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedService.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 15.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BellaInk
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${selectedService.durationMinutes} daq. · ${selectedService.category.bookingSubtitleLabel}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = BellaMuted
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = "Xizmatni almashtirish",
                            tint = BellaMuted
                        )
                    }
                }

                // Specialist Selector
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(title = stringResource(R.string.booking_specialist_title))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.specialists.forEach { specialist ->
                            BookingSpecialistChipCard(
                                specialist = specialist,
                                isSelected = specialist.id == selectedSpecialist.id,
                                onClick = { onSelectSpecialist(specialist.id) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Date Selector
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.booking_date_title),
                        trailingCaption = "NOYABR 2026"
                    )
                    DateSelector(
                        days = uiState.bookingDays,
                        selectedDayId = selectedDay.id,
                        onSelectDay = onSelectDay
                    )
                }

                // Time Slots
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.booking_time_title),
                        trailingCaption = stringResource(R.string.booking_local_time)
                    )
                    TimeSlotGrid(
                        slots = availableSlots,
                        selectedSlot = uiState.bookingDraft.selectedTimeSlot,
                        onSelectSlot = onSelectTime
                    )
                }

                // Price Summary
                BookingSummary(
                    service = selectedService,
                    finalPriceUsd = uiState.bookingFinalPriceUsd,
                    promoApplied = uiState.bookingDraft.promoCodeApplied
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.booking_pay_note),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            color = BellaMuted
                        ),
                        textAlign = TextAlign.Center
                    )

                    PrimaryButton(
                        text = stringResource(R.string.booking_confirm_cta),
                        onClick = { showPersonalInfoSheet = true },
                        enabled = availableSlots.isNotEmpty(),
                        testTag = "booking_proceed_button"
                    )
                }
            }
        }
    }

    // Service Picker Bottom Sheet
    if (showServicePicker) {
        ModalBottomSheet(
            onDismissRequest = { showServicePicker = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = BellaIvory
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Parvarishni tanlang",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = CormorantGaramondFamily,
                        color = BellaInk
                    )
                )
                uiState.services.forEach { service ->
                    val isCurrent = service.id == selectedService.id
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isCurrent) BellaBlush else BellaWhite,
                        border = BorderStroke(1.dp, if (isCurrent) BellaRose else BellaBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onSelectService(service.id)
                                showServicePicker = false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = service.imageResId),
                                contentDescription = service.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = service.name,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = BellaInk
                                    )
                                )
                                Text(
                                    text = "${service.durationMinutes} daq. · $${service.priceUsd}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                                )
                            }
                            if (isCurrent) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = "Tanlangan",
                                    tint = BellaRose
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Personal Information & Appointment Review Bottom Sheet (Steps 5-7)
    if (showPersonalInfoSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPersonalInfoSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = BellaIvory
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Ma’lumotlaringizni tasdiqlang",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = CormorantGaramondFamily,
                        fontSize = 26.sp,
                        color = BellaInk
                    )
                )

                // Review Summary Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = BellaSand,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = selectedService.name,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = BellaInk
                            )
                        )
                        Text(
                            text = "${selectedDay.fullDateLabel} · ${uiState.bookingDraft.selectedTimeSlot}",
                            style = MaterialTheme.typography.bodySmall.copy(color = BellaInk)
                        )
                        Text(
                            text = "Usta: ${selectedSpecialist.name} · Jami: $${uiState.bookingFinalPriceUsd}",
                            style = MaterialTheme.typography.bodySmall.copy(color = BellaRose)
                        )
                    }
                }

                // BELLA20 promo switch
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = BellaWhite,
                    border = BorderStroke(1.dp, BellaBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "BELLA20 chegirmasini qo‘llash",
                                style = MaterialTheme.typography.titleSmall.copy(color = BellaInk)
                            )
                            Text(
                                text = "Ilk tashrif uchun 20% chegirma",
                                style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                            )
                        }
                        Switch(
                            checked = uiState.bookingDraft.promoCodeApplied,
                            onCheckedChange = onTogglePromo,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = BellaWhite,
                                checkedTrackColor = BellaRose
                            )
                        )
                    }
                }

                HorizontalDivider(color = BellaBorder)

                CustomTextField(
                    value = uiState.bookingDraft.customerName,
                    onValueChange = onCustomerNameChange,
                    label = "To‘liq ism sharif",
                    placeholder = "Ismingizni kiriting",
                    errorText = uiState.bookingDraft.errors.fullNameError,
                    leadingIcon = {
                        Icon(Icons.Outlined.PersonOutline, contentDescription = null, tint = BellaMuted)
                    }
                )

                CustomTextField(
                    value = uiState.bookingDraft.customerPhone,
                    onValueChange = onCustomerPhoneChange,
                    label = "Telefon raqam",
                    placeholder = "+998 90 123 45 67",
                    errorText = uiState.bookingDraft.errors.phoneError,
                    leadingIcon = {
                        Icon(Icons.Outlined.Phone, contentDescription = null, tint = BellaMuted)
                    }
                )

                CustomTextField(
                    value = uiState.bookingDraft.customerEmail,
                    onValueChange = onCustomerEmailChange,
                    label = "Elektron pochta",
                    placeholder = "email@manzil.com",
                    errorText = uiState.bookingDraft.errors.emailError,
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = null, tint = BellaMuted)
                    }
                )

                if (uiState.bookingDraft.errors.generalError != null) {
                    Text(
                        text = uiState.bookingDraft.errors.generalError,
                        style = MaterialTheme.typography.bodySmall.copy(color = BellaStatusCancelledText)
                    )
                }

                PrimaryButton(
                    text = if (uiState.bookingDraft.isSubmitting) "Saqlanmoqda..." else "Yozilishni yakunlash · $${uiState.bookingFinalPriceUsd}",
                    onClick = {
                        onConfirmBooking()
                    },
                    enabled = !uiState.bookingDraft.isSubmitting,
                    testTag = "booking_final_confirm_button"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
