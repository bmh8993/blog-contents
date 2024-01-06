class MartOwner {
    fun createPayment(cart: List<Pair<Product, Int>>): Payment {
        val totalPrice: Int = cart
            .map { (product, int) -> product.price * int }
            .reduce { acc, price -> acc + price }
        return Payment(cart, totalPrice)
    }

    fun processPayment(card: Card, payment: Payment) {
        card.checkout(payment)
    }
}
