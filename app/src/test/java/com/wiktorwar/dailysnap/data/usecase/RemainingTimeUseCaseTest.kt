package com.wiktorwar.dailysnap.data.usecase

import app.cash.turbine.test
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.data.time.Clock
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class RemainingTimeUseCaseTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private val dispatcherProvider = mockk<DispatcherProvider> {
        every { io } returns testDispatcher
        every { main } returns testDispatcher
    }

    @Test
    fun `emits every second`() = runTest(testDispatcher) {
        val oneSecond = TimeUnit.SECONDS.toMillis(1)
        val halfASecond = oneSecond / 2
        val midnight = TimeUnit.SECONDS.toMillis(5) // midnight at +5s (time starts at 0)

        val clock = mockk<Clock> {
            every { currentTimeMillis() } answers { testScheduler.currentTime }
            every { midnightMillis() } returns midnight
        }

        val useCase = RemainingTimeUseCase(clock, dispatcherProvider)

        useCase().test {
            // initial emission (at t = 0)
            assertEquals(5_000, awaitItem())

            // advance by 0.5s → should NOT emit
            testScheduler.advanceTimeBy(halfASecond)
            expectNoEvents()

            // advance by another 0.5s → total 1s → should emit
            testScheduler.advanceTimeBy(halfASecond)
            assertEquals(4_000, awaitItem())

            // advance by 1s → should emit
            testScheduler.advanceTimeBy(oneSecond)
            assertEquals(3_000, awaitItem())

            // advance by 0.25s → should NOT emit
            testScheduler.advanceTimeBy(250)
            expectNoEvents()

            // advance by 0.75s → total 1s again → should emit
            testScheduler.advanceTimeBy(750)
            assertEquals(2_000, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}