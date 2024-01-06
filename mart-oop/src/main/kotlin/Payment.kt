import java.time.LocalDateTime

class Payment private constructor(
    val purchaseInfo: List<Pair<Product, Int>>,
    val totalPrice: Int,
    private var paidAt: LocalDateTime?,
) {
    constructor(cart: List<Pair<Product, Int>>, totalPrice: Int) : this(cart, totalPrice, null)
    fun paid() {
        paidAt = LocalDateTime.now()
    }
}
