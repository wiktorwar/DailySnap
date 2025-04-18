package com.wiktorwar.dailysnap.data

import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Params, out Result>(
    private val dispatcherProvider: DispatcherProvider
) {
    operator fun invoke(params: Params? = null): Flow<Result> {
        return execute(params).flowOn(dispatcherProvider.io)
    }

    protected abstract fun execute(params: Params?): Flow<Result>
}