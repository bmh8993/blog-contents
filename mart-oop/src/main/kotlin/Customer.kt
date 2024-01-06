
import java.util.UUID

class Customer(
    private val name: String,
) {
    private val customerId = "$name-${UUID.randomUUID()}"
    private val card = Card()
    private val cart = mutableListOf<CartItem>()

    fun addToCart(cartItem: CartItem) {
        cart.add(cartItem)
    }

    fun requestCheckout(martOwner: MartOwner) {
        return martOwner.createPayment(customerId, cart)
    }

    fun pay(martOwner: MartOwner) {
        martOwner.processPayment(customerId, card)
    }
}
