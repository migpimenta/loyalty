package com.hotels.loyalty.daos

import com.hotels.loyalty.FreeNight
import com.hotels.loyalty.LoyaltyPoint
import org.springframework.stereotype.Component
import java.util.*

@Component
class FreeNightDao {
    val freeNights = mutableMapOf<UUID, FreeNight>()

    fun createFreeNight(points: List<LoyaltyPoint>, accountId: Int): FreeNight {
        val freeNight = FreeNight(usdValue = averageUsdValue(points), accountId = accountId)

        freeNights[freeNight.id] = freeNight

        return freeNight
    }

    fun getFreeNights(accountId: Int): List<FreeNight> {
        return freeNights.values.filter { it.accountId == accountId }
    }

    fun markAsRedeemed(freeNightId: UUID) = {
        freeNights[freeNightId]?.let { freeNights[it.id] = it.markAsRedeemed() }
    }

    private fun averageUsdValue(it: List<LoyaltyPoint>) = it.map { it.usdValue }.average()

    /**
     * Extension function.
     */
    fun FreeNight.markAsRedeemed(): FreeNight = this.copy(redeemed = true)

}