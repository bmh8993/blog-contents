package sample.springtestcode.spring.kiosk.persistence.repository

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import sample.springtestcode.spring.kiosk.persistence.entity.StockEntity

@ActiveProfiles("test")
@SpringBootTest
class StockRepositoryTest {

    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @AfterEach
    fun tearDown() {
        stockRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
    }

    @DisplayName("상품 번호에 해당하는 재고를 조회한다.")
    @Test
    fun findAllByProductNumberIn() {
        // given
        val stockEntity1 = StockEntity.create("001", 1)
        val stockEntity2 = StockEntity.create("002", 2)
        val stockEntity3 = StockEntity.create("003", 3)
        stockRepository.saveAll(listOf(stockEntity1, stockEntity2, stockEntity3))

        // when
        val stocks = stockRepository.findAllByProductNumberIn(listOf("001", "002"))

        // then
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 1),
                tuple("002", 2),
            )
    }

    private fun createProduct(type: ProductType, productNumber: String, price: Int): ProductEntity {
        return ProductEntity(
            productNumber = productNumber,
            type = type,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "메뉴 이름",
            price = price,
        )
    }
}
