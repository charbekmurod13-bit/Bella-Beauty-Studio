package com.example.data.models

import androidx.annotation.DrawableRes

enum class ServiceCategory(val label: String, val bookingSubtitleLabel: String) {
    ALL("Barchasi", "Barcha xizmatlar"),
    HAIR("Soch", "Soch parvarishi"),
    NAILS("Tirnoq", "Manikyur va pedikyur"),
    MAKEUP("Pardoz", "Pardoz"),
    FACIAL("Yuz", "Yuz parvarishi"),
    BROWS("Qosh va kiprik", "Qosh va kiprik parvarishi")
}

data class Service(
    val id: String,
    val name: String,
    val shortDescription: String,
    val favoriteTagline: String,
    val fullDescription: String,
    val category: ServiceCategory,
    val durationMinutes: Int,
    val priceUsd: Int,
    val rating: Double,
    val reviewCount: Int,
    @DrawableRes val imageResId: Int,
    val benefits: List<String>,
    val preparationNote: String,
    val isFeaturedOnHome: Boolean = false
)

data class Specialist(
    val id: String,
    val name: String,
    val roleTitle: String,
    val shortDescription: String,
    val fullBio: String,
    val experienceYears: Int,
    val rating: Double,
    @DrawableRes val imageResId: Int?,
    val availableServiceIds: List<String>,
    val isAnyOption: Boolean = false
)

enum class AppointmentStatus(val label: String) {
    UPCOMING("Tasdiqlandi"),
    COMPLETED("Yakunlangan"),
    CANCELLED("Bekor qilingan")
}

data class Appointment(
    val id: String,
    val bookingCode: String,
    val serviceId: String,
    val serviceName: String,
    @DrawableRes val serviceImageResId: Int,
    val specialistId: String,
    val specialistName: String,
    val dateLabel: String,
    val shortDateLabel: String,
    val timeSlot: String,
    val priceUsd: Int,
    val status: AppointmentStatus,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val promoApplied: Boolean = false
)

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    @DrawableRes val avatarResId: Int,
    val loyaltyTitle: String,
    val loyaltyMessage: String,
    val notificationsEnabled: Boolean = true,
    val reminder24hEnabled: Boolean = true
)

data class GalleryItem(
    val id: String,
    val title: String,
    val caption: String,
    @DrawableRes val imageResId: Int
)

data class Review(
    val id: String,
    val serviceId: String,
    val authorName: String,
    val rating: Double,
    val comment: String,
    val dateLabel: String
)

data class BookingDayOption(
    val id: String,
    val dayShort: String,
    val dayNumber: String,
    val fullDateLabel: String,
    val shortDateLabel: String,
    val isAvailable: Boolean = true
)
