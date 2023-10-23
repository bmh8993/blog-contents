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
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Configuration
class OrderingMessagePubConfig {
    val topicName: String = "test-ordering-topic"

    @Bean
    fun orderingMessageOutputChannel() = DirectChannel()

    @Bean
    @ServiceActivator(inputChannel = "orderingMessageOutputChannel")
    fun orderingMessageSender(
        pubSubTemplate: PubSubTemplate
    ): MessageHandler {

        val adapter = PubSubMessageHandler(pubSubTemplate, topicName)

        adapter.setSuccessCallback { ackId: String, message: Message<*> ->
            logger.info(
                "orderingMessageOutputChannel: Message sent $ackId of $message"
            )
        }

        adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
            logger.info(
                "orderingMessageOutputChannel: Error sending $message due to $cause"
            )
        }

        return adapter;
    }
}

/**
 * OrderingKey 지정은 아래에 있는 @Header(GcpPubSubHeaders.ORDERING_KEY) orderingKey: String = "test-ordering-key" 부분이다.
 */
@Component
@MessagingGateway(defaultRequestChannel = "orderingMessageOutputChannel")
interface OrderingMessagePubGateway {
    fun sendToPub(
        message: String,
        @Header(GcpPubSubHeaders.ORDERING_KEY) orderingKey: String = "test-ordering-key"
    )
}
