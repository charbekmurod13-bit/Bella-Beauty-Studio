package com.example.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mock.BellaMockData
import com.example.data.models.Appointment
import com.example.data.models.AppointmentStatus
import com.example.data.models.BookingDayOption
import com.example.data.models.GalleryItem
import com.example.data.models.Review
import com.example.data.models.Service
import com.example.data.models.ServiceCategory
import com.example.data.models.Specialist
import com.example.data.models.User
import com.example.data.repository.BellaRepository
import com.example.data.repository.LocalBellaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class BookingFormErrors(
    val fullNameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val generalError: String? = null
)

data class BookingDraftState(
    val selectedServiceId: String = "srv_facial",
    val selectedSpecialistId: String = "spec_sofia",
    val selectedDayId: String = "day_24",
    val selectedTimeSlot: String = "10:00",
    val customerName: String = BellaMockData.defaultUser.fullName,
    val customerPhone: String = BellaMockData.defaultUser.phone,
    val customerEmail: String = BellaMockData.defaultUser.email,
    val promoCodeApplied: Boolean = false,
    val errors: BookingFormErrors = BookingFormErrors(),
    val isSubmitting: Boolean = false
)

data class BellaUiState(
    val isLoading: Boolean = false,
    val user: User = BellaMockData.defaultUser,
    val services: List<Service> = BellaMockData.services,
    val specialists: List<Specialist> = BellaMockData.specialists,
    val favoriteServiceIds: Set<String> = BellaMockData.initialFavoriteServiceIds,
    val appointments: List<Appointment> = BellaMockData.initialAppointments,
    val galleryItems: List<GalleryItem> = BellaMockData.galleryItems,
    val reviews: List<Review> = BellaMockData.reviews,
    val bookingDays: List<BookingDayOption> = BellaMockData.bookingDays,
    val timeSlots: List<String> = BellaMockData.timeSlots,
    val selectedCategory: ServiceCategory = ServiceCategory.ALL,
    val searchQuery: String = "",
    val bookingDraft: BookingDraftState = BookingDraftState(),
    val lastConfirmedAppointment: Appointment? = null,
    val selectedGalleryItem: GalleryItem? = null
) {
    val filteredServices: List<Service>
        get() = services.filter { service ->
            val matchesCategory = selectedCategory == ServiceCategory.ALL || service.category == selectedCategory
            val query = searchQuery.trim().lowercase()
            val matchesSearch = query.isEmpty() ||
                service.name.lowercase().contains(query) ||
                service.shortDescription.lowercase().contains(query) ||
                service.category.label.lowercase().contains(query)
            matchesCategory && matchesSearch
        }

    val favoriteServices: List<Service>
        get() = services.filter { favoriteServiceIds.contains(it.id) }

    val featuredServices: List<Service>
        get() = services.filter { it.isFeaturedOnHome }

    val upcomingAppointments: List<Appointment>
        get() = appointments.filter { it.status == AppointmentStatus.UPCOMING }

    val pastAppointments: List<Appointment>
        get() = appointments.filter { it.status == AppointmentStatus.COMPLETED }

    val cancelledAppointments: List<Appointment>
        get() = appointments.filter { it.status == AppointmentStatus.CANCELLED }

    val nextUpcomingAppointment: Appointment?
        get() = upcomingAppointments.firstOrNull()

    val selectedBookingService: Service
        get() = services.firstOrNull { it.id == bookingDraft.selectedServiceId } ?: services.first()

    val selectedBookingSpecialist: Specialist
        get() = specialists.firstOrNull { it.id == bookingDraft.selectedSpecialistId } ?: specialists.first()

    val selectedBookingDay: BookingDayOption
        get() = bookingDays.firstOrNull { it.id == bookingDraft.selectedDayId } ?: bookingDays[1]

    val bookingFinalPriceUsd: Int
        get() {
            val base = selectedBookingService.priceUsd
            return if (bookingDraft.promoCodeApplied) (base * 0.8).roundToInt() else base
        }

    fun getAvailableTimeSlotsForSelectedDay(): List<String> {
        val day = selectedBookingDay
        return if (!day.isAvailable) emptyList() else timeSlots
    }
}

class BellaViewModel(
    private val repository: BellaRepository = LocalBellaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(BellaUiState())
    val uiState: StateFlow<BellaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.userFlow,
                repository.servicesFlow,
                repository.specialistsFlow,
                repository.favoriteServiceIdsFlow,
                repository.appointmentsFlow
            ) { user, services, specialists, favorites, appointments ->
                _uiState.value.copy(
                    user = user,
                    services = services,
                    specialists = specialists,
                    favoriteServiceIds = favorites,
                    appointments = appointments,
                    bookingDays = repository.getBookingDays(),
                    timeSlots = repository.getTimeSlots()
                )
            }.collect { updated ->
                _uiState.value = updated
            }
        }
    }

    fun selectCategory(category: ServiceCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleFavorite(serviceId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(serviceId)
        }
    }

    fun startBooking(
        serviceId: String? = null,
        specialistId: String? = null,
        applyWelcomePromo: Boolean = false
    ) {
        val currentUser = _uiState.value.user
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(
                    selectedServiceId = serviceId ?: state.bookingDraft.selectedServiceId,
                    selectedSpecialistId = specialistId ?: state.bookingDraft.selectedSpecialistId,
                    customerName = currentUser.fullName,
                    customerPhone = currentUser.phone,
                    customerEmail = currentUser.email,
                    promoCodeApplied = applyWelcomePromo || state.bookingDraft.promoCodeApplied,
                    errors = BookingFormErrors(),
                    isSubmitting = false
                )
            )
        }
    }

    fun selectBookingService(serviceId: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(selectedServiceId = serviceId)
            )
        }
    }

    fun selectBookingSpecialist(specialistId: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(selectedSpecialistId = specialistId)
            )
        }
    }

    fun selectBookingDay(dayId: String) {
        val day = _uiState.value.bookingDays.firstOrNull { it.id == dayId } ?: return
        if (!day.isAvailable) return
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(selectedDayId = dayId)
            )
        }
    }

    fun selectBookingTime(timeSlot: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(selectedTimeSlot = timeSlot)
            )
        }
    }

    fun togglePromoCode(applied: Boolean) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(promoCodeApplied = applied)
            )
        }
    }

    fun updateCustomerName(value: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(
                    customerName = value,
                    errors = state.bookingDraft.errors.copy(fullNameError = null, generalError = null)
                )
            )
        }
    }

    fun updateCustomerPhone(value: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(
                    customerPhone = value,
                    errors = state.bookingDraft.errors.copy(phoneError = null, generalError = null)
                )
            )
        }
    }

    fun updateCustomerEmail(value: String) {
        _uiState.update { state ->
            state.copy(
                bookingDraft = state.bookingDraft.copy(
                    customerEmail = value,
                    errors = state.bookingDraft.errors.copy(emailError = null, generalError = null)
                )
            )
        }
    }

    fun confirmBooking(onSuccess: () -> Unit) {
        val state = _uiState.value
        val draft = state.bookingDraft

        val trimmedName = draft.customerName.trim()
        val trimmedPhone = draft.customerPhone.trim()
        val trimmedEmail = draft.customerEmail.trim()

        val nameError = if (trimmedName.length < 2) "Ismingizni to‘liq kiriting" else null
        val phoneDigits = trimmedPhone.filter { it.isDigit() }
        val phoneError = if (phoneDigits.length < 7) "Telefon raqamingizni to‘g‘ri kiriting" else null
        val emailError = if (trimmedEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            "Yaroqli elektron pochta kiriting"
        } else {
            null
        }
        val timeAvailable = state.getAvailableTimeSlotsForSelectedDay().contains(draft.selectedTimeSlot)
        val generalError = if (!timeAvailable) "Tanlangan sanada bo‘sh vaqt mavjud emas" else null

        if (nameError != null || phoneError != null || emailError != null || generalError != null) {
            _uiState.update {
                it.copy(
                    bookingDraft = it.bookingDraft.copy(
                        errors = BookingFormErrors(
                            fullNameError = nameError,
                            phoneError = phoneError,
                            emailError = emailError,
                            generalError = generalError
                        )
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(bookingDraft = it.bookingDraft.copy(isSubmitting = true)) }
            delay(350)

            val service = state.selectedBookingService
            val specialist = state.selectedBookingSpecialist
            val day = state.selectedBookingDay
            val finalPrice = state.bookingFinalPriceUsd
            val codeNum = 2400 + state.appointments.size + 1

            val newAppointment = Appointment(
                id = "apt_${System.currentTimeMillis()}",
                bookingCode = "BBS-$codeNum",
                serviceId = service.id,
                serviceName = service.name,
                serviceImageResId = service.imageResId,
                specialistId = specialist.id,
                specialistName = if (specialist.isAnyOption) "Sofia (Tayinlangan)" else specialist.name,
                dateLabel = day.fullDateLabel,
                shortDateLabel = day.shortDateLabel,
                timeSlot = draft.selectedTimeSlot,
                priceUsd = finalPrice,
                status = AppointmentStatus.UPCOMING,
                customerName = trimmedName,
                customerPhone = trimmedPhone,
                customerEmail = trimmedEmail,
                promoApplied = draft.promoCodeApplied
            )

            repository.createAppointment(newAppointment)
            _uiState.update {
                it.copy(
                    bookingDraft = it.bookingDraft.copy(isSubmitting = false, errors = BookingFormErrors()),
                    lastConfirmedAppointment = newAppointment
                )
            }
            onSuccess()
        }
    }

    fun cancelAppointment(appointmentId: String) {
        viewModelScope.launch {
            repository.cancelAppointment(appointmentId)
        }
    }

    fun updateUserProfile(fullName: String, email: String, phone: String): Boolean {
        val nameOk = fullName.trim().length >= 2
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
        val phoneOk = phone.filter { it.isDigit() }.length >= 7
        if (!nameOk || !emailOk || !phoneOk) return false
        viewModelScope.launch {
            repository.updateUserProfile(fullName, email, phone)
        }
        return true
    }

    fun updateNotifications(notificationsEnabled: Boolean, reminder24hEnabled: Boolean) {
        viewModelScope.launch {
            repository.updateNotificationPreferences(notificationsEnabled, reminder24hEnabled)
        }
    }

    fun openGalleryPreview(item: GalleryItem) {
        _uiState.update { it.copy(selectedGalleryItem = item) }
    }

    fun closeGalleryPreview() {
        _uiState.update { it.copy(selectedGalleryItem = null) }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            repository.resetUserSession()
            onLoggedOut()
        }
    }
}
