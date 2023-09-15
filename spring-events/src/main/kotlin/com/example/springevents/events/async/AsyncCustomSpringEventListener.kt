package com.example.springevents.events.async

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import java.time.OffsetDateTime

private val logger = mu.KotlinLogging.logger {}

@Component
class AsyncCustomSpringEventListener {

    @Async
    @EventListener
//    @TransactionalEventListener
    fun consume(event: AsyncCustomSpringEvent) {
        logger.info("listen message ${event.message} // ${Thread.currentThread().name} // ${OffsetDateTime.now()}") //
    }
}
