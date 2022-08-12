package com.dungle.android.aiactivassignment.presentation.flights

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.dungle.android.aiactivassignment.data.repository.FakeFlightRepository
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FlightsViewModelTest : com.dungle.android.aiactivassignment.DataStoreTest() {

    private lateinit var viewModel: FlightsViewModel
    private val testKey: Preferences.Key<Boolean> = booleanPreferencesKey("isDeleted")

    private val flight1 = Flight("1", "2022-08-12T21:30:00.000Z", 1, "Star 1")
    private val flight2 = Flight("2", "2022-08-12T21:30:00.000Z", 2, "Star 2")
    private val flight3 = Flight("3", "2022-08-12T21:30:00.000Z", 3, "Falcon 1")
    private val flight4 = Flight("4", "2022-08-12T21:30:00.000Z", 4, "Falcon 2")

    private val emptyLocalFlightData = emptyList<Flight>()

    private val remoteFlightData = listOf(
        flight1, flight2, flight3, flight4
    )

    private val localFlightData = listOf(
        flight1,
        flight2
    )

    @Before
    fun setup(){
        viewModel = FlightsViewModel(FakeFlightRepository(), dataStore)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when event is Refresh`() = runTest {
        dataStore.edit { preferences ->
            preferences[testKey] = true
        }
        viewModel.onEvent(FlightsEvent.Refresh)
        val result = viewModel.state.value?.flights?.containsAll(remoteFlightData) == true
        advanceUntilIdle()
        assertThat(result).isTrue()
    }

    @Test
    fun `when event is Search`() {
        viewModel.onEvent(FlightsEvent.OnSearchQueryChange("test"))
    }

    @Test
    fun `when event is Delete`() {
        viewModel.onEvent(FlightsEvent.OnItemRemoveOrRestore(true, flight1))
    }

    @Test
    fun `when event is Restore`() {
        viewModel.onEvent(FlightsEvent.OnItemRemoveOrRestore(false, flight1))
    }
}