package sample.springtestcode.spring.kiosk.persistence.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @AfterEach
    fun tearDown() {
        productRepository.deleteAllInBatch()
    }

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    fun findAllBySellingStatusesIn() {
        // given
        val productEntity1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000)
        val productEntity2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500)
        val productEntity3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        // when
        val products = productRepository.findAllBySellingStatusIn(listOf(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD))

        // then ⭐️list 검증은 아래를 표준으로 잡자.
        Assertions.assertThat(products).hasSize(2)
            .extracting("productNumber", "sellingStatus", "name")
            .containsExactlyInAnyOrder(
                Assertions.tuple("001", ProductSellingStatus.SELLING, "아메리카노"),
                Assertions.tuple("002", ProductSellingStatus.HOLD, "카페라떼"),
            )
    }

    @DisplayName("상품번호를 가진 상품들을 조회한다.")
    @Test
    fun findAllByProductNumberIn() {
        // given
        val productEntity1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000,)
        val productEntity2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500)
        val productEntity3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        // when
        val products = productRepository.findAllByProductNumberIn(listOf("001", "002"))

        // then ⭐️list 검증은 아래를 표준으로 잡자.
        Assertions.assertThat(products).hasSize(2)
            .extracting("productNumber", "sellingStatus", "name")
            .containsExactlyInAnyOrder(
                Assertions.tuple("001", ProductSellingStatus.SELLING, "아메리카노"),
                Assertions.tuple("002", ProductSellingStatus.HOLD, "카페라떼"),
            )
    }

    @DisplayName("가장 최근에 저장된 상품의 상품번호를 조회한다.")
    @Test
    fun findLatestProductNumber() {
        // given
        val productEntity1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000,)
        val productEntity2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.HOLD, "카페라떼", 4500)
        val targetProductNumber = "003"
        val productEntity3 = createProduct(targetProductNumber, ProductType.HANDMADE, ProductSellingStatus.STOP_SELLING, "팥빙수", 7000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then ⭐️list 검증은 아래를 표준으로 잡자.
        Assertions.assertThat(latestProductNumber).isEqualTo(targetProductNumber)
    }

    @DisplayName("가장 최근에 저장된 상품의 상품번호를 조회할 때, 상품이 하나도 없는 경우 null을 반환한다.")
    @Test
    fun findLatestProductNumberWhenProductIsEmpty() {
        // given

        // when
        val latestProductNumber = productRepository.findLatestProductNumber()

        // then ⭐️list 검증은 아래를 표준으로 잡자.
        Assertions.assertThat(latestProductNumber).isNull()
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
