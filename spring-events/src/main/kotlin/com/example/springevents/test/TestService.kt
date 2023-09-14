package com.example.springevents.test

import com.example.springevents.events.async.AsyncCustomSpringEventPublisher
import com.example.springevents.events.sync.SyncCustomSpringEventPublisher
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class TestService(
    private val asyncCustomSpringEventPublisher: AsyncCustomSpringEventPublisher,
    private val syncCustomSpringEventPublisher: SyncCustomSpringEventPublisher,
    private val applicationContext: ApplicationContext,
) {

    fun sync() {
        for (i in 1..10) {
            syncCustomSpringEventPublisher.publishCustomEvent("sync $i")
        }
    }

    fun async() {
        for (i in 1..10) {
            asyncCustomSpringEventPublisher.publishCustomEvent("async $i")
        }
    }

    private fun getSpringIOCBeanList() {
        applicationContext.beanDefinitionNames.forEach {
            println(it)
        }
    }
}
