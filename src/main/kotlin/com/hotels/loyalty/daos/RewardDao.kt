package com.hotels.loyalty.daos

import com.hotels.loyalty.Reward
import com.hotels.loyalty.Point
import org.springframework.stereotype.Component
import java.util.*

@Component
class RewardDao {
    val rewards = mutableMapOf<UUID, Reward>()

    fun saveReward(reward: Reward) {
        rewards[reward.id] = reward
    }

    fun getRewardsByAccount(accountId: Int): List<Reward> {
        return rewards.values.filter { it.accountId == accountId }
    }

    fun getReward(rewardId: UUID): Reward? {
        return rewards[rewardId]
    }
}