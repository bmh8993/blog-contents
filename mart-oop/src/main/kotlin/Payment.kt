import java.time.LocalDateTime

class Payment private constructor(
    private val customerId: String,
    private val purchaseInfo: List<CartItem>,
    private val totalPrice: Int,
    private var paidAt: LocalDateTime?,
) {
    private val paymentId: String = "payment-${LocalDateTime.now()}"

    constructor(
        customerId: String,
        cart: List<CartItem>,
        totalPrice: Int,
    ) : this(customerId, cart, totalPrice, null)

    fun paid() {
        paidAt = LocalDateTime.now()
    }

    fun isPaid(): Boolean {
        return paidAt != null
    }

    fun isSameCustomer(customerId: String): Boolean {
        return this.customerId == customerId
    }
}
