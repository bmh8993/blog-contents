package com.example.springevents.events.sub

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.AckMode
import com.google.cloud.spring.pubsub.integration.inbound.PubSubMessageSource
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageSource
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header

private val logger = KotlinLogging.logger {}

@Configuration
class SynchronousPullMessageSubConfig {

    val subscription: String = "pulling3-topic-sub"

    @Bean("synchronousPullMessageInputChannel")
    fun synchronousPullMessageInputChannel() = DirectChannel()

    @Bean
    @InboundChannelAdapter(channel = "synchronousPullMessageInputChannel")
    fun synchronousPullMessageInboundChannelAdapter(
        pubSubTemplate: PubSubTemplate
    ): MessageSource<Any> {
        val messageSource = PubSubMessageSource(pubSubTemplate, subscription)
        messageSource.setAckMode(AckMode.MANUAL)
        messageSource.setPayloadType(String::class.java)
        messageSource.setBlockOnPull(true)
        messageSource.maxFetchSize = 2
        return messageSource
    }

    @ServiceActivator(inputChannel = "synchronousPullMessageInputChannel")
    fun messageReceiver(
        payload: String,
        @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage
    ) {
        logger.info("SynchronousPullMessage: $payload")

        try {
            logger.info { "Consuming message: $payload" }
            message.ack()
        } catch (ex: Exception) {
            logger.error { ex.stackTraceToString() }
            message.nack()
        }
    }
}