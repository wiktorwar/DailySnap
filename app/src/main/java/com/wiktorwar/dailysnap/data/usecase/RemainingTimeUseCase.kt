package com.wiktorwar.dailysnap.data.usecase

import com.wiktorwar.dailysnap.data.time.Clock
import com.wiktorwar.dailysnap.data.FlowUseCase
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RemainingTimeUseCase @Inject constructor(
    private val clock: Clock,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, Long>(dispatcherProvider) {

    override fun execute(params: Unit?): Flow<Long> = flow {
        val midnightMillis = clock.midnightMillis()

        var remainingMillis: Long
        do {
            val now = clock.currentTimeMillis()
            remainingMillis = (midnightMillis - now).coerceAtLeast(0L)
            emit(remainingMillis)

            delay(TimeUnit.SECONDS.toMillis(1))
        } while (remainingMillis > 0)
    }
}
