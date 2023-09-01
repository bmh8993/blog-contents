package com.example.springevents.test

import com.example.springevents.events.pub.StreamingPullMessagePubGateway
import com.example.springevents.events.pub.SynchronousPullMessagePubGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(
    private val testService: TestService,
    private val streamingPullMessagePubGateway: StreamingPullMessagePubGateway,
    private val synchronousPullMessagePubGateway: SynchronousPullMessagePubGateway
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
}