package com.dungle.android.aiactivassignment.data.repository

import com.dungle.android.aiactivassignment.common.Resource
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.dungle.android.aiactivassignment.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeFlightRepository : FlightRepository {

    private val emptyLocalFlightData = emptyList<Flight>()

    private val localFlightData = listOf(
        Flight("1", "2022-08-12T21:30:00.000Z", 1, "Star 1"),
        Flight("2", "2022-08-12T21:30:00.000Z", 2, "Star 2")
    )

    private val remoteFlightData = listOf(
        Flight("1", "2022-08-12T21:30:00.000Z", 1, "Star 1"),
        Flight("2", "2022-08-12T21:30:00.000Z", 2, "Star 2"),
        Flight("3", "2022-08-12T21:30:00.000Z", 3, "Falcon 1"),
        Flight("4", "2022-08-12T21:30:00.000Z", 4, "Falcon 2"),
    )

    override suspend fun getFlights(fetchFromRemote: Boolean): Flow<Resource<List<Flight>>> {
        return flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(
                data = localFlightData
            ))

            val isDatabaseEmpty = localFlightData.isEmpty()
            val shouldLoadFromCache = isDatabaseEmpty.not() && fetchFromRemote.not()
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteFlights = try {
                remoteFlightData
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("IOException: Could not load data"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error("HttpException: Something went wrong"))
                null
            }

            remoteFlights?.let { flights ->
                emit(Resource.Success(flights))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getFlights(searchQuery: String): Flow<Resource<List<Flight>>> {
        return flow {
            emit(Resource.Loading(true))

            val isDatabaseEmpty = localFlightData.isEmpty()
            if (isDatabaseEmpty) {
                emit(Resource.Loading(false))
                emit(Resource.Success(
                    data = emptyList()
                ))
            } else {
                emit(Resource.Success(
                    data = localFlightData
                ))
            }
        }
    }

    override suspend fun deleteItem(item: Flight) {

    }

    override suspend fun addItem(item: Flight) {

    }
}
