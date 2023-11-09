package sample.springtestcode.spring.kiosk.persistence.entity

import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import sample.springtestcode.spring.common.persistence.repository.BaseEntity

@Entity
@Table(name = "tb_order_product")
class OrderProductEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    val order: OrderEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    val product: ProductEntity,
) : BaseEntity() {
    constructor(order: OrderEntity, product: ProductEntity) : this(null, order, product)
}
