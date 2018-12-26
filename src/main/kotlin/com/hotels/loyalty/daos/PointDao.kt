package com.hotels.loyalty.daos

import com.hotels.loyalty.Point
import org.springframework.stereotype.Component

@Component
class PointDao {

    /** Map of point id to point **/
    val allPoints = listOf(
            Point(accountId = 50, usdValue = 100.0),
            Point(accountId = 50, usdValue = 100.0),
            Point(accountId = 50, usdValue = 200.0),
            Point(accountId = 10, usdValue = 400.0),
            Point(accountId = 10, usdValue = 500.0),
            Point(accountId = 2, usdValue = 1000.0)
    ).associateBy { it.id }.toMutableMap()

    fun getPointsFor( accountIds: List<Int>): List<Point> {
        return allPoints.values.filter { accountIds.contains(it.accountId) }
    }

    fun upsertPoints(points: List<Point>) {
        points.forEach { allPoints[it.id] =  it }
    }
}