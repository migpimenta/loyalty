package com.hotels.loyalty.services

import com.hotels.loyalty.FreeNight
import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.daos.FreeNightDao
import com.hotels.loyalty.daos.PointDao
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoyaltyService(val pointDao: PointDao, val freeNightDao: FreeNightDao) {
    val pointsPerReward = 4

    fun getPointsFor(accountId: Int): List<LoyaltyPoint> {
        return pointDao.getPointsFor(accountId)
    }

    fun addPoints(points: List<LoyaltyPoint>) {

        pointDao.getPointsFor(*getAccountIds(points))
                .union(points)
                .filter { it.freeNightId == null }
                .groupBy { it.accountId }
                .map { (accountId, points) ->
                    points
                        .chunked(pointsPerReward)
                        .filter { it.size == pointsPerReward }
                        .map { (freeNightDao.createFreeNight(points, accountId) to points) }
                        .onEach { (freeNight, points) ->
                            pointDao.upsertPoints(points.map { it.copy(freeNightId = freeNight.id) })
                        }
                }
    }

    fun getFreeNights(accountId: Int): List<FreeNight> {
        return freeNightDao.getFreeNights(accountId)
    }

    private fun getAccountIds(points: List<LoyaltyPoint>): IntArray {
        return points.map { it.accountId }.toIntArray()
    }

    fun redeemFreeNights(accountId: Int, freeNightId: UUID) {
        freeNightDao.markAsRedeemed(freeNightId)
    }
}

