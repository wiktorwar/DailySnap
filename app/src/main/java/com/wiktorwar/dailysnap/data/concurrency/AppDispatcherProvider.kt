package com.wiktorwar.dailysnap.data.concurrency

import androidx.compose.ui.platform.AndroidUiDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AppDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val main: CoroutineContext = AndroidUiDispatcher.Main
    override val io: CoroutineContext = Dispatchers.IO
}