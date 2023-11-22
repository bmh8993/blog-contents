package sample.springtestcode.spring.kiosk.api.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

data class ProductCreateRequest(

    @field:NotNull(message = "상품 타입을 선택해주세요.")
    val type: ProductType,

    @field:NotNull(message = "판매 상태를 선택해주세요.")
    val sellingStatus: ProductSellingStatus,

    @field:NotBlank(message = "상품명을 입력해주세요.")
    val name: String,

    @field:Positive(message = "상품 가격은 0보다 커야합니다.")
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
