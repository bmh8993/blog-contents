package com.example.concurrency.facade

import com.example.concurrency.service.StockService
import org.springframework.stereotype.Component

@Component
class OptimisticLockStockFacade(
    private val stockService: StockService,
) {

    fun decrease(stockId: Long, qantity: Long) {
        while (true) {
            try {
                stockService.decreaseWithOptimisticLock(stockId, qantity)
                break
            } catch (e: Exception) {
                Thread.sleep(50)
            }
        }
    }
}
