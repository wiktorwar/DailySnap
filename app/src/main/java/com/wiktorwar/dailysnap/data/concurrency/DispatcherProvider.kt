package com.wiktorwar.dailysnap.data.concurrency

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}