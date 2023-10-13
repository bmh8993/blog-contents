package com.example.concurrency.service

import com.example.concurrency.domain.Stock
import com.example.concurrency.repository.StockRepository
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    /**
     * stock 조회
     * 재고 감소
     * 갱신된 값 저장
     */
    fun decrease(stockId: Long, qantity: Long) {
        val stock: Stock = stockRepository.findById(stockId).orElseThrow()
        stock.decrease(qantity)
        stockRepository.saveAndFlush(stock)
    }
}