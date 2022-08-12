package com.dungle.android.aiactivassignment.presentation.flights

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dungle.android.aiactivassignment.common.Resource
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.dungle.android.aiactivassignment.domain.repository.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val repository: FlightRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _state: MutableLiveData<FlightsState> = MutableLiveData()
    val state: LiveData<FlightsState>
        get() = _state

    private var searchJob: Job? = null

    fun onEvent(event: FlightsEvent) {
        when (event) {
            is FlightsEvent.Refresh -> {
                getFlight(fetchFromRemote = true)
            }
            is FlightsEvent.OnSearchQueryChange -> {
                _state.value = FlightsState(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getFlight()
                }
            }
            is FlightsEvent.OnItemRemoveOrRestore -> {
                if (event.isRemoved) {
                    onItemDelete(event.item)
                } else {
                    onAddItem(event.item)
                }
                saveToDataStore(dataStore, event.isRemoved)
            }
        }
    }

    private fun saveToDataStore(dataStore: DataStore<Preferences>, isDeleted: Boolean) {
        viewModelScope.launch {
            val key = booleanPreferencesKey("isDeleted")
            dataStore.edit {
                it[key] = isDeleted
            }
        }
    }

    private fun onAddItem(item : Flight) {
        viewModelScope.launch {
            repository.addItem(item)
            _state.value = FlightsState(isItemDeleted = false)
        }
    }

    private fun onItemDelete(item : Flight) {
        viewModelScope.launch {
            repository.deleteItem(item)
            _state.value = FlightsState(isItemDeleted = true)
        }
    }

    private fun getFlight(searchQuery: String? = state.value?.searchQuery?.lowercase(Locale.ROOT),
                          fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            val key = booleanPreferencesKey("isDeleted")
            val preferences = dataStore.data.first()
            val isDeleted = preferences[key] == true
            _state.value = FlightsState(isRefreshing = true)
            if (searchQuery.isNullOrEmpty()) {
                // Assume that if there are items deleted before, the data should only be loaded from local
                repository.getFlights(fetchFromRemote && !isDeleted)
                    .collect { result ->
                        handleResult(result)
                    }
            } else {
                repository.getFlights(searchQuery)
                    .collect { result ->
                        handleResult(result)
                    }
            }
        }
    }

    private fun handleResult(result: Resource<List<Flight>>) {
        when (result) {
            is Resource.Success -> {
                result.data?.let {
                    _state.value = FlightsState(flights = it)
                }
            }
            is Resource.Error -> {
                _state.value = FlightsState(error = result.message ?: "Unexpected error occured")
            }
            is Resource.Loading -> {
                _state.value = FlightsState(isLoading = result.isLoading)
            }
        }
    }
}