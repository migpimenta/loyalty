package com.hotels.loyalty.controllers

import com.hotels.loyalty.Point
import com.hotels.loyalty.Redemption
import com.hotels.loyalty.Reward
import com.hotels.loyalty.services.LoyaltyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@ControllerAdvice
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
     * Redeems a reward. Would be preferable to use spring's @ExceptionHandler but using try catch to
     * demonstrate try as an expression.
     */
    @PostMapping("/account/{accountId}/ ")
    fun redeemRewards(@PathVariable accountId: Int, @RequestBody redemption: Redemption) : ResponseEntity<Any> {
        return try {
            loyaltyService.redeemReward(accountId, redemption.rewardId)
            ResponseEntity.noContent().build()
        } catch (ex: IllegalArgumentException) {
            ResponseEntity(ErrorDetails(Date(), ex.message), HttpStatus.BAD_REQUEST)
        }
    }

    data class ErrorDetails(val date: Date, val message: String?)
}

