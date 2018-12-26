package com.hotels.loyalty.controllers

import com.hotels.loyalty.Point
import com.hotels.loyalty.services.LoyaltyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoyaltyPointController(val loyaltyService: LoyaltyService) {

    /**
     * Creates loyalty points.
     */
    @PostMapping("/points")
    fun addPoints(@RequestBody points: List<Point>) {
        loyaltyService.addPoints(points)
    }
}

