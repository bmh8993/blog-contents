package com.example.concurrency.service

import com.example.concurrency.domain.Stock
import com.example.concurrency.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository,
) {
    /**
     * stock 조회
     * 재고 감소
     * 갱신된 값 저장
     */
    @Transactional
    fun decrease(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findById(stockId).orElseThrow()
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }

    @Synchronized
    fun synchronizedDecrease(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findById(stockId).orElseThrow()
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }

    @Transactional
    fun decreaseWithPessimisticLock(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findByIdWithPessimisticLock(stockId)
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }

    @Transactional
    fun decreaseWithOptimisticLock(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findByIdWithOptimisticLock(stockId)
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun decreaseNewTx(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findById(stockId).orElseThrow()
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }
}
