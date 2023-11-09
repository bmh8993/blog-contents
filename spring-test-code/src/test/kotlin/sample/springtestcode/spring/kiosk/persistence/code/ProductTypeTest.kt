package sample.springtestcode.spring.kiosk.persistence.code

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.(false)")
    @Test
    fun isNotStockType() {
        // given
        val givenType = ProductType.HANDMADE

        // when
        val result = givenType.isStockType()

        // then
        assertThat(result).isFalse()
    }

    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.(true)")
    @Test
    fun isStockType() {
        // given
        val givenType = ProductType.BAKERY

        // when
        val result = givenType.isStockType()

        // then
        assertThat(result).isTrue()
    }
}
