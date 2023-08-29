package com.example.springevents.sync.listener

import com.example.springevents.sync.CustomSpringEvent
import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SyncCustomSpringEventListener {
    @EventListener
    fun consume(event: CustomSpringEvent) {
        logger.info("received custom event by @EventHandler. message: ${event.message}")
    }
}

@Component
class SyncCustomSpringEventListenerUnder42 : ApplicationListener<CustomSpringEvent> {
    override fun onApplicationEvent(event: CustomSpringEvent) {
        logger.info("received custom event by ApplicationListener. message: ${event.message}")
    }
}
