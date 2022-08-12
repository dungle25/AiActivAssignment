package com.dungle.android.aiactivassignment.data.local

import androidx.room.*
import com.dungle.android.aiactivassignment.data.local.entity.FlightEntity

@Dao
interface FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlights(flights : List<FlightEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlights(flight : FlightEntity)

    @Query("DELETE FROM flightentity")
    suspend fun clearFlights()

    @Delete
    suspend fun deleteFlight(flight: FlightEntity)

    @Query("SELECT * FROM flightentity WHERE name LIKE :searchName")
    suspend fun getFlightByName(searchName: String) : List<FlightEntity>

    @Query("SELECT * FROM flightentity")
    suspend fun getFlights() : List<FlightEntity>
}