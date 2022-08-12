package com.dungle.android.aiactivassignment.data.local.entity

import androidx.room.*

@Dao
interface FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlights(flights : List<FlightEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlights(flight : FlightEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedFlights(flight : DeletedFlightEntity)

    @Query("DELETE FROM flightentity")
    suspend fun clearFlights()

    @Delete
    suspend fun deleteFlight(flight: FlightEntity)

    @Delete
    suspend fun removeDeletedFlight(flight: DeletedFlightEntity)

    @Query("SELECT * FROM flightentity WHERE name LIKE :searchName")
    suspend fun getFlightByName(searchName: String) : List<FlightEntity>

    @Query("SELECT * FROM flightentity")
    suspend fun getFlights() : List<FlightEntity>

    @Query("SELECT * FROM deletedflightentity")
    suspend fun getDeletedFlights() : List<DeletedFlightEntity>
}