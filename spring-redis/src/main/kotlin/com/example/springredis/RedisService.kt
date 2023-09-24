package com.example.springredis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service

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
}
