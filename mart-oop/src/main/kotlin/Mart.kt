import discountpolicy.ExpirationDiscountPolicy
import discountpolicy.NightTimeDiscountPolicy
import java.time.LocalDate

class Mart {

    companion object {
        fun main() {
            val customer = Customer("제이든")
            val martOwner = MartOwner()

            val cola = Product("콜라", 1000, LocalDate.now().plusDays(1), listOf(ExpirationDiscountPolicy(), NightTimeDiscountPolicy()))
            val ramen = Product("라면", 3000, LocalDate.now().plusDays(2), listOf(ExpirationDiscountPolicy(), NightTimeDiscountPolicy()))

            customer.addToCart(CartItem(cola, 2))
            customer.addToCart(CartItem(ramen, 1))

            val payment = customer.requestCheckout(martOwner)

            customer.pay(martOwner)
        }
    }
}
