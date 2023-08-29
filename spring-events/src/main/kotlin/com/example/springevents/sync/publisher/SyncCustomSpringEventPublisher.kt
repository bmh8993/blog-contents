package com.example.springevents.sync.publisher

import com.example.springevents.sync.CustomSpringEvent
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SyncCustomSpringEventPublisher(
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishCustomEvent(message: String) {
        logger.info("publishing custom event. message: $message")

        val customSpringEvent = CustomSpringEvent(this, message)
        applicationEventPublisher.publishEvent(customSpringEvent)
    }
}
