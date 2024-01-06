import java.time.LocalDate

class Mart {

    companion object {
        fun main() {
            val customer = Customer("제이든")
            val martOwner = MartOwner()

            val cola = Product("콜라", 1000, LocalDate.now().plusDays(1))
            val ramen = Product("라면", 3000, LocalDate.now().plusDays(2))
            val beer = Product("맥주", 5000, LocalDate.now().plusDays(3))
            val milk = Product("우유", 2000, LocalDate.now().plusDays(4))
            val bread = Product("식빵", 1000, LocalDate.now().plusDays(5))
            val egg = Product("계란", 2000, LocalDate.now().plusDays(6))
            val butter = Product("버터", 3000, LocalDate.now().plusDays(7))
            val chips = Product("감자칩", 1000, LocalDate.now().plusDays(8))
            val chocolate = Product("초콜릿", 2000, LocalDate.now().plusDays(9))
            val iceCream = Product("아이스크림", 3000, LocalDate.now().plusDays(10))
            val candy = Product("사탕", 1000, LocalDate.now().plusDays(11))
            val gum = Product("껌", 2000, LocalDate.now().plusDays(12))
            val snack = Product("과자", 3000, LocalDate.now().plusDays(13))
            val water = Product("생수", 1000, LocalDate.now().plusDays(14))
            val juice = Product("주스", 2000, LocalDate.now().plusDays(15))
            val tea = Product("차", 3000, LocalDate.now().plusDays(16))
            val coffee = Product("커피", 1000, LocalDate.now().plusDays(17))
            val energyDrink = Product("에너지드링크", 2000, LocalDate.now().plusDays(18))

            customer.addToCart(cola, 2)
            customer.addToCart(ramen, 1)

            val payment = customer.requestCheckout(martOwner)

            customer.pay(martOwner, payment)
        }
    }
}
