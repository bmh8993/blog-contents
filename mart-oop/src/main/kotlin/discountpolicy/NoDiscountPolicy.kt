package discountpolicy

import DiscountPolicy
import Product

class NoDiscountPolicy : DiscountPolicy {
    override val discountPercent: Double = 0.0

    override fun isSatisfiedBy(product: Product): Boolean {
        return false
    }

    override fun calculateDiscountAmount(originalPrice: Int, product: Product): Int {
        return 0
    }
}
