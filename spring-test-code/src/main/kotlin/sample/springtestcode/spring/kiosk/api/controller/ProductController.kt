package sample.springtestcode.spring.kiosk.api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sample.springtestcode.spring.common.api.ApiResponse
import sample.springtestcode.spring.kiosk.api.request.ProductCreateRequest
import sample.springtestcode.spring.kiosk.api.response.ProductResponse
import sample.springtestcode.spring.kiosk.service.ProductService

// @RequiredArgsConstructor
@RestController
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/api/v1/products/selling")
    fun getSellingProducts(): ApiResponse<List<ProductResponse>> {
        return ApiResponse.ok(productService.getSellingProducts())
    }

    @PostMapping("/api/v1/products/new")
    fun createProduct(
        @Valid @RequestBody request: ProductCreateRequest,
    ): ApiResponse<ProductResponse> {
        return ApiResponse.created(productService.createProduct(request))
    }
}
