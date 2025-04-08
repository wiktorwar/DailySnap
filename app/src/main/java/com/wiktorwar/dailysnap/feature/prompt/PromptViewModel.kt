package com.wiktorwar.dailysnap.feature.prompt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import com.wiktorwar.dailysnap.data.usecase.TodayPromptUseCase
import com.wiktorwar.dailysnap.feature.base.BaseViewModel
import com.wiktorwar.dailysnap.feature.prompt.PromptIntent.LoadPrompt
import com.wiktorwar.dailysnap.feature.prompt.PromptIntent.TakePicture
import com.wiktorwar.dailysnap.navigation.Destination
import com.wiktorwar.dailysnap.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class PromptViewModel @Inject constructor(
    private val todayPromptUseCase: TodayPromptUseCase,
    private val navigator: Navigator,
    recompositionMode: RecompositionMode,
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel<PromptIntent, PromptViewState>(recompositionMode, dispatcherProvider) {

    @Composable
    override fun states(intents: Flow<PromptIntent>): PromptViewState {
        var viewState by remember { mutableStateOf(PromptViewState()) }

        LaunchedEffect(Unit) {
            intents.collect { intent ->
                when (intent) {
                    LoadPrompt -> {
                        viewModelScope.launch {
                            todayPromptUseCase().collect { prompt ->
                            viewState = PromptViewState(
                                userPrompt = prompt.prompt,
                                remainingTime = prompt.remainingTime?.let { formatRemainingTime(it) })
                        }
                        }
                    }

                    TakePicture -> navigator.navigate(Destination.Camera)
                }
            }
        }

        LaunchedEffect(Unit) {
            handle(LoadPrompt)
        }

        return viewState
    }

    private fun formatRemainingTime(millis: Long): String {
        val duration = Duration.ofMillis(millis)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}