package sample.springtestcode.spring.kiosk.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import sample.springtestcode.spring.common.persistence.repository.BaseEntity
import sample.springtestcode.spring.kiosk.persistence.code.OrderStatus
import java.time.LocalDateTime

// TODO: arrayList하고 List하고 뭐가 다른거지?

@Entity
@Table(name = "tb_order")
class OrderEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,

    val totalPrice: Int,

    val registeredDateTime: LocalDateTime,
) : BaseEntity() {

    companion object {
        fun create(products: List<ProductEntity>, registeredDateTime: LocalDateTime): OrderEntity {
            val totalPrice = calculateTotalPrice(products)
            return OrderEntity(
                orderStatus = OrderStatus.INIT,
                totalPrice = totalPrice,
                registeredDateTime = registeredDateTime,
            )
        }

        private fun calculateTotalPrice(products: List<ProductEntity>) =
            products.sumOf { it.price }
    }
}
