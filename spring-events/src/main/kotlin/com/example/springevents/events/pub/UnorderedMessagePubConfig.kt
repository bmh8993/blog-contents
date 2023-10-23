package com.example.springevents.events.pub

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.PubSubHeaderMapper
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Configuration
class UnorderedMessagePubConfig {
    val topicName: String = "test-unordered-topic"

    @Bean
    fun unorderedMessageOutputChannel() = DirectChannel()

    @Bean
    @ServiceActivator(inputChannel = "unorderedMessageOutputChannel")
    fun unorderedMessageSender(
        pubSubTemplate: PubSubTemplate
    ): MessageHandler {

        val adapter = PubSubMessageHandler(pubSubTemplate, topicName)

        adapter.setSuccessCallback { ackId: String, message: Message<*> ->
            logger.info(
                "unorderedMessageOutputChannel: Message sent $ackId of $message"
            )
        }

        adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
            logger.info(
                "unorderedMessageOutputChannel: Error sending $message due to $cause"
            )
        }

        return adapter;
    }
}

@Component
@MessagingGateway(defaultRequestChannel = "unorderedMessageOutputChannel")
interface UnorderedMessagePubGateway {
    fun sendToPub(
        message: String,
    )
}
