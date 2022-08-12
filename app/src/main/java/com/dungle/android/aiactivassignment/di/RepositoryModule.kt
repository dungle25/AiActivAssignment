package com.dungle.android.aiactivassignment.di

import com.dungle.android.aiactivassignment.data.local.FlightDatabase
import com.dungle.android.aiactivassignment.data.remote.FlightApi
import com.dungle.android.aiactivassignment.data.repository.FlightRepositoryImpl
import com.dungle.android.aiactivassignment.domain.repository.FlightRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFlightRepository(api: FlightApi, db: FlightDatabase): FlightRepository {
        return FlightRepositoryImpl(api, db)
    }


//    @Provides
//    @Singleton
//    @Named("flightNumberText")
//    fun getFlightNumberText(application: Application): String {
//        return application.getString(R.string.text_flight_number)
//    }
//
//    @Provides
//    @Singleton
//    @Named("launchTimeText")
//    fun getLaunchTimeText(application: Application): String {
//        return application.getString(R.string.text_launch_time)
//    }
}