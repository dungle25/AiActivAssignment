package com.dungle.android.aiactivassignment.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FlightEntity::class, DeletedFlightEntity::class],
    version = 2
)
abstract class FlightDatabase : RoomDatabase() {
    abstract val dao: FlightDao
}