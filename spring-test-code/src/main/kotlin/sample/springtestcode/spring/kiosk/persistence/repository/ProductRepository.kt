package sample.springtestcode.spring.kiosk.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findAllBySellingStatusIn(sellingStatuses: List<ProductSellingStatus>): List<ProductEntity>
}
