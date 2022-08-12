package com.dungle.android.aiactivassignment.di

import android.app.Application
import androidx.room.Room
import com.dungle.android.aiactivassignment.data.local.FlightDatabase
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
        ).build()
    }
}