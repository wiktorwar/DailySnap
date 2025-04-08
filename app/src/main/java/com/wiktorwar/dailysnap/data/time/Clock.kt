package com.wiktorwar.dailysnap.data.time

interface Clock {
    fun currentTimeMillis(): Long
    fun midnightMillis(): Long
}