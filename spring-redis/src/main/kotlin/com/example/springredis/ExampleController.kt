package com.example.springredis

import com.example.springredis.pv2.ExampleDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = mu.KotlinLogging.logger {}

@RestController
@RequestMapping("/example")
class ExampleController(
    private val redisService: RedisService,
    private val exampleService: ExampleService,
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

    @GetMapping("/cache")
    fun testCache(): ExampleDto {
        return exampleService.findByName("1234")
    }

    @GetMapping("/cache-v2")
    fun testCacheV2(): ExampleDtoV2 {
        return exampleService.findByNameV2("1234")
    }
}
