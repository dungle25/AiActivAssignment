package com.dungle.android.aiactivassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dungle.android.aiactivassignment.domain.model.Flight

@Entity
data class FlightEntity(
    @PrimaryKey val id: String,
    val dateUtc: String,
    val flightNumber: Int,
    val name: String
) {
    fun toFlight(): Flight {
        return Flight(
            id = id,
            dateUtc = dateUtc,
            flightNumber = flightNumber,
            name = name
        )
    }
}
