package sample.springtestcode.unit.beverages

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class AmericanoTest {
    @Test
    fun getName() {
        val americano = Americano()

        assertThat(americano.getName()).isEqualTo("아메리카노")
    }

    @Test
    fun getPrice() {
        val americano = Americano()

        assertThat(americano.getPrice()).isEqualTo(4000)
    }
}
