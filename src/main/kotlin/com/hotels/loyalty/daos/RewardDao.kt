package com.hotels.loyalty.daos

import com.hotels.loyalty.Reward
import com.hotels.loyalty.Point
import org.springframework.stereotype.Component
import java.util.*

@Component
class RewardDao {
    val rewards = mutableMapOf<UUID, Reward>()

    fun createReward(points: List<Point>, accountId: Int): Reward {
        val reward = Reward(usdValue = averageUsdValue(points), accountId = accountId)

        rewards[reward.id] = reward

        return reward
    }

    fun getRewards(accountId: Int): List<Reward> {
        return rewards.values.filter { it.accountId == accountId }
    }

    fun markAsRedeemed(reward: UUID) = {
        rewards[reward]?.let { rewards[it.id] = it.markAsRedeemed() }
    }

    private fun averageUsdValue(it: List<Point>) = it.map { it.usdValue }.average()

    /**
     * Extension function.
     */
    fun Reward.markAsRedeemed(): Reward = copy(redeemed = true)
}