package com.example.springredis

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logger = mu.KotlinLogging.logger {}

@Aspect
@Component
class BlockDuplicateCallAspect(
    private val request: HttpServletRequest,
    private val stringRedisTemplate: StringRedisTemplate,
) {

    @Around("@annotation(com.example.springredis.BlockDuplicateCall)")
    fun blockDuplicateCall(joinPoint: ProceedingJoinPoint): Any? {
        val key = "${request.method}${request.requestURI}:${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"

        logger.info("key: $key")

        val isSuccess = stringRedisTemplate.opsForValue().setIfAbsent(key, "value", Duration.ofMinutes(1))

        logger.info("isSuccess: $isSuccess")

        if (isSuccess != true) {
            throw RuntimeException("Duplicate call")
        }

        val result = joinPoint.proceed()

        stringRedisTemplate.unlink(key)

        return result
    }
}
