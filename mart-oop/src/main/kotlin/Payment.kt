import java.time.LocalDateTime

class Payment private constructor(
    val customerId: String,
    val purchaseInfo: List<Pair<Product, Int>>,
    val totalPrice: Int,
    private var paidAt: LocalDateTime?,
) {
    val paymentId: String = "payment-${LocalDateTime.now()}"

    constructor(
        customerId: String,
        cart: List<Pair<Product, Int>>,
        totalPrice: Int,
    ) : this(customerId, cart, totalPrice, null)

    fun paid() {
        paidAt = LocalDateTime.now()
    }
}
