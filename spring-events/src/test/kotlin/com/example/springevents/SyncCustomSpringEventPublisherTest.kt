package com.example.springevents

import com.example.springevents.sync.listener.SyncCustomSpringEventListener
import com.example.springevents.sync.listener.SyncCustomSpringEventListenerUnder42
import com.example.springevents.sync.publisher.SyncCustomSpringEventPublisher
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationEventPublisher

@SpringBootTest(
    classes = [
        SyncCustomSpringEventPublisher::class,
        SyncCustomSpringEventListener::class,
        SyncCustomSpringEventListenerUnder42::class,
    ],
)
class SyncCustomSpringEventPublisherTest {

    @MockBean
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Autowired
    private lateinit var publisher: SyncCustomSpringEventPublisher

    @Test
    fun `event 발행 및 소비 테스트`() {
        val message = "Test message"

        // 이벤트 발행 함수 호출.
        publisher.publishCustomEvent(message)
    }
}
