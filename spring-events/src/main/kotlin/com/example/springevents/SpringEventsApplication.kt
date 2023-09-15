package com.example.springevents

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
class SpringEventsApplication

fun main(args: Array<String>) {
    runApplication<SpringEventsApplication>(*args)
}
