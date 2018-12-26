package com.hotels.loyalty.services

import com.hotels.loyalty.Reward
import com.hotels.loyalty.Point
import com.hotels.loyalty.daos.RewardDao
import com.hotels.loyalty.daos.PointDao
import org.apache.juli.logging.LogFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoyaltyService(val pointDao: PointDao, val rewardDao: RewardDao) {
    private val logger = LogFactory.getLog(LoyaltyService::class.java)
    val pointsPerReward = 5

    /**
     * Creates loyalty points. In the process creates rewards by:
     * - Gathering all points not associated with a reward for accounts we are adding points.
     * - Chunk them in {@link #pointsPerReward}.
     * - Create reward and saving it.
     * - update points with the new rewardId.
     */
    fun addPoints(pointsToAdd: List<Point>) {

        pointDao.getPointsFor(getAccountIds(pointsToAdd))
                .filter { it.rewardId == null }
                .union(pointsToAdd)
                .groupBy { it.accountId }
                .map { (accountId, points) ->
                    points
                        .chunked(pointsPerReward)
                        .onEach { dissociatedPoints ->
                            // persist points which are not associated with a reward
                            if (dissociatedPoints.size < pointsPerReward) {
                                pointDao.upsertPoints(dissociatedPoints)
                            }
                        }
                        .filter { it.size == pointsPerReward }
                        .map { (createReward(it, accountId) to it) }
                        .onEach { (reward, points) ->
                            // persist newly created reward and points associated with it
                            rewardDao.saveReward(reward)
                            pointDao.upsertPoints(points.map { it.copy(rewardId = reward.id) } )
                            logRewardCreation(reward, points)
                        }
                }
    }

    fun getPointsFor(accountId: Int): List<Point> {
        return pointDao.getPointsFor(listOf(accountId))
    }

    fun getRewards(accountId: Int): List<Reward> {
        return rewardDao.getRewardsByAccount(accountId)
    }

    fun redeemReward(accountId: Int, rewardId: UUID) {
        rewardDao.getReward(rewardId)?.let {
            when (accountId) {
                it.accountId -> rewardDao.saveReward(it.redeemReward())
                else -> illegalArgumentException("Trying to redeem rewardId=$rewardId on accountId=$accountId but the reward belongs to accountId=${it.accountId}")
            }
        } ?: illegalArgumentException("No reward found for rewardId=$rewardId")
    }

    private fun illegalArgumentException(message: String) {
        logger.error(message)
        throw IllegalArgumentException(message)
    }

    private fun getAccountIds(points: List<Point>) = points.map { it.accountId }

    private fun createReward(points: List<Point>, accountId: Int) = Reward(usdValue = averageUsdValue(points), accountId = accountId)

    private fun averageUsdValue(it: List<Point>) = it.map { it.usdValue }.average()

    private fun logRewardCreation(reward: Reward, points: List<Point>) =
        logger.info("Reward created rewardId=${reward.id}, valueUsd=${reward.usdValue}, pointIds=${points.map { it.id }}")

    /**
     * Extension function.
     */
    private fun Reward.redeemReward(): Reward = this.copy(redeemed = true)
}