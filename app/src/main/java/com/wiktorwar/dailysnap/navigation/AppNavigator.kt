package com.wiktorwar.dailysnap.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor(): Navigator {
    private val _destinations = MutableSharedFlow<Destination>(extraBufferCapacity = 1)
    override val destinations: Flow<Destination> = _destinations

    override fun navigate(destination: Destination) {
        _destinations.tryEmit(destination)
    }

    override fun back() {
        _destinations.tryEmit(Destination.Back)
    }
}