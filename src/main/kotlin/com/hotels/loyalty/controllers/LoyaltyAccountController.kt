package com.hotels.loyalty.controllers

import com.hotels.loyalty.FreeNight
import com.hotels.loyalty.LoyaltyPoint
import com.hotels.loyalty.Redemption
import com.hotels.loyalty.services.LoyaltyService
import org.springframework.web.bind.annotation.*

@RestController
class LoyaltyAccountController(val loyaltyService: LoyaltyService) {

    /**
     * Retrieves all loyalty points for the account.
     */
    @GetMapping("/account/{accountId}/points")
    fun getPoints(@PathVariable accountId: Int): List<LoyaltyPoint> {
        return loyaltyService.getPointsFor(accountId)
    }

    /**
     * Retrieves all free nights for the accounts.
     */
    @GetMapping("/account/{accountId}/freenight")
    fun getFreeNights(@PathVariable accountId: Int) : List<FreeNight> {
        return loyaltyService.getFreeNights(accountId)
    }

    /**
     * Redeems a free night.
     */
    @PostMapping("/account/{accountId}/redemption")
    fun redeemFreeNights(@PathVariable accountId: Int, @RequestBody redemption: Redemption) {
        loyaltyService.redeemFreeNights(accountId, redemption.freeNightId)
    }
}

