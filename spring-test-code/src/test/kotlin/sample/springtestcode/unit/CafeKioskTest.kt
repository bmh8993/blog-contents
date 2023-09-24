package sample.springtestcode.unit

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import sample.springtestcode.unit.beverages.Americano
import sample.springtestcode.unit.beverages.Latte
import java.time.LocalDateTime

class CafeKioskTest {

    @Test
    fun add_manual_test() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.addBeverage(Americano())

        println(">>> 담긴 음료 수 : ${cafeKiosk.beverages.size}")
        println(">>> 담긴 음료 : ${cafeKiosk.beverages[0].getName()}")
    }

    @Test
    fun add() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.addBeverage(Americano())

        assertThat(cafeKiosk.beverages.size).isEqualTo(1)
        assertThat(cafeKiosk.beverages[0].getName()).isEqualTo("아메리카노")
    }

    @Test
    fun addSeveralBeverages() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.addBeverage(americano, 2)

        assertThat(cafeKiosk.beverages.size).isEqualTo(2)
        assertThat(cafeKiosk.beverages[0]).isEqualTo(americano)
        assertThat(cafeKiosk.beverages[1]).isEqualTo(americano)
    }

    @Test
    fun addZeroBeverages() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        assertThatThrownBy { cafeKiosk.addBeverage(americano, 0) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("음료 개수는 1개 이상이어야 합니다.")
    }

    @Test
    fun remove() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()

        cafeKiosk.addBeverage(americano)
        assertThat(cafeKiosk.beverages).hasSize(1)

        cafeKiosk.removeBeverage(americano)
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    @Test
    fun clear() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.addBeverage(americano)
        cafeKiosk.addBeverage(latte)
        assertThat(cafeKiosk.beverages).hasSize(2)

        cafeKiosk.clearBeverages()
        assertThat(cafeKiosk.beverages).isEmpty()
    }

    /**
     * createOrderV1은 테스트 코드를 동작시키는 시점에 따라 결과가 달라질 수 있습니다.
     * 즉, craeteOrderV1은 테스트하기 좋은 코드가 아니다.
     *
     * 현재 시간은 테스트 하기 어려운 영역에 속한다.
     */
    @Test
    fun createOrderV1() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.addBeverage(americano)
        cafeKiosk.addBeverage(latte)

        val order = cafeKiosk.createOrderV1()

        assertThat(order.beverages).hasSize(2)
        assertThat(order.beverages[0]).isEqualTo(americano)
        assertThat(order.beverages[1]).isEqualTo(latte)
    }

    /**
     * createOrderV2는 테스트하기 좋은 코드이다.
     * 테스트 코드를 동작시키는 시점에 따라 결과가 달라지지 않는다.
     *
     * 현재 시간은 제어 불가능한 영역인데, 이걸 제어 가능한 영역으로 바꿔준다.
     */
    @Test
    fun createOrderV2WithCurrentDateTime() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.addBeverage(americano)
        cafeKiosk.addBeverage(latte)

        val order = cafeKiosk.createOrderV2(LocalDateTime.of(2023, 9, 25, 10, 1))

        assertThat(order.beverages).hasSize(2)
        assertThat(order.beverages[0]).isEqualTo(americano)
        assertThat(order.beverages[1]).isEqualTo(latte)
    }

    @Test
    fun createOrderV2WithOutOpenTime() {
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.addBeverage(americano)
        cafeKiosk.addBeverage(latte)

        assertThatThrownBy { cafeKiosk.createOrderV2(LocalDateTime.of(2023, 9, 25, 9, 59)) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("주문 가능 시간이 아닙니다.")
    }
}
