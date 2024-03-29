package com.example.springredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SpringRedisApplication

fun main(args: Array<String>) {
    runApplication<SpringRedisApplication>(*args)
}
