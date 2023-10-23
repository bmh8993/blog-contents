package com.example.springevents.events.sub

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.AckMode
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header

private val logger = mu.KotlinLogging.logger {}

@Configuration
class UnorderedMessageSubConfig {

    val subscription: String = "test-unordered-topic-sub"

    @Bean("unorderedMessageInputChannel")
    fun orderingMessageInputChannel() = DirectChannel()

    @Bean
    fun unorderedMessageInboundChannelAdapter(
        @Qualifier("unorderedMessageInputChannel") inputChannel: MessageChannel,
        pubSubTemplate: PubSubTemplate
    ): PubSubInboundChannelAdapter {

        val adapter = PubSubInboundChannelAdapter(pubSubTemplate, subscription)

        adapter.outputChannel = inputChannel
        adapter.ackMode = AckMode.MANUAL
        adapter.payloadType = String::class.java
        return adapter
    }

    @ServiceActivator(inputChannel = "unorderedMessageInputChannel")
    fun messageReceiver(
        payload: String,
        @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
    ) {
        val orderingKey = message.pubsubMessage.orderingKey

        logger.info("UnorderedMessage: $payload, orderingKey: $orderingKey")

        try {
//            logger.info { "Consuming message: $payload" }
            message.ack()
        } catch (ex: Exception) {
            logger.error { ex.stackTraceToString() }
            message.nack()
        }
    }
}