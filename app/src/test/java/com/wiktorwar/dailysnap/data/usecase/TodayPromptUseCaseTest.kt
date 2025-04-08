package com.wiktorwar.dailysnap.data.usecase

import app.cash.turbine.test
import com.wiktorwar.dailysnap.data.Prompt
import com.wiktorwar.dailysnap.data.PromptRepository
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TodayPromptUseCaseTest {
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private val dispatcherProvider = mockk<DispatcherProvider> {
        every { io } returns testDispatcher
        every { main } returns testDispatcher
    }

    @Test
    fun `maps base prompt with remaining time`() = runTest(testDispatcher) {
        // Given
        val fakePrompt = Prompt(prompt = "What made you smile today?", remainingTime = null)
        val remainingTime = 42_000L

        val promptRepository = mockk<PromptRepository> {
            every { getRandomPrompt() } returns fakePrompt
        }

        val remainingTimeUseCase: RemainingTimeUseCase = mockk()
        every { remainingTimeUseCase.invoke() } returns flowOf(remainingTime)

        val useCase = TodayPromptUseCase(
            promptRepository = promptRepository,
            remainingTimeUseCase = remainingTimeUseCase,
            dispatcherProvider = dispatcherProvider
        )

        // When & Then
        useCase().test {
            assertEquals(
                Prompt(prompt = fakePrompt.prompt, remainingTime = remainingTime),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}