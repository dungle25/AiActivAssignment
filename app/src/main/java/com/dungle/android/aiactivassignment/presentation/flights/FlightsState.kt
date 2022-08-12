package com.dungle.android.aiactivassignment.presentation.flights

import com.dungle.android.aiactivassignment.domain.model.Flight

data class FlightsState(
    val flights : List<Flight> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error: String = "",
    val searchQuery: String = "",
    val isItemDeleted: Boolean = false
)
