package sample.springtestcode.unit.beverages

class Americano : Beverage {
    override fun getName(): String {
        return "아메리카노"
    }

    override fun getPrice(): Int {
        return 4000
    }
}
