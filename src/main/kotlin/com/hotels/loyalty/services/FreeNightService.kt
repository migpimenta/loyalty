package com.hotels.loyalty.services

import com.hotels.loyalty.FreeNight
import com.hotels.loyalty.LoyaltyPoint
import org.springframework.stereotype.Component

@Component
class FreeNightService {

    fun createFreeNight(points: List<LoyaltyPoint>) : FreeNight {
        return FreeNight(usdValue = averageUsdValue(points))
    }

    private fun averageUsdValue(it: List<LoyaltyPoint>) = it.map { it.usdValue }.average()
}