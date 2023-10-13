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

    var stockId = 0L

    @Autowired
    lateinit var stockService: StockService

    @Autowired
    lateinit var stockRepository: StockRepository

    @BeforeEach
    fun before() {
        val savedStock = stockRepository.saveAndFlush(Stock(productId = 1, quantity = 100))

        stockId = savedStock.id!!
    }

    @AfterEach
    fun after() {
        stockRepository.deleteAll()
    }

    @Test
    fun decrease() {
        stockService.decrease(stockId, 1)

        // 100 - 1 = 99
        val stock = stockRepository.findById(stockId).orElseThrow()

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
            executorService.submit {
                try {
                    stockService.decrease(stockId, 1)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        val stock = stockRepository.findById(stockId).orElseThrow()

        // 100 - 100 = 0
        assertThat(stock.quantity).isEqualTo(0)
    }

    /**
     * 해결방법 첫번째: stockService.decrease 메서드에 synchronized 처리
     * ---
     * @Transactional과 같이 사용하면 완전하게 동시성 처리가 안된다.
     * synchronized 처리는 메서드에 대한 lock이다. tx와 관련된 lock이 아니다.
     * 그렇기 때문에 해당 메서드에 lock이 해제되고, tx를 종료시키는 찰나에 다른 쓰레드가 해당 메서드에 접근할 수 있다.
     * ---
     * synchronized 처리의 문제점
     * - 각 프로세스에서만 보장된다. 그렇기 때문에 서버 머신 한 대에서는 동시성 처리가 되지만,
     * 서버가 두 대 이상이라면 불가능하다. 동시성을 보장하지 못한다.
     */
    @Test
    fun decreaseWith100ThreadsV1() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    stockService.synchronizedDecrease(stockId, 1)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        val stock = stockRepository.findById(stockId).orElseThrow()

        // 100 - 100 = 0
        assertThat(stock.quantity).isEqualTo(0)
    }

    /**
     * 해결방법: Pessismistic Lock(비관적 잠금) 사용
     * ---
     * 비관락의 작점은 충돌이 빈번하게 일어난다면 optimistic lock(낙관적 잠금)보다 성능이 좋다.
     * 락을 걸고 업데이트 하기 때문에 데이터 정합성이 보장된다.
     *
     * 단, 락을 잡기 때문에 성능 감소가 있을 수 있다.
     */
    @Test
    fun decreaseWith100ThreadsV2() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    stockService.decreaseWithPessimisticLock(stockId, 1)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        val stock = stockRepository.findById(stockId).orElseThrow()

        // 100 - 100 = 0
        assertThat(stock.quantity).isEqualTo(0)
    }
}
