package sample.springtestcode.spring.kiosk.api.resreq

import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

data class ProductResponse(
    val id: Long,
    val productNumber: String,
    val type: ProductType,
    val sellingStatus: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    companion object {
        fun of(productEntity: ProductEntity): ProductResponse {
            return ProductResponse(
                id = productEntity.id!!,
                productNumber = productEntity.productNumber,
                type = productEntity.type,
                sellingStatus = productEntity.sellingStatus,
                name = productEntity.name,
                price = productEntity.price,
            )
        }
    }
}
