package sample.springtestcode.spring.kiosk.persistence.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import sample.springtestcode.spring.kiosk.persistence.code.OrderStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import java.time.LocalDateTime

class OrderEntityTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    fun calculateTotalPrice() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = OrderEntity.create(products, LocalDateTime.now())

        // then
        assertThat(order.totalPrice).isEqualTo(3000)
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT 이다.")
    @Test
    fun init() {
        // given
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = OrderEntity.create(products, LocalDateTime.now())

        // then
        assertThat(order.orderStatus).isEqualTo(OrderStatus.INIT)
    }

    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    @Test
    fun registeredDateTime() {
        // given
        val registeredDateTime = LocalDateTime.now()
        val products = listOf(
            createProduct("001", 1000),
            createProduct("002", 2000),
        )

        // when
        val order = OrderEntity.create(products, registeredDateTime)

        // then
        assertThat(order.registeredDateTime).isEqualTo(registeredDateTime)
    }

    private fun createProduct(productNumber: String, price: Int): ProductEntity {
        return ProductEntity(
            productNumber = productNumber,
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "메뉴 이름",
            price = price,
        )
    }
}
