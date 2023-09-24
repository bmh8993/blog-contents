package sample.springtestcode.unit.beverages

class Latte : Beverage {
    override fun getName(): String {
        return "라떼"
    }

    override fun getPrice(): Int {
        return 5000
    }
}
