package com.example.concurrency.facade

import com.example.concurrency.repository.NamedLockRepository
import com.example.concurrency.service.StockService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NamedLockStockFacade(
    private val namedLockRepository: NamedLockRepository,
    private val stockService: StockService
) {

    @Transactional
    fun decrease(stockId: Long, qantity: Long) {
        try {
            namedLockRepository.getLock(stockId.toString())
            stockService.decreaseNewTx(stockId, qantity)
        } finally {
            namedLockRepository.releaseLock(stockId.toString())
        }
    }
}