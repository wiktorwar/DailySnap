package com.wiktorwar.dailysnap.di

import app.cash.molecule.RecompositionMode
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {

    @Binds
    abstract fun bindsDispatcherProvider(appDispatcherProvider: AppDispatcherProvider): DispatcherProvider

    companion object {
        @Provides
        fun providesRecompositionMode(): RecompositionMode = RecompositionMode.ContextClock
    }
}