package com.hotels.loyalty.services

import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.daos.PointDao
import org.springframework.stereotype.Service

@Service
class PointService(val pointDao: PointDao, val freeNightService: FreeNightService) {
    fun getPointsFor(accountId: Int): List<LoyaltyPoint> {
        return pointDao.getPointsFor(accountId)
    }

    fun addPoints(accountId: Int, points: List<LoyaltyPoint>) {
        pointDao.upsertPoints(points)

        pointDao.getPointsFor(accountId)
                .filter { it.freeNightId == null }
                .chunked(4)
                .filter { it.size == 4 }
                .map{ (freeNightService.createFreeNight(points) to points) }
                .onEach { (freeNight,points) ->
                    pointDao.upsertPoints(points.map{ it.copy(freeNightId = freeNight.id) })
                }
    }
}