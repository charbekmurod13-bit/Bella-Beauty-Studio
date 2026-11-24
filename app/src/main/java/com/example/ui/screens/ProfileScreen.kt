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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.CustomTextField
import com.example.ui.components.PrimaryButton
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BellaBlush
import com.example.ui.theme.BellaBorder
import com.example.ui.theme.BellaGold
import com.example.ui.theme.BellaInk
import com.example.ui.theme.BellaIvory
import com.example.ui.theme.BellaMuted
import com.example.ui.theme.BellaRose
import com.example.ui.theme.BellaStatusCancelledText
import com.example.ui.theme.BellaStatusConfirmedBg
import com.example.ui.theme.BellaStatusConfirmedText
import com.example.ui.theme.BellaWhite
import com.example.ui.theme.CormorantGaramondFamily
import com.example.viewmodel.BellaUiState

@Composable
fun ProfileScreen(
    uiState: BellaUiState,
    onViewAppointmentsClick: () -> Unit,
    onOpenFavoritesClick: () -> Unit,
    onUpdateProfile: (String, String, String) -> Boolean,
    onUpdateNotifications: (Boolean, Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = uiState.user
    val nextVisit = uiState.nextUpcomingAppointment
    val latestPast = uiState.pastAppointments.firstOrNull()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.profile_eyebrow),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.5.sp,
                                letterSpacing = 1.6.sp,
                                color = BellaMuted
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(R.string.profile_title),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 31.sp,
                                color = BellaInk
                            )
                        )
                    }
                    IconButton(
                        onClick = { showSettingsSheet = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("profile_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Tune,
                            contentDescription = "Sozlamalar",
                            tint = BellaInk
                        )
                    }
                }
            }

            // User Identity Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = user.avatarResId),
                        contentDescription = user.fullName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = CormorantGaramondFamily,
                                fontSize = 24.sp,
                                color = BellaInk
                            )
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp,
                                color = BellaMuted
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.profile_edit),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = BellaRose
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showEditProfileDialog = true }
                                .padding(vertical = 4.dp)
                                .testTag("profile_edit_link")
                        )
                    }
                }
            }

            // Loyalty Card
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = BellaBlush,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = user.loyaltyTitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    letterSpacing = 1.4.sp,
                                    color = BellaMuted
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = user.loyaltyMessage,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = CormorantGaramondFamily,
                                    fontSize = 20.sp,
                                    color = BellaInk
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = BellaGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            // Next Visit ("Keyingi tashrifingiz")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(title = stringResource(R.string.profile_next_visit))
                    if (nextVisit != null) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = BellaWhite,
                            border = BorderStroke(1.dp, BellaBorder.copy(alpha = 0.7f)),
                            shadowElevation = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable(onClick = onViewAppointmentsClick)
                                .testTag("profile_next_visit_card")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = BellaStatusConfirmedBg
                                ) {
                                    Text(
                                        text = nextVisit.status.label,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = BellaStatusConfirmedText,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = nextVisit.serviceName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = BellaInk
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.CalendarToday,
                                            contentDescription = null,
                                            tint = BellaRose,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = nextVisit.dateLabel,
                                                style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                                            )
                                            Text(
                                                text = nextVisit.timeSlot,
                                                style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                                            )
                                        }
                                    }
                                    Text(
                                        text = stringResource(R.string.details_link),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = BellaRose,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = BellaWhite,
                            border = BorderStroke(1.dp, BellaBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onViewAppointmentsClick)
                        ) {
                            Text(
                                text = "Hozircha rejalashtirilgan tashriflar yo‘q. Barchasini ko‘rish uchun bosing.",
                                style = MaterialTheme.typography.bodyMedium.copy(color = BellaMuted),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            // Visit History ("Tashriflar tarixi")
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionHeader(
                        title = stringResource(R.string.profile_visit_history),
                        actionText = stringResource(R.string.see_all),
                        onActionClick = onViewAppointmentsClick
                    )
                    if (latestPast != null) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = BellaWhite,
                            border = BorderStroke(1.dp, BellaBorder.copy(alpha = 0.7f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .clickable(onClick = onViewAppointmentsClick)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = latestPast.serviceImageResId),
                                    contentDescription = latestPast.serviceName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = latestPast.serviceName,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = BellaInk
                                        )
                                    )
                                    Text(
                                        text = "${latestPast.shortDateLabel} · $${latestPast.priceUsd}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                    contentDescription = "Ko'rish",
                                    tint = BellaMuted
                                )
                            }
                        }
                    }
                }
            }

            // Menu Items
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProfileMenuRow(
                        icon = Icons.Outlined.FavoriteBorder,
                        title = stringResource(R.string.profile_favorite_services),
                        onClick = onOpenFavoritesClick,
                        testTag = "profile_menu_favorites"
                    )
                    ProfileMenuRow(
                        icon = Icons.Outlined.Notifications,
                        title = stringResource(R.string.profile_notifications),
                        onClick = { showNotificationsDialog = true },
                        testTag = "profile_menu_notifications"
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        var nameInput by remember { mutableStateOf(user.fullName) }
        var emailInput by remember { mutableStateOf(user.email) }
        var phoneInput by remember { mutableStateOf(user.phone) }
        var validationError by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            containerColor = BellaIvory,
            title = {
                Text(
                    text = "Profilni tahrirlash",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = CormorantGaramondFamily,
                        color = BellaInk
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    CustomTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it; validationError = null },
                        label = "Ism sharif",
                        placeholder = "Amelia Bennett"
                    )
                    CustomTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it; validationError = null },
                        label = "Elektron pochta",
                        placeholder = "amelia.bennett@email.com"
                    )
                    CustomTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it; validationError = null },
                        label = "Telefon",
                        placeholder = "+998 90 123 45 67"
                    )
                    if (validationError != null) {
                        Text(
                            text = validationError!!,
                            style = MaterialTheme.typography.bodySmall.copy(color = BellaStatusCancelledText)
                        )
                    }
                }
            },
            confirmButton = {
                PrimaryButton(
                    text = "Saqlash",
                    onClick = {
                        if (onUpdateProfile(nameInput, emailInput, phoneInput)) {
                            showEditProfileDialog = false
                        } else {
                            validationError = "Iltimos, barcha maydonlarni to‘g‘ri to‘ldiring."
                        }
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Bekor qilish", color = BellaMuted)
                }
            }
        )
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        var pushEnabled by remember { mutableStateOf(user.notificationsEnabled) }
        var reminderEnabled by remember { mutableStateOf(user.reminder24hEnabled) }

        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            containerColor = BellaIvory,
            title = {
                Text(
                    text = "Bildirishnomalar",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = CormorantGaramondFamily,
                        color = BellaInk
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Maxsus takliflar va yangiliklar",
                            style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = pushEnabled,
                            onCheckedChange = { pushEnabled = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = BellaRose)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tashrifdan 24 soat oldin eslatma",
                            style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = reminderEnabled,
                            onCheckedChange = { reminderEnabled = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = BellaRose)
                        )
                    }
                }
            },
            confirmButton = {
                PrimaryButton(
                    text = "Saqlash",
                    onClick = {
                        onUpdateNotifications(pushEnabled, reminderEnabled)
                        showNotificationsDialog = false
                    }
                )
            }
        )
    }

    // Settings / Account Dialog (Includes Phone, My Appointments & Logout)
    if (showSettingsSheet) {
        AlertDialog(
            onDismissRequest = { showSettingsSheet = false },
            containerColor = BellaIvory,
            title = {
                Text(
                    text = "Hisob va sozlamalar",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = CormorantGaramondFamily,
                        color = BellaInk
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Telefon: ${user.phone}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BellaInk)
                    )
                    Text(
                        text = "Studiya manzili: Rose ko‘chasi, 24",
                        style = MaterialTheme.typography.bodySmall.copy(color = BellaMuted)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ProfileMenuRow(
                        icon = Icons.Outlined.CalendarToday,
                        title = "Mening tashriflarim",
                        onClick = {
                            showSettingsSheet = false
                            onViewAppointmentsClick()
                        },
                        testTag = "settings_my_appointments"
                    )
                    ProfileMenuRow(
                        icon = Icons.AutoMirrored.Rounded.Logout,
                        title = "Hisobdan chiqish",
                        onClick = {
                            showSettingsSheet = false
                            onLogoutClick()
                        },
                        tint = BellaStatusCancelledText,
                        testTag = "settings_logout_button"
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSettingsSheet = false }) {
                    Text("Yopish", color = BellaRose)
                }
            }
        )
    }
}

@Composable
private fun ProfileMenuRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit,
    tint: androidx.compose.ui.graphics.Color = BellaInk,
    testTag: String = ""
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = BellaWhite,
        border = BorderStroke(1.dp, BellaBorder.copy(alpha = 0.6f)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .then(if (testTag.isNotEmpty()) Modifier.testTag(testTag) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (tint == BellaStatusCancelledText) BellaStatusCancelledText else BellaRose,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                    color = tint
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = BellaMuted
            )
        }
    }
}
