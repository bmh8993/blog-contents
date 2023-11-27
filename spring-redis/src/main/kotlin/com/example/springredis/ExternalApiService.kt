package com.example.springredis

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ExternalApiService {

    fun getUserName(userId: String): String {
        Thread.sleep(500)

        println("Getting user name from external API")

        return when (userId) {
            "A" -> "Adam"
            "B" -> "Bob"
            else -> "John"
        }
    }

    @Cacheable(
        cacheNames = ["userAgeCache"],
        key = "#userId",
    )
    fun getUserAge(userId: String): Int {
        Thread.sleep(500)

        println("Getting user age from external API")

        return when (userId) {
            "A" -> 20
            "B" -> 30
            else -> 40
        }
    }
}
