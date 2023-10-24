package com.example.concurrency.service

import com.example.concurrency.domain.Stock
import com.example.concurrency.facade.LettuceLockStockFacade
import com.example.concurrency.facade.NamedLockStockFacade
import com.example.concurrency.facade.OptimisticLockStockFacade
import com.example.concurrency.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@Testcontainers
//@ActiveProfiles("test")
class StockServiceTest {

    var stockId = 0L

    @Autowired
    lateinit var stockService: StockService

    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var optimisticLockStockFacade: OptimisticLockStockFacade

    @Autowired
    lateinit var namedLockStockFacade: NamedLockStockFacade

    @Autowired
    lateinit var lettuceLockStockFacade: LettuceLockStockFacade

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
     * 장점: 충돌이 빈번하게 일어난다면 optimistic lock(낙관적 잠금)보다 성능이 좋다.
     * 락을 걸고 업데이트 하기 때문에 데이터 정합성이 보장된다.
     *
     * 단점: 락을 잡기 때문에 성능 감소가 있을 수 있다.
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

    /**
     * 해결방법: Optimistic Lock(낙관적 잠금) 사용
     * ---
     * optimistic lock은 실패 했을 시 재시도가 필요하다. (facade 구현)
     * ---
     * 장점: optimistic lock은 별도의 lock을 잡지 않으므로 pessimistic lock보다 성능이 좋다.
     * 단점: 실패 시 재시도 로직을 개발자가 직접 작성해야한다.
     *
     * 충돌이 빈번하다 > pessimistic lock
     * 충돌이 드물다 > optimistic lock
     */
    @Test
    fun decreaseWith100ThreadsV3() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    optimisticLockStockFacade.decrease(stockId, 1)
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
     * named lock
     * ---
     * 주의사항: named lock은 tx가 종료되더라도 lock이 자동으로 해제되지 않는다.
     * 별도의 명령으로 해제해야 한다. 또는 특정 시간이 지나면 자동으로 해제되도록 설정할 수 있다.
     */
    @Test
    fun decreaseWith100ThreadsV4() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    namedLockStockFacade.decrease(stockId, 1)
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

    @Test
    fun decreaseWith100ThreadsV5() {
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    lettuceLockStockFacade.decrease(stockId, 1)
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
