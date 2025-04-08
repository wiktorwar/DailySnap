package com.wiktorwar.dailysnap.di

import app.cash.molecule.RecompositionMode
import com.wiktorwar.dailysnap.data.concurrency.AppDispatcherProvider
import com.wiktorwar.dailysnap.data.concurrency.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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