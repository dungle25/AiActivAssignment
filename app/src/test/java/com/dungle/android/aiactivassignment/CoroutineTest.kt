package com.dungle.android.aiactivassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class CoroutineTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    protected val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    protected val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setupViewModelScope() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanupViewModelScope() {
        Dispatchers.resetMain()
    }

    @After
    fun cleanupCoroutines() {
        testDispatcher.cleanupTestCoroutines()
        testDispatcher.resumeDispatcher()
    }

    fun coTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest(block)
}