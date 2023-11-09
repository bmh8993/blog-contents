package sample.springtestcode.spring.kiosk.persistence.entity

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.persistence.repository.StockRepository

@ActiveProfiles("test")
@SpringBootTest
class StockEntityTest {

    @Autowired
    lateinit var stockRepository: StockRepository

    @DisplayName("재고가 충분한지 체크한다.")
    @Test
    fun isQuantityEnough() {
        // given
        val stockEntity = StockEntity.create("001", 1)
        stockRepository.save(stockEntity)

        val quantity = 1

        // when
        val result = stockEntity.isQuantityEnough(quantity)

        // then
        assertThat(result).isTrue()
    }

    @DisplayName("재고를 주어진 개수만큼 감소시킨다.")
    @Test
    fun decreaseQuantity() {
        // given
        val stockEntity = StockEntity.create("001", 1)
        stockRepository.save(stockEntity)

        val quantity = 1

        // when
        stockEntity.decreaseQuantity(quantity)

        // then
        assertThat(stockEntity.quantity).isZero()
    }

    @DisplayName("재고보다 많은 수의 수량을 감소시키는 경우 예외가 발생한다.")
    @Test
    fun isNotQuantityEnoughThrowError() {
        // given
        val stockEntity = StockEntity.create("001", 1)
        stockRepository.save(stockEntity)

        val quantity = 2

        // when // then
        assertThatThrownBy { stockEntity.decreaseQuantity(quantity) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("차감할 재고 수량이 없습니다.")
    }
}
