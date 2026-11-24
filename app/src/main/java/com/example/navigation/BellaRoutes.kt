package com.example.navigation

object BellaRoutes {
    const val WELCOME = "welcome"
    const val HOME = "home"
    const val SERVICES = "services"
    const val SERVICE_DETAILS = "service_details/{serviceId}"
    const val BOOKING = "booking"
    const val BOOKING_CONFIRMATION = "booking_confirmation"
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"
    const val APPOINTMENTS = "appointments"
    const val SPECIALISTS = "specialists"
    const val SPECIALIST_DETAILS = "specialist_details/{specialistId}"

    fun serviceDetails(serviceId: String) = "service_details/$serviceId"
    fun specialistDetails(specialistId: String) = "specialist_details/$specialistId"
}
