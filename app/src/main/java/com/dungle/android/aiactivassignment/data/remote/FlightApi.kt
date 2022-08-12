package com.dungle.android.aiactivassignment.data.remote

import com.dungle.android.aiactivassignment.data.remote.model.FlightResponse
import retrofit2.http.GET

interface FlightApi {
    @GET("launches/upcoming")
    suspend fun getFlightResponseInfo() : List<FlightResponse>
}