package com.dungle.android.aiactivassignment.data.repository

import com.dungle.android.aiactivassignment.common.Resource
import com.dungle.android.aiactivassignment.data.local.entity.DeletedFlightEntity
import com.dungle.android.aiactivassignment.data.local.entity.FlightDatabase
import com.dungle.android.aiactivassignment.data.remote.FlightApi
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.dungle.android.aiactivassignment.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val flightApi: FlightApi,
    db: FlightDatabase
) : FlightRepository {

    private val dao = db.dao

    override suspend fun getFlights(fetchFromRemote: Boolean): Flow<Resource<List<Flight>>> {
        return flow {
            emit(Resource.Loading(true))
            val localFlights = dao.getFlights()
            val deletedFlight = getDeletedFlights().map { it.toFlight() }

            emit(Resource.Success(
                data = localFlights.map { it.toFlight() }
                    .filter { !deletedFlight.contains(it) }
                    .sortedBy { it.flightNumber }
            ))

            val isDatabaseEmpty = localFlights.isEmpty()
            val shouldLoadFromCache = isDatabaseEmpty.not() && fetchFromRemote.not()
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteFlights = try {
                val response = flightApi.getFlightResponseInfo()
                    .map { it.toFlight() }
                    .filter { !deletedFlight.contains(it) }
                    .sortedBy { it.flightNumber }
                response
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("IOException: Could not load data"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error("HttpException: Something went wrong"))
                null
            }

            remoteFlights?.let { flights ->
                dao.clearFlights()
                dao.insertFlights(flights.map { it.toFlightEntity() })
                emit(Resource.Success(flights))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getFlights(searchQuery: String): Flow<Resource<List<Flight>>> {
        return flow {
            emit(Resource.Loading(true))
            val localFlights = dao.getFlightByName("%$searchQuery%")
            val deletedFlight = getDeletedFlights().map { it.toFlight() }
            val isDatabaseEmpty = localFlights.isEmpty()
            if (isDatabaseEmpty) {
                emit(Resource.Loading(false))
                emit(Resource.Success(
                    data = emptyList()
                ))
            } else {
                emit(Resource.Success(
                    data = localFlights.map { it.toFlight() }
                        .filter { !deletedFlight.contains(it) }
                        .sortedBy { it.flightNumber }
                ))
            }
        }
    }

    private suspend fun getDeletedFlights() : List<DeletedFlightEntity> {
        return dao.getDeletedFlights()
    }

    override suspend fun deleteItem(item: Flight) {
        dao.deleteFlight(item.toFlightEntity())
        dao.insertDeletedFlights(item.toDeletedFlightEntity())
    }

    override suspend fun addItem(item: Flight) {
        dao.insertFlights(item.toFlightEntity())
        dao.removeDeletedFlight(item.toDeletedFlightEntity())
    }
}
