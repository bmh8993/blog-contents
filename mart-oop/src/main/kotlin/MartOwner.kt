class MartOwner {

    private val paymentBox = mutableListOf<Payment>()

    fun createPayment(customerId: String, cart: List<CartItem>) {
        val totalPrice: Int = cart
            .map { cartItem -> cartItem.getTotalPrice() }
            .reduce { acc, price -> acc + price }

        val payment = Payment(customerId, cart, totalPrice)
        paymentBox.add(payment)
    }

    fun processPayment(customerId: String, card: Card) {
        findNotPaidPaymentBy(customerId)?.let { payment ->
            card.checkout(payment)
        } ?: throw IllegalStateException("계산할 내역이 없습니다.")
    }

    private fun findNotPaidPaymentBy(customerId: String): Payment? {
        return paymentBox.firstOrNull { payment ->
            payment.isPaid() && payment.isSameCustomer(customerId)
        }
    }
}
