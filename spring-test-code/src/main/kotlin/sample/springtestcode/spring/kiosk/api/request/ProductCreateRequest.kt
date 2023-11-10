package sample.springtestcode.spring.kiosk.api.request

import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

data class ProductCreateRequest(
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    fun toEntity(productNumber: String): ProductEntity {
        return ProductEntity(
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price,
            productNumber = productNumber,
        )
    }
}
