package com.example.springredis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/example")
class ExampleController(
    private val redisService: RedisService,
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
        println("start")
        redisService.blockDuplicateCall()
        println("end")
    }
}
