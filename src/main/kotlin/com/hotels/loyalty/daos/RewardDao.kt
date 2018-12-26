package com.hotels.loyalty.daos

import com.hotels.loyalty.Reward
import org.springframework.stereotype.Component
import java.util.*

@Component
class RewardDao {
    val allRewards = mutableMapOf<UUID, Reward>()

    fun saveReward(reward: Reward) {
        allRewards[reward.id] = reward
    }

    fun getRewardsByAccount(accountId: Int): List<Reward> {
        return allRewards.values.filter { it.accountId == accountId }
    }

    fun getReward(rewardId: UUID): Reward? {
        return allRewards[rewardId]
    }
}