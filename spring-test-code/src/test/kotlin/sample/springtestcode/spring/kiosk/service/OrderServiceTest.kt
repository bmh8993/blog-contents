package sample.springtestcode.spring.kiosk.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.springtestcode.spring.kiosk.api.resreq.OrderCreateRequest
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import sample.springtestcode.spring.kiosk.persistence.entity.StockEntity
import sample.springtestcode.spring.kiosk.persistence.repository.OrderProductRepository
import sample.springtestcode.spring.kiosk.persistence.repository.OrderRepository
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository
import sample.springtestcode.spring.kiosk.persistence.repository.StockRepository
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var orderProductRepository: OrderProductRepository

    @Autowired
    lateinit var stockRepository: StockRepository

    @AfterEach
    fun tearDown() {
        productRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
        orderProductRepository.deleteAllInBatch()
        stockRepository.deleteAllInBatch()
    }

    @DisplayName("동일한 상품 번호를 가진 상품을 여러개 주문할 수 있다.")
    @Test
    fun createOrderWithDuplicateProductNumber() {
        // given
        val productEntity1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val productEntity2 = createProduct(ProductType.HANDMADE, "002", 2000)
        val productEntity3 = createProduct(ProductType.HANDMADE, "003", 3000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        val request = OrderCreateRequest(
            productNumbers = listOf("001", "001"),
        )

        // when
        val registeredDateTime = LocalDateTime.now()
        val response = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(response.id).isNotNull
        assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 2000)
        assertThat(response.products).hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
            )
    }

    @DisplayName("재고가 있는 상품이 포함된 상품번호 리스트를 받아 주문을 생성한다.")
    @Test
    fun createOrderWithStock() {
        // given
        val productEntity1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val productEntity2 = createProduct(ProductType.BAKERY, "002", 3000)
        val productEntity3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        val stockEntity1 = StockEntity.create(productEntity1.productNumber, 2)
        val stockEntity2 = StockEntity.create(productEntity2.productNumber, 2)
        stockRepository.saveAll(listOf(stockEntity1, stockEntity2))

        val request = OrderCreateRequest(
            productNumbers = listOf("001", "001", "002", "003"),
        )

        // when
        val registeredDateTime = LocalDateTime.now()
        val response = orderService.createOrder(request, registeredDateTime)

        // then
        assertThat(response.id).isNotNull
        assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 10000)
        assertThat(response.products).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
                tuple("002", 3000),
                tuple("003", 5000),
            )

        val stocks = stockRepository.findAll()
        assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1),
            )
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    fun createOrderWithNotEnoughStock() {
        // given
        val productEntity1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val productEntity2 = createProduct(ProductType.BAKERY, "002", 3000)
        val productEntity3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(productEntity1, productEntity2, productEntity3))

        val stockEntity1 = StockEntity.create(productEntity1.productNumber, 2)
        val stockEntity2 = StockEntity.create(productEntity2.productNumber, 2)
        stockRepository.saveAll(listOf(stockEntity1, stockEntity2))

        val request = OrderCreateRequest(
            productNumbers = listOf("001", "001", "001", "002", "003"),
        )

        // when // then
        assertThatThrownBy { orderService.createOrder(request, LocalDateTime.now()) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고가 부족한 상품이 있습니다.")
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
