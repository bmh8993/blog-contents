package discountpolicy

import DiscountPolicy
import Product
import java.time.LocalDate

class ExpirationDiscountPolicy : DiscountPolicy {
    override val discountPercent: Double = 0.7

    override fun isSatisfiedBy(product: Product): Boolean {
        return product.isExpiringSoon(LocalDate.now())
    }
}
