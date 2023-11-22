package sample.springtestcode.spring.kiosk.api.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sample.springtestcode.spring.common.api.ApiResponse
import sample.springtestcode.spring.kiosk.api.request.OrderCreateRequest
import sample.springtestcode.spring.kiosk.api.response.OrderResponse
import sample.springtestcode.spring.kiosk.service.OrderService
import java.time.LocalDateTime

@RestController
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping("/api/v1/orders/new")
    fun createOrder(
        @Valid @RequestBody request: OrderCreateRequest,
    ): ApiResponse<OrderResponse> {
        val registeredDateTime = LocalDateTime.now()
        return ApiResponse.created(orderService.createOrder(request, registeredDateTime))
    }
}
