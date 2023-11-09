package sample.springtestcode.spring.kiosk.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.springtestcode.spring.kiosk.persistence.entity.StockEntity

@Repository
interface StockRepository : JpaRepository<StockEntity, Long> {
    fun findAllByProductNumberIn(productNumbers: List<String>): List<StockEntity>
}
