class Customer(
    val name: String,
) {
    private val card = Card()
    private val cart = mutableListOf<Pair<Product, Int>>()

    fun addToCart(product: Product, quantity: Int) {
        cart.add(product to quantity)
    }

    fun requestCheckout(martOwner: MartOwner): Payment {
        return martOwner.createPayment(cart)
    }

    fun pay(martOwner: MartOwner, payment: Payment) {
        martOwner.processPayment(card, payment)
    }
}
