package com.example.springevents.events.async

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

private val logger = mu.KotlinLogging.logger {}

@Component
class AsyncCustomSpringEventPublisher(
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishCustomEvent(message: String) {
        logger.info("publishing message $message // ${Thread.currentThread().name} // ${OffsetDateTime.now()}")

        val customSpringEvent = AsyncCustomSpringEvent(this, message)
        applicationEventPublisher.publishEvent(customSpringEvent)
    }
}
