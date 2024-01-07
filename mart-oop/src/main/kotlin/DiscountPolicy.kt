interface DiscountPolicy {
    val discountPercent: Double
    fun isSatisfiedBy(product: Product): Boolean

    fun calculateDiscountAmount(originalPrice: Int, product: Product): Int {
        return if (isSatisfiedBy(product)) {
            originalPrice.times(discountPercent).toInt()
        } else {
            0
        }
    }
}
