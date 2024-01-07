import discountpolicy.NoDiscountPolicy
import java.time.LocalDate

class Product(
    private val name: String,
    private val price: Int,
    private val expireDate: LocalDate,
    private val discountPolicies: List<DiscountPolicy>,
) {
    fun isExpiringSoon(now: LocalDate): Boolean {
        return expireDate.minusDays(1).isEqual(now)
    }

    fun calculatePrice(quantity: Int): Int {
        val bestDiscountPolicy = getBestDiscountPolicy()
        val originalPrice = calculateOriginalPrice(quantity)
        val discountPrice = bestDiscountPolicy.calculateDiscountAmount(originalPrice, this)
        return originalPrice.minus(discountPrice)
    }

    private fun getBestDiscountPolicy(): DiscountPolicy {
        return discountPolicies
            .filter { it.isSatisfiedBy(this) }
            .maxByOrNull { it.discountPercent }
            ?: NoDiscountPolicy()
    }

    private fun calculateOriginalPrice(quantity: Int): Int {
        return this.price.times(quantity)
    }
}
