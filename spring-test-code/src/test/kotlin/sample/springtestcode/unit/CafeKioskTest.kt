package sample.springtestcode.unit

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import sample.springtestcode.unit.beverages.Americano
import sample.springtestcode.unit.beverages.Latte

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
}
