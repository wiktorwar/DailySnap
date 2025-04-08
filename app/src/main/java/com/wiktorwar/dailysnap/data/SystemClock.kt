package com.wiktorwar.dailysnap.data

import com.wiktorwar.dailysnap.data.time.Clock
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class SystemClock @Inject constructor() : Clock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun midnightMillis(): Long {
        val zone = ZoneId.systemDefault()
        val midnight = Instant.ofEpochMilli(currentTimeMillis())
            .atZone(zone)
            .toLocalDate()
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
        return midnight.toEpochMilli()
    }
}