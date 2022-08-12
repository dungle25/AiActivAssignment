package com.dungle.android.aiactivassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dungle.android.aiactivassignment.data.local.entity.FlightEntity

@Database(
    entities = [FlightEntity::class],
    version = 1
)
abstract class FlightDatabase : RoomDatabase() {
    abstract val dao: FlightDao
}