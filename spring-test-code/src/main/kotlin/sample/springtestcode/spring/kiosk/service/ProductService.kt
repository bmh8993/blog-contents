package sample.springtestcode.spring.kiosk.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.springtestcode.spring.kiosk.api.request.ProductCreateRequest
import sample.springtestcode.spring.kiosk.api.response.ProductResponse
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun getSellingProducts(): List<ProductResponse> {
        val products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
        return products.map { ProductResponse.of(it) }
    }

    // TODO: 동시성 처리
    @Transactional
    fun createProduct(request: ProductCreateRequest): ProductResponse {
        val nextProductNumber = createNextProductNumber()

        val savedProduct = productRepository.save(
            request.toEntity(nextProductNumber)
        )

        return ProductResponse.of(savedProduct)
    }

    private fun createNextProductNumber(): String {
        val latestProductNumber = productRepository.findLatestProductNumber() ?: return "001"
        val nextProductNumber = latestProductNumber.toInt().plus(1)

        return String.format("%03d", nextProductNumber)
    }
}
