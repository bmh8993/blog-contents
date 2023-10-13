package com.example.concurrency.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val productId: Long,

    var quantity: Long
) {
    fun decrease(quantity: Long) {
        if (this.quantity < quantity) {
            throw RuntimeException("재고는 0개 미만이 될 수 없습니다.")
        }
        this.quantity -= quantity
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stock

        if (id != other.id) return false
        if (productId != other.productId) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + productId.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }

    override fun toString(): String {
        return "Stock(id=$id, productId=$productId, quantity=$quantity)"
    }
}
