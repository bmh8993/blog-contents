package com.example.springredis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class RedisService(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun setString(key: String, value: String) {
        val ops: ValueOperations<String, String> = stringRedisTemplate.opsForValue()
        ops[key] = value
    }

    fun getString(key: String): String? {
        val ops: ValueOperations<String, String> = stringRedisTemplate.opsForValue()
        return ops[key]
    }

    fun blockDuplicateCall() {
        val key = "RedisService.blockDuplicateCall:${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"

        println("key: $key")

        val isSuccess = stringRedisTemplate.opsForValue().setIfAbsent(key, "value", Duration.ofMinutes(1))

        println("isSuccess: $isSuccess")

        if (isSuccess != true) {
            throw RuntimeException("Duplicate call")
        }

        Thread.sleep(5000) // 다른 작업 대신 sleep으로 대체

        stringRedisTemplate.unlink(key)
    }
}
