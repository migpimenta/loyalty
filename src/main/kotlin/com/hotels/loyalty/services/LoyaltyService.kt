package com.hotels.loyalty.services

import com.hotels.loyalty.FreeNight
import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.daos.FreeNightDao
import com.hotels.loyalty.daos.PointDao
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoyaltyService(val pointDao: PointDao, val freeNightDao: FreeNightDao) {
    val pointsPerFreeNight = 4

    /**
     * Creates loyalty points. In the process creates free nights by:
     * - Gathering all points not associated with a free night for accounts we are adding points.
     * - Chunk them in {@link #pointsPerFreeNight}.
     * - Create free night and saving it.
     * - update points with the new freeNightId.
     */
    fun addPoints(points: List<LoyaltyPoint>) {

        pointDao.getPointsFor(*getAccountIds(points))
                .filter { it.freeNightId == null }
                .union(points)
                .groupBy { it.accountId }
                .map { (accountId, points) ->
                    points
                        .chunked(pointsPerFreeNight)
                        .onEach {
                            // side-effect: persist points not associated with a free night
                            if (it.size < pointsPerFreeNight) {
                                pointDao.upsertPoints(points)
                            }
                        }
                        .filter { it.size == pointsPerFreeNight }
                        .map { (freeNightDao.createFreeNight(points, accountId) to points) }
                        .onEach { (freeNight, points) ->
                            //side-effect: persist points associated with the newly created freeNight
                            pointDao.upsertPoints(points.map { it.copy(freeNightId = freeNight.id) })
                        }
                }
    }

    fun getPointsFor(accountId: Int): List<LoyaltyPoint> {
        return pointDao.getPointsFor(accountId)
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

