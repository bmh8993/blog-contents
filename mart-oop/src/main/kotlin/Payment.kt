import java.time.LocalDateTime

class Payment private constructor(
    private val customerId: String,
    private val purchaseInfo: List<Pair<Product, Int>>,
    private val totalPrice: Int,
    private var paidAt: LocalDateTime?,
) {
    private val paymentId: String = "payment-${LocalDateTime.now()}"

    constructor(
        customerId: String,
        cart: List<Pair<Product, Int>>,
        totalPrice: Int,
    ) : this(customerId, cart, totalPrice, null)

    fun paid() {
        paidAt = LocalDateTime.now()
    }
}
