class MartOwner {

    private val paymentBox = mutableListOf<Payment>()

    fun createPayment(customerId: String, cart: List<Pair<Product, Int>>): Payment {
        val totalPrice: Int = cart
            .map { (product, int) -> product.price * int }
            .reduce { acc, price -> acc + price }
        val payment = Payment(customerId, cart, totalPrice)
        paymentBox.add(payment)

        return payment
    }

    fun processPayment(card: Card, payment: Payment) {
        card.checkout(payment)
    }
}
