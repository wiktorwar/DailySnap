package com.wiktorwar.dailysnap.feature.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.wiktorwar.dailysnap.di.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Base class for view models.
 * [Intent] is an input for view model, it represents user intention.
 * [ViewState] is output of viewmodel, it's a representation of current snapshot of the screen.
 * Class is based of [the molecule sample] (https://github.com/cashapp/molecule/blob/trunk/sample-viewmodel/src/main/java/com/example/molecule/viewmodel/MoleculeViewModel.kt)
 */
abstract class BaseViewModel<Intent, ViewState>(
    dispatcherProvider: DispatcherProvider,
    recompositionMode: RecompositionMode,
) : ViewModel() {

    // Events have a capacity large enough to handle simultaneous UI events, but
    // small enough to surface issues if they get backed up for some reason.
    private val events = MutableSharedFlow<Intent>(extraBufferCapacity = 20)

    val viewStates: StateFlow<ViewState>

    init {
        viewStates = viewModelScope.launchMolecule(mode = recompositionMode) {
            states(events)
        }
    }

    fun handle(intent: Intent) {
        if (!events.tryEmit(intent)) {
            error("Event buffer overflow.")
        }
    }

    @Composable
    protected abstract fun states(intents: Flow<Intent>): ViewState
}
