package com.hotels.loyalty

import java.util.*

data class LoyaltyPoint(val id: UUID = UUID.randomUUID(), val accountId: Int, val usdValue: Double, val freeNightId: UUID? = null)

data class FreeNight(val id: UUID = UUID.randomUUID(), val usdValue: Double)