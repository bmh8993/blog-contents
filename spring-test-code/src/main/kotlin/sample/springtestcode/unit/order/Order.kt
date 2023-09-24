package sample.springtestcode.unit.order

import sample.springtestcode.unit.beverages.Beverage
import java.time.LocalDateTime

class Order(
    val orderDateTime: LocalDateTime,
    val beverages: List<Beverage>,
)
