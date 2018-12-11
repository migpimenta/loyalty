package com.hotels.loyalty.controllers

import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.services.LoyaltyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoyaltyPointController(val loyaltyService: LoyaltyService) {

    @PostMapping("/point")
    fun addPoint(@RequestBody points: List<LoyaltyPoint>) {
        loyaltyService.addPoints(points)
    }
}

