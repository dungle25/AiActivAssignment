package com.dungle.android.aiactivassignment.domain.repository

import com.dungle.android.aiactivassignment.common.Resource
import com.dungle.android.aiactivassignment.domain.model.Flight
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    suspend fun getFlights(fetchFromRemote: Boolean) : Flow<Resource<List<Flight>>>
    suspend fun getFlights(searchQuery: String) : Flow<Resource<List<Flight>>>
    suspend fun deleteItem(item : Flight)
    suspend fun addItem(item : Flight)
}