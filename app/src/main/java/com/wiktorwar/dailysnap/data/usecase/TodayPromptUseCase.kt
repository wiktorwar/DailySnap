package com.wiktorwar.dailysnap.data.usecase

import com.wiktorwar.dailysnap.data.FlowUseCase
import com.wiktorwar.dailysnap.data.Prompt
import com.wiktorwar.dailysnap.data.PromptRepository
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TodayPromptUseCase @Inject constructor(
    private val promptRepository: PromptRepository,
    private val remainingTimeUseCase: RemainingTimeUseCase,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, Prompt>(dispatcherProvider) {

    override fun execute(params: Unit?): Flow<Prompt> {
        val basePrompt = flowOf(promptRepository.getRandomPrompt())
        val remainingTimeFlow = remainingTimeUseCase()

        return combine(
            basePrompt,
            remainingTimeFlow
        ) { prompt, remainingSeconds ->
            prompt.copy(remainingTime = remainingSeconds)
        }
    }
}