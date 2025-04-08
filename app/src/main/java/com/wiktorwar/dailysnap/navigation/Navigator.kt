package com.wiktorwar.dailysnap.navigation

import kotlinx.coroutines.flow.Flow

interface Navigator {
    fun navigate(destination: Destination)
    fun back()
    val destinations: Flow<Destination>
}