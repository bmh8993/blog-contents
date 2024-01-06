class CartItem(
    private val product: Product,
    private val quantity: Int,
) {
    fun getTotalPrice(): Int {
        return product.price * quantity
    }
}
