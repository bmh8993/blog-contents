package sample.springtestcode.spring.kiosk.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findAllBySellingStatusIn(sellingStatuses: List<ProductSellingStatus>): List<ProductEntity>

    fun findAllByProductNumberIn(productNumbers: List<String>): List<ProductEntity>

    @Query("select p.product_number from tb_product p order by id desc limit 1", nativeQuery = true)
    fun findLatestProductNumber(): String?
}
