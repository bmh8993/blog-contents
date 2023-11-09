package sample.springtestcode.spring.kiosk.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.springtestcode.spring.kiosk.persistence.entity.OrderProductEntity

@Repository
interface OrderProductRepository : JpaRepository<OrderProductEntity, Long>