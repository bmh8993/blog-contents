package sample.springtestcode.spring.kiosk.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import sample.springtestcode.spring.common.persistence.repository.BaseEntity

@Entity
@Table(name = "tb_stock")
class StockEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val productNumber: String,
    var quantity: Int,
) : BaseEntity() {

    fun isQuantityEnough(productCount: Int): Boolean {
        return this.quantity - productCount >= 0
    }

    fun decreaseQuantity(productCount: Int) {
        if (isQuantityEnough(productCount).not()) throw IllegalArgumentException("차감할 재고 수량이 없습니다.")
        this.quantity -= productCount
    }

    companion object {
        fun create(productNumber: String, quantity: Int): StockEntity {
            return StockEntity(
                productNumber = productNumber,
                quantity = quantity,
            )
        }
    }
}
