package com.hotels.loyalty.services

import com.hotels.loyalty.Reward
import com.hotels.loyalty.Point
import com.hotels.loyalty.daos.RewardDao
import com.hotels.loyalty.daos.PointDao
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoyaltyService(val pointDao: PointDao, val rewardDao: RewardDao) {
    val pointsPerReward = 4

    /**
     * Creates loyalty points. In the process creates rewards by:
     * - Gathering all points not associated with a reward for accounts we are adding points.
     * - Chunk them in {@link #pointsPerReward}.
     * - Create reward and saving it.
     * - update points with the new rewardId.
     */
    fun addPoints(points: List<Point>) {

        pointDao.getPointsFor(*getAccountIds(points))
                .filter { it.rewardId == null }
                .union(points)
                .groupBy { it.accountId }
                .map { (accountId, points) ->
                    points
                        .chunked(pointsPerReward)
                        .onEach {
                            // side-effect: persist points not associated with a reward
                            if (it.size < pointsPerReward) {
                                pointDao.upsertPoints(points)
                            }
                        }
                        .filter { it.size == pointsPerReward }
                        .map { (rewardDao.createReward(points, accountId) to points) }
                        .onEach { (reward, points) ->
                            //side-effect: persist points associated with the newly created reward
                            pointDao.upsertPoints(points.map { it.copy(rewardId = reward.id) })
                        }
                }
    }

    fun getPointsFor(accountId: Int): List<Point> {
        return pointDao.getPointsFor(accountId)
    }

    fun getRewards(accountId: Int): List<Reward> {
        return rewardDao.getRewards(accountId)
    }

    private fun getAccountIds(points: List<Point>): IntArray {
        return points.map { it.accountId }.toIntArray()
    }

    fun redeemReward(accountId: Int, rewardId: UUID) {
        rewardDao.markAsRedeemed(rewardId)
    }
}

