package com.wiktorwar.dailysnap.feature.prompt

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.wiktorwar.dailysnap.data.Prompt
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.data.usecase.TodayPromptUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
class PromptViewModelTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val dispatcherProvider = object : DispatcherProvider {
        override val io = testDispatcher
        override val main = testDispatcher
    }

    private val todayPromptUseCase = mockk<TodayPromptUseCase>()

    @Test
    fun `emits view state with mapped prompt and remaining time`() = runTest(testDispatcher) {
        // Given
        val prompt = Prompt(prompt = "Test prompt", remainingTime = 3661000L) // 1h 1m 1s
        coEvery { todayPromptUseCase.invoke() } returns flowOf(prompt)

        val viewModel = PromptViewModel(
            todayPromptUseCase = todayPromptUseCase,
            recompositionMode = RecompositionMode.Immediate,
            dispatcherProvider = dispatcherProvider,
        )

        val expectedState = PromptViewState(
            userPrompt = "Test prompt",
            remainingTime = "01:01:01"
        )

        viewModel.viewStates.test {
            testScheduler.advanceUntilIdle()
            assertEquals(expectedState, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}