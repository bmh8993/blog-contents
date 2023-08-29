package com.example.springevents

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(
    private val testService: TestService
) {

    @GetMapping("/sync")
    fun sync(): String {
        testService.sync()
        return "sync"
    }

    @GetMapping("/async")
    fun async(): String {
        testService.async()
        return "async"
    }
}