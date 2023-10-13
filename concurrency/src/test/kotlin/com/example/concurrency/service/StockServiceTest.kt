package com.example.concurrency.service

import com.example.concurrency.domain.Stock
import com.example.concurrency.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class StockServiceTest {

    @Autowired
    lateinit var stockService: StockService

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun before() {
        stockRepository.saveAndFlush(Stock(productId = 1, quantity = 100))
    }

    @AfterEach
    fun after() {
        stockRepository.deleteAll()
    }

    @Test
    fun decrease() {
        stockService.decrease(1, 1)

        // 100 - 1 = 99
        val stock = stockRepository.findById(1).orElseThrow()

        assertThat(stock.quantity).isEqualTo(99)
    }

    /**
     * race condition(경쟁 상태) 발생
     * 이를 해결하려면? 하나의 쓰레드 작업이 끝날 때까지 다른 쓰레드는 대기하도록 해야 함
     */
    @Test
    fun decreaseWith100Threads() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            try {
                executorService.submit {
                    stockService.decrease(1, 1)
                }
            } finally {
                latch.countDown()
            }
        }

        latch.await()

        val stock = stockRepository.findAll()[0]

        // 100 - 100 = 0
        assertThat(stock.quantity).isEqualTo(0)
    }
}
