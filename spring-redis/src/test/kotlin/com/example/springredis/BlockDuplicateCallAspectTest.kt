package com.example.springredis

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BlockDuplicateCallAspectTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `test block duplicate call annotation`() {
        // given
        val executorService = Executors.newFixedThreadPool(2)
        val futures = mutableListOf<Future<*>>()

        val task = Callable {
            mockMvc.perform(get("/example/block-duplicate-call-annotation"))
                .andExpect(status().isOk) // 성공적으로 응답이 왔다면 HTTP 상태 코드 200 확인
                .andReturn()
        }

        // 동시에 두 번의 요청을 보냅니다.
        futures.add(executorService.submit(task))
        futures.add(executorService.submit(task))

        // when
        val throwableList: List<Throwable> = futures.mapNotNull { future ->
            try {
                future.get() // Future에서 결과값 가져오기
                null
            } catch (e: ExecutionException) {
                e.cause // 예외 발생 시 그 예외 반환
            }
        }

        // then
        assertThat(throwableList).hasSize(1)
        assertThatThrownBy { throw throwableList[0].cause!! }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage("Duplicate call")
    }
}
