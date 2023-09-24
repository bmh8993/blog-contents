package sample.springtestcode.unit

import sample.springtestcode.unit.beverages.Beverage
import sample.springtestcode.unit.order.Order
import java.time.LocalDateTime

class CafeKiosk {

    val beverages: ArrayList<Beverage> = ArrayList()

    fun addBeverage(beverage: Beverage) {
        beverages.add(beverage)
    }

    fun addBeverage(beverage: Beverage, count: Int) {
        if (count < 1) {
            throw IllegalArgumentException("음료 개수는 1개 이상이어야 합니다.")
        }

        for (i in 1..count) {
            beverages.add(beverage)
        }
    }

    fun removeBeverage(beverage: Beverage) {
        beverages.remove(beverage)
    }

    fun clearBeverages() {
        beverages.clear()
    }

    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (beverage in beverages) {
            totalPrice += beverage.getPrice()
        }
        return totalPrice
    }

    fun createOrder(): Order {
        return Order(LocalDateTime.now(), beverages)
    }
}
