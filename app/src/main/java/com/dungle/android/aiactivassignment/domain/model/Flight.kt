package com.dungle.android.aiactivassignment.domain.model

import com.dungle.android.aiactivassignment.data.local.entity.FlightEntity

data class Flight(
    val id: String,
    val dateUtc: String,
    val flightNumber: Int,
    val name: String
) {
    fun toFlightEntity(): FlightEntity {
        return FlightEntity(id = id, dateUtc = dateUtc, flightNumber = flightNumber, name = name)
    }
}
