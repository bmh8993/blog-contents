package sample.springtestcode.spring.kiosk.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import sample.springtestcode.spring.kiosk.api.resreq.ProductResponse
import sample.springtestcode.spring.kiosk.service.ProductService

// @RequiredArgsConstructor
@RestController
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): List<ProductResponse> {
        return productService.getSellingProducts()
    }
}
