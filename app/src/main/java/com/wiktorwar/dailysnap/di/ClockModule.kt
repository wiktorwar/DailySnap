package com.wiktorwar.dailysnap.di

import com.wiktorwar.dailysnap.data.SystemClock
import com.wiktorwar.dailysnap.data.time.Clock
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClockModule {

    @Binds
    @Singleton
    abstract fun bindClock(systemClock: SystemClock): Clock
}