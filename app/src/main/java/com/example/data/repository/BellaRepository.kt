package com.example.data.repository

import com.example.data.mock.BellaMockData
import com.example.data.models.Appointment
import com.example.data.models.AppointmentStatus
import com.example.data.models.BookingDayOption
import com.example.data.models.GalleryItem
import com.example.data.models.Review
import com.example.data.models.Service
import com.example.data.models.Specialist
import com.example.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository contract designed so Firebase Auth & Firestore can replace
 * [LocalBellaRepository] later without changing ViewModel or Compose screens.
 */
interface BellaRepository {
    val userFlow: Flow<User>
    val servicesFlow: Flow<List<Service>>
    val specialistsFlow: Flow<List<Specialist>>
    val favoriteServiceIdsFlow: Flow<Set<String>>
    val appointmentsFlow: Flow<List<Appointment>>
    val galleryFlow: Flow<List<GalleryItem>>
    val reviewsFlow: Flow<List<Review>>

    fun getBookingDays(): List<BookingDayOption>
    fun getTimeSlots(): List<String>
    suspend fun toggleFavorite(serviceId: String)
    suspend fun createAppointment(appointment: Appointment)
    suspend fun cancelAppointment(appointmentId: String)
    suspend fun updateUserProfile(fullName: String, email: String, phone: String)
    suspend fun updateNotificationPreferences(notificationsEnabled: Boolean, reminder24hEnabled: Boolean)
    suspend fun resetUserSession()
}

class LocalBellaRepository : BellaRepository {
    private val _user = MutableStateFlow(BellaMockData.defaultUser)
    override val userFlow: Flow<User> = _user.asStateFlow()

    private val _services = MutableStateFlow(BellaMockData.services)
    override val servicesFlow: Flow<List<Service>> = _services.asStateFlow()

    private val _specialists = MutableStateFlow(BellaMockData.specialists)
    override val specialistsFlow: Flow<List<Specialist>> = _specialists.asStateFlow()

    private val _favoriteIds = MutableStateFlow(BellaMockData.initialFavoriteServiceIds)
    override val favoriteServiceIdsFlow: Flow<Set<String>> = _favoriteIds.asStateFlow()

    private val _appointments = MutableStateFlow(BellaMockData.initialAppointments)
    override val appointmentsFlow: Flow<List<Appointment>> = _appointments.asStateFlow()

    private val _gallery = MutableStateFlow(BellaMockData.galleryItems)
    override val galleryFlow: Flow<List<GalleryItem>> = _gallery.asStateFlow()

    private val _reviews = MutableStateFlow(BellaMockData.reviews)
    override val reviewsFlow: Flow<List<Review>> = _reviews.asStateFlow()

    override fun getBookingDays(): List<BookingDayOption> = BellaMockData.bookingDays

    override fun getTimeSlots(): List<String> = BellaMockData.timeSlots

    override suspend fun toggleFavorite(serviceId: String) {
        _favoriteIds.update { current ->
            if (current.contains(serviceId)) current - serviceId else current + serviceId
        }
    }

    override suspend fun createAppointment(appointment: Appointment) {
        _appointments.update { current ->
            listOf(appointment) + current
        }
    }

    override suspend fun cancelAppointment(appointmentId: String) {
        _appointments.update { current ->
            current.map { item ->
                if (item.id == appointmentId) item.copy(status = AppointmentStatus.CANCELLED) else item
            }
        }
    }

    override suspend fun updateUserProfile(fullName: String, email: String, phone: String) {
        _user.update { current ->
            current.copy(
                fullName = fullName.trim(),
                email = email.trim(),
                phone = phone.trim()
            )
        }
    }

    override suspend fun updateNotificationPreferences(
        notificationsEnabled: Boolean,
        reminder24hEnabled: Boolean
    ) {
        _user.update { current ->
            current.copy(
                notificationsEnabled = notificationsEnabled,
                reminder24hEnabled = reminder24hEnabled
            )
        }
    }

    override suspend fun resetUserSession() {
        _user.value = BellaMockData.defaultUser
        _favoriteIds.value = BellaMockData.initialFavoriteServiceIds
        _appointments.value = BellaMockData.initialAppointments
    }
}
