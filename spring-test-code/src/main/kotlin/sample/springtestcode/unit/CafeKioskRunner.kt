package sample.springtestcode.unit

import sample.springtestcode.unit.beverages.Americano
import sample.springtestcode.unit.beverages.Latte

object CafeKioskRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        val cafeKiosk: CafeKiosk = CafeKiosk()

        cafeKiosk.addBeverage(Americano())
        println("아메리카노 추가")

        cafeKiosk.addBeverage(Latte())
        println("라떼 추가")

        val totalPrice: Int = cafeKiosk.calculateTotalPrice()
        println("총 가격: $totalPrice")
    }
}
