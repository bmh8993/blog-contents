package com.example.concurrency.repository

import com.example.concurrency.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long>
