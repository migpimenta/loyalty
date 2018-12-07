package com.hotels.loyalty

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoyaltyServiceApplication

fun main(args: Array<String>) {
    runApplication<LoyaltyServiceApplication>(*args)
}
