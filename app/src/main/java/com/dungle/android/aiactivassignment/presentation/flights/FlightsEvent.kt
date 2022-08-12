package com.dungle.android.aiactivassignment.presentation.flights

import com.dungle.android.aiactivassignment.domain.model.Flight

sealed class FlightsEvent {
    object Refresh : FlightsEvent()
    data class OnSearchQueryChange(val query: String) : FlightsEvent()
    data class OnItemRemoveOrRestore(val isRemoved: Boolean, val item: Flight) : FlightsEvent()
}
