package sample.springtestcode.spring.kiosk.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository

/**
 * @SpringBootTest: spring application을 실행시켜서 테스트가 가능하게된다.
 * @DataJpaTest: SpringBootTest보다 가볍다. 왜냐하면, JPA 관련된 Bean들만 주입해준다.
 */
// @SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
class ProductServiceTest {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    fun findAllBySellingStatusesIn() {
        // given
        val productEntity1 = ProductEntity(
            productNumber = "001",
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "아메리카노",
            price = 4000,
        )
        val productEntity2 = ProductEntity(
            productNumber = "002",
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.HOLD,
            name = "카페라떼",
            price = 4500,
        )
        val productEntity3 = ProductEntity(
            productNumber = "003",
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.STOP_SELLING,
            name = "팥빙수",
            price = 7000,
        )
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
}
