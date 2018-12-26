package com.hotels.loyalty

import java.util.*

data class Point(val id: UUID = UUID.randomUUID(), val accountId: Int, val usdValue: Double, val rewardId: UUID? = null)

data class Reward(val id: UUID = UUID.randomUUID(), val usdValue: Double, val accountId: Int, val redeemed : Boolean = false)

data class Redemption(val id: UUID = UUID.randomUUID(), val rewardId: UUID)