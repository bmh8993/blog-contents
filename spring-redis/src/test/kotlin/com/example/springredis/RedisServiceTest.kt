package com.example.springredis

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class RedisServiceTest {

    @Autowired
    lateinit var redisService: RedisService

    @Test
    fun testBlockDuplicateCall() {
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        val futures = mutableListOf<Future<*>>()

        // 동시에 호출하면 하나만 성공하고 나머지는 실패한다.
        futures.add(executorService.submit { redisService.blockDuplicateCall() })
        futures.add(executorService.submit { redisService.blockDuplicateCall() })

        val throwableList: List<Throwable> = futures.mapNotNull { future ->
            try {
                future.get()
                null
            } catch (e: ExecutionException) {
                e.cause
            }
        }

        assertThat(throwableList).hasSize(1)
        assertThatThrownBy { throw throwableList[0] }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage("Duplicate call")
    }

    @Test
    fun testBlockDuplicateCallWithAnnotation() {
        val executorService: ExecutorService = Executors.newFixedThreadPool(2)
        val futures = mutableListOf<Future<*>>()

        // 동시에 호출하면 하나만 성공하고 나머지는 실패한다.
        futures.add(executorService.submit { redisService.blockDuplicateCallAnnotation() })
        futures.add(executorService.submit { redisService.blockDuplicateCallAnnotation() })

        val throwableList: List<Throwable> = futures.mapNotNull { future ->
            try {
                future.get()
                null
            } catch (e: ExecutionException) {
                e.cause
            }
        }

        assertThat(throwableList).hasSize(1)
        assertThatThrownBy { throw throwableList[0] }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage("Duplicate call")
    }
}
