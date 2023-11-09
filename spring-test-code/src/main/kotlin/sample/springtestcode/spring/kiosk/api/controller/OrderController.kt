package sample.springtestcode.spring.kiosk.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sample.springtestcode.spring.kiosk.api.resreq.OrderCreateRequest
import sample.springtestcode.spring.kiosk.api.resreq.OrderResponse
import sample.springtestcode.spring.kiosk.service.OrderService
import java.time.LocalDateTime

@RestController
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping("/api/v1/orders/new")
    fun createOrder(
        @RequestBody request: OrderCreateRequest,
    ): OrderResponse {
        val registeredDateTime = LocalDateTime.now()
        return orderService.createOrder(request, registeredDateTime)
    }
}
