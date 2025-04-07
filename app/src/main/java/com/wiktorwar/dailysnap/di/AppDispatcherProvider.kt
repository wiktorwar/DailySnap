package com.wiktorwar.dailysnap.di

import androidx.compose.ui.platform.AndroidUiDispatcher
import com.wiktorwar.dailysnap.di.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AppDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val main: CoroutineContext = AndroidUiDispatcher.Main
    override val io: CoroutineContext = Dispatchers.IO
}