package com.hotels.loyalty.daos

import com.hotels.loyalty.FreeNight
import org.springframework.stereotype.Component
import java.util.*

@Component
class FreeNightDao {
    val allFreeNights = mutableMapOf<UUID, FreeNight>()

    fun createFreeNight(freeNight: FreeNight) {
        allFreeNights[freeNight.id] = freeNight
    }
}