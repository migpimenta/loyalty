package com.hotels.loyalty.controllers

import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.services.PointService
import org.springframework.web.bind.annotation.*

@RestController
class PointController(val pointService: PointService) {

    @GetMapping("/account/{accountId}/points")
    fun getPoints(@PathVariable accountId: Int): List<LoyaltyPoint> {
        return pointService.getPointsFor(accountId)
    }

    @PostMapping("/account/{accountId}/points")
    fun addPoint(@PathVariable accountId: Int, @RequestBody points: List<LoyaltyPoint>) {
        pointService.addPoints(accountId, points)
    }
}

