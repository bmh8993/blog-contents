package sample.springtestcode.unit

import sample.springtestcode.unit.beverages.Beverage
import sample.springtestcode.unit.order.Order
import java.time.LocalDateTime
import java.time.LocalTime

class CafeKiosk {

    private val SHOP_OPEN_TIME: LocalTime = LocalTime.of(10, 0)
    private val SHOP_CLOSE_TIME: LocalTime = LocalTime.of(22, 0)

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

    fun createOrderV1(): Order {
        val currentDateTime = LocalDateTime.now()
        val currentTime: LocalTime = currentDateTime.toLocalTime()

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw IllegalStateException("주문 가능 시간이 아닙니다.")
        }

        return Order(currentDateTime, beverages)
    }

    fun createOrderV2(currentDateTime: LocalDateTime): Order {
        val currentTime: LocalTime = currentDateTime.toLocalTime()

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw IllegalStateException("주문 가능 시간이 아닙니다.")
        }

        return Order(currentDateTime, beverages)
    }
}
