package com.wiktorwar.dailysnap.di

import com.wiktorwar.dailysnap.navigation.AppNavigator
import com.wiktorwar.dailysnap.navigation.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindNavigator(appNavigator: AppNavigator): Navigator
}