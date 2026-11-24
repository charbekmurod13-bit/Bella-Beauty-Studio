package com.example

import com.example.data.mock.BellaMockData
import com.example.data.models.ServiceCategory
import com.example.viewmodel.BellaUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun mockData_containsExpectedFigmaServicesAndSpecialists() {
        assertEquals(5, BellaMockData.services.size)
        assertEquals(3, BellaMockData.specialists.size)
        assertEquals(3, BellaMockData.initialFavoriteServiceIds.size)
        assertEquals("Amelia Bennett", BellaMockData.defaultUser.fullName)
    }

    @Test
    fun uiState_filtersByCategoryAndSearch() {
        val state = BellaUiState(selectedCategory = ServiceCategory.NAILS)
        assertEquals(1, state.filteredServices.size)
        assertEquals("Tabiiy manikyur-pedikyur", state.filteredServices.first().name)

        val searchState = BellaUiState(searchQuery = "pardoz")
        assertEquals(1, searchState.filteredServices.size)
        assertEquals("Nafis pardoz", searchState.filteredServices.first().name)
    }

    @Test
    fun uiState_promoDiscountCalculatesTwentyPercentOff() {
        val state = BellaUiState()
        assertEquals(85, state.bookingFinalPriceUsd)
        val promoState = state.copy(
            bookingDraft = state.bookingDraft.copy(
                selectedServiceId = "srv_makeup",
                promoCodeApplied = true
            )
        )
        assertEquals(76, promoState.bookingFinalPriceUsd)
        assertTrue(promoState.getAvailableTimeSlotsForSelectedDay().isNotEmpty())
    }
}
