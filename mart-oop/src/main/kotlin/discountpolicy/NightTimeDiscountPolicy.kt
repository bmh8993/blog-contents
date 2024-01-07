package discountpolicy

import DiscountPolicy
import Product
import java.time.LocalDateTime
import java.time.LocalTime

class NightTimeDiscountPolicy : DiscountPolicy {
    override val discountPercent: Double = 0.3

    override fun isSatisfiedBy(product: Product): Boolean {
        return isNightTime()
    }

    private fun isNightTime(): Boolean {
        val now = LocalDateTime.now()
        return now.isAfter(LocalDateTime.of(now.toLocalDate(), LocalTime.of(2, 0))) &&
            now.isBefore(LocalDateTime.of(now.toLocalDate(), LocalTime.of(5, 0)))
    }
}
