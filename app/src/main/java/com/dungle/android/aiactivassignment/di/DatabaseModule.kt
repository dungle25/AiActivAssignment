package com.dungle.android.aiactivassignment.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dungle.android.aiactivassignment.data.local.entity.FlightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): FlightDatabase {
        return Room.databaseBuilder(
            application,
            FlightDatabase::class.java,
            "flight.db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `DeletedFlightEntity` (`id` TEXT NOT NULL, `dateUtc` TEXT NOT NULL, `flightNumber` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
        }
    }
}