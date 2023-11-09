package sample.springtestcode.spring.kiosk.api.resreq

import sample.springtestcode.spring.kiosk.persistence.entity.OrderEntity
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val totalPrice: Int,
    val registeredDateTime: LocalDateTime,
    val products: List<ProductResponse>,
) {
    companion object {
        fun of(order: OrderEntity, products: List<ProductEntity>): OrderResponse {
            return OrderResponse(
                id = order.id!!,
                totalPrice = order.totalPrice,
                registeredDateTime = order.registeredDateTime,
                products = products.map { ProductResponse.of(it) },
            )
        }
    }
}
