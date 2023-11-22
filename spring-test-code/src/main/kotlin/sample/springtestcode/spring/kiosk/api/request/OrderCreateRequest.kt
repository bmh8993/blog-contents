package sample.springtestcode.spring.kiosk.api.request

import jakarta.validation.constraints.NotEmpty

data class OrderCreateRequest(
    @field:NotEmpty(message = "주문하려는 상품번호는 필수입니다.")
    val productNumbers: List<String>,
)
