package sample.springtestcode.spring.kiosk.persistence.code

enum class ProductType(
    val text: String,
) {
    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리"),
    ;

    fun isStockType(): Boolean {
        return this in listOf(BAKERY, BOTTLE)
    }
}
