package com.example.springevents.events.pub

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Configuration
class SynchronousPullMessagePubConfig {
    val topicName: String = "pulling3-topic"

    @Bean
    fun synchronousPullMessageOutputChannel() = DirectChannel()

    @Bean
    @ServiceActivator(inputChannel = "synchronousPullMessageOutputChannel")
    fun synchronousPullMessageSender(
        pubSubTemplate: PubSubTemplate
    ): MessageHandler {
        val adapter = PubSubMessageHandler(pubSubTemplate, topicName)

        adapter.setSuccessCallback { ackId: String, message: Message<*> ->
            logger.info(
                "synchronousPullMessageOutputChannel: Message sent $ackId of $message"
            )
        }

        adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
            logger.info(
                "synchronousPullMessageOutputChannel: Error sending $message due to $cause"
            )
        }

        return adapter
    }
}

@Component
@MessagingGateway(defaultRequestChannel = "synchronousPullMessageOutputChannel")
interface SynchronousPullMessagePubGateway {
    fun sendToPub(message: String)
}
