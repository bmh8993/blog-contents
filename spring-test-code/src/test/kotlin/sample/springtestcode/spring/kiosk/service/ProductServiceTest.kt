package sample.springtestcode.spring.kiosk.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.api.request.ProductCreateRequest
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var productRepository: ProductRepository

    @AfterEach
    fun tearDown() {
        productRepository.deleteAllInBatch()
    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면. 상품번호는 001이다.")
    @Test
    fun createProductWhenProductIsEmpty() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "카푸치노",
            price = 5000,
        )

        // when
        val response = productService.createProduct(request)

        // then
        assertThat(response)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)

        val products = productRepository.findAll()
        assertThat(products).hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains(tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000))
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    @Test
    fun createProduct() {
        // given
        val productEntity1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        productRepository.saveAll(listOf(productEntity1))

        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "카푸치노",
            price = 5000,
        )

        // when
        val response = productService.createProduct(request)

        // then
        assertThat(response)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)

        val products = productRepository.findAll()
        assertThat(products).hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                Tuple.tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000),
                Tuple.tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000),
            )
    }

    private fun createProduct(
        productNumber: String,
        type: ProductType,
        sellingStatus: ProductSellingStatus,
        name: String,
        price: Int,
    ): ProductEntity {
        return ProductEntity(
            productNumber = productNumber,
            type = type,
            sellingStatus = sellingStatus,
            name = name,
            price = price,
        )
    }
}