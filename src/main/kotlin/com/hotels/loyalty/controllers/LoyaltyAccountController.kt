package com.hotels.loyalty.controllers

import com.hotels.loyalty.Reward
import com.hotels.loyalty.Point
import com.hotels.loyalty.Redemption
import com.hotels.loyalty.services.LoyaltyService
import org.springframework.web.bind.annotation.*

@RestController
class LoyaltyAccountController(val loyaltyService: LoyaltyService) {

    /**
     * Retrieves all loyalty points for the account.
     */
    @GetMapping("/account/{accountId}/points")
    fun getPoints(@PathVariable accountId: Int): List<Point> {
        return loyaltyService.getPointsFor(accountId)
    }

    /**
     * Retrieves all rewards for the accounts.
     */
    @GetMapping("/account/{accountId}/rewards")
    fun getRewards(@PathVariable accountId: Int) : List<Reward> {
        return loyaltyService.getRewards(accountId)
    }

    /**
     * Redeems a reward.
     */
    @PostMapping("/account/{accountId}/redemption")
    fun redeemRewards(@PathVariable accountId: Int, @RequestBody redemption: Redemption) {
        loyaltyService.redeemReward(accountId, redemption.rewardId)
    }
}

