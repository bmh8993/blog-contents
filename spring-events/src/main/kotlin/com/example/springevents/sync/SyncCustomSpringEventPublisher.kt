package com.example.springevents.sync

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

private val logger = mu.KotlinLogging.logger {}

@Component
class SyncCustomSpringEventPublisher(
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishCustomEvent(message: String) {
        logger.info("publishing message $message // ${Thread.currentThread().name} // ${OffsetDateTime.now()}")

        val customSpringEvent = CustomSpringEvent(this, message)
        applicationEventPublisher.publishEvent(customSpringEvent)
    }
}
