package com.example.springredis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class UserService(
    private val externalApiService: ExternalApiService,
    private val redisTemplate: StringRedisTemplate
) {

    fun getUserProfile(userId: String): UserProfile {
        var userName: String? = null
        val ops: ValueOperations<String, String> = redisTemplate.opsForValue()
        val cacheName = ops.get("nameKey: $userId")

        if (cacheName == null) {
            userName = externalApiService.getUserName(userId)
            ops.set("nameKey: $userId", userName, 5, TimeUnit.SECONDS)
        } else {
            userName = cacheName
        }

        val userAge = externalApiService.getUserAge(userId)

        return UserProfile(userName, userAge)
    }
}
