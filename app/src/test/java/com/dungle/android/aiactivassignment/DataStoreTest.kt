package com.dungle.android.aiactivassignment

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import java.io.File

abstract class DataStoreTest : CoroutineTest() {

    private lateinit var preferencesScope: CoroutineScope
    protected lateinit var dataStore: DataStore<Preferences>
    private val scope = TestScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun createDatastore() {
        Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))

        dataStore = PreferenceDataStoreFactory.create(scope = scope) {
            InstrumentationRegistry.getInstrumentation().targetContext.preferencesDataStoreFile(
                "user_preferences"
            )
        }
    }

    @After
    fun removeDatastore() {
        File(
            ApplicationProvider.getApplicationContext<Context>().filesDir,
            "datastore"
        ).deleteRecursively()

        preferencesScope.cancel()
    }
}