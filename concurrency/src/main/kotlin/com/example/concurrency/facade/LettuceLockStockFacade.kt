package com.example.concurrency.facade

import com.example.concurrency.repository.RedisLockRepository
import com.example.concurrency.service.StockService
import org.springframework.stereotype.Component

@Component
class LettuceLockStockFacade(
    private val redisLockRepository: RedisLockRepository,
    private val stockService: StockService,
) {
    fun decrease(stockId: Long, quantity: Long) {
        while (redisLockRepository.lock(stockId.toString()).not()) {
            Thread.sleep(100)
        }

        try {
            stockService.decrease(stockId, quantity)
        } finally {
            redisLockRepository.unlock(stockId.toString())
        }
    }
}
