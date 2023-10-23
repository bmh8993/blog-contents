package com.example.springevents.test

import com.example.springevents.events.pub.OrderingMessagePubConfig
import com.example.springevents.events.pub.OrderingMessagePubGateway
import com.example.springevents.events.pub.UnorderedMessagePubGateway
import com.example.springevents.events.pub.SynchronousPullMessagePubGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@RestController
@RequestMapping("test")
class TestController(
    private val testService: TestService,
    private val streamingPullMessagePubGateway: UnorderedMessagePubGateway,
    private val synchronousPullMessagePubGateway: SynchronousPullMessagePubGateway,
    private val unorderedMessagePubGateway: UnorderedMessagePubGateway,
    private val orderingMessagePubGateway: OrderingMessagePubGateway
) {

    @GetMapping("/sync")
    fun sync(): String {
        testService.sync()
        return "sync"
    }

    @GetMapping("/async")
    fun async(): String {
        testService.async()
        return "async"
    }

    @PostMapping("/pubsub-adapter")
    fun publishTopic() {
        for (i in 1..10) {
            streamingPullMessagePubGateway.sendToPub("streaming pull message $i")
            synchronousPullMessagePubGateway.sendToPub("synchronous pull message $i")
        }
    }

    @PostMapping("/pubsub-unordered")
    fun publishUnorderedTopic() {
        val threadCount = 10
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    unorderedMessagePubGateway.sendToPub("unordered message $i")
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
    }

    @PostMapping("/pubsub-ordering")
    fun publishOrderingTopic() {
        val threadCount = 10
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    orderingMessagePubGateway.sendToPub("ordering message $i")
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
    }
}