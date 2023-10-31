package sample.springtestcode.spring.kiosk.service

import org.springframework.stereotype.Service
import sample.springtestcode.spring.kiosk.api.dto.ProductResDto
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun getSellingProducts(): List<ProductResDto> {
        val products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
        return products.map { ProductResDto.of(it) }
    }
}
