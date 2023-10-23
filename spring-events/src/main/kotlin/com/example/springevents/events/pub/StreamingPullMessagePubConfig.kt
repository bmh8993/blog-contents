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
class StreamingPullMessagePubConfig {
    val topicName: String = "test-streaming-topic"

    @Bean
    fun streamingPullMessageOutputChannel() = DirectChannel()

    @Bean
    @ServiceActivator(inputChannel = "streamingPullMessageOutputChannel")
    fun streamingPullMessageSender(
        pubSubTemplate: PubSubTemplate
    ): MessageHandler {
        val adapter = PubSubMessageHandler(pubSubTemplate, topicName)

        adapter.setSuccessCallback { ackId: String, message: Message<*> ->
            logger.info(
                "streamingPullMessageOutputChannel: Message sent $ackId of $message"
            )
        }

        adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
            logger.info(
                "streamingPullMessageOutputChannel: Error sending $message due to $cause"
            )
        }

        return adapter;
    }
}

@Component
@MessagingGateway(defaultRequestChannel = "streamingPullMessageOutputChannel")
interface StreamingPullMessagePubGateway {
    fun sendToPub(message: String)
}
