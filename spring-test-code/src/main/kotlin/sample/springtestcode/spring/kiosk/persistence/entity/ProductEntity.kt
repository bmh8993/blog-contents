package sample.springtestcode.spring.kiosk.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import sample.springtestcode.spring.common.persistence.repository.BaseEntity
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType

// TODO
// - GenerationType
// - Kotlin - hibernate에서 어려움
// Enumerated
// NoArgsConstructor
// RequiredArgsConstructor
// @Column
@Table(name = "product")
@Entity
class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var productNumber: String,

    @Enumerated(EnumType.STRING)
    var type: ProductType,

    @Enumerated(EnumType.STRING)
    var sellingStatus: ProductSellingStatus,

    var name: String,

    var price: Int,
) : BaseEntity()
