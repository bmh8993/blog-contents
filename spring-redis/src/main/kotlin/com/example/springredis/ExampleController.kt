package com.example.springredis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = mu.KotlinLogging.logger {}

@RestController
@RequestMapping("/example")
class ExampleController(
    private val redisService: RedisService,
    private val userService: UserService,
) {

    @PostMapping("/fruit")
    fun setFruit(
        @RequestBody request: FruitSetRequest,
    ) {
        redisService.setString("fruit", request.name)
    }

    @GetMapping("/fruit")
    fun getFruit(): String? {
        return redisService.getString("fruit")
    }

    @GetMapping("/block-duplicate-call")
    fun testBlockDuplicateCall() {
        logger.info("start")
        redisService.blockDuplicateCall()
        logger.info("end")
    }

    @GetMapping("/block-duplicate-call-annotation")
    @BlockDuplicateCall
    fun testBlockDuplicateCallAnnotation() {
        logger.info("start")
        redisService.blockDuplicateCallAnnotation()
        logger.info("end")
    }

    @GetMapping("users/{userId}/profile")
    fun getUserProfile(
        @PathVariable("userId") userId: String,
    ): UserProfile {
        return userService.getUserProfile(userId)
    }
}
