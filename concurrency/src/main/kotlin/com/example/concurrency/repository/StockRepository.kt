package com.example.concurrency.repository

import com.example.concurrency.domain.Stock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface StockRepository : JpaRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // select for update
    @Query("select s from Stock s where s.id = :stockId")
    fun findByIdWithPessimisticLock(stockId: Long): Stock
}
