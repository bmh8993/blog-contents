package com.example.springevents.events.sync

import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

private val logger = KotlinLogging.logger {}

@Component
class SyncCustomSpringEventListener {
    @EventListener
    fun consume(event: CustomSpringEvent) {
        logger.info("listen message ${event.message} // ${Thread.currentThread().name} // ${OffsetDateTime.now()}")
    }
}
