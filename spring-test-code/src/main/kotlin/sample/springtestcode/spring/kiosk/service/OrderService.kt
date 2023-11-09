package sample.springtestcode.spring.kiosk.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.springtestcode.spring.kiosk.api.resreq.OrderCreateRequest
import sample.springtestcode.spring.kiosk.api.resreq.OrderResponse
import sample.springtestcode.spring.kiosk.persistence.entity.OrderEntity
import sample.springtestcode.spring.kiosk.persistence.entity.OrderProductEntity
import sample.springtestcode.spring.kiosk.persistence.entity.ProductEntity
import sample.springtestcode.spring.kiosk.persistence.entity.StockEntity
import sample.springtestcode.spring.kiosk.persistence.repository.OrderProductRepository
import sample.springtestcode.spring.kiosk.persistence.repository.OrderRepository
import sample.springtestcode.spring.kiosk.persistence.repository.ProductRepository
import sample.springtestcode.spring.kiosk.persistence.repository.StockRepository
import java.time.LocalDateTime

@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val stockRepository: StockRepository,
) {
    @Transactional
    fun createOrder(request: OrderCreateRequest, registeredDateTime: LocalDateTime): OrderResponse {
        val productNumbers = request.productNumbers
        val products = findProductsBy(productNumbers)

        // TODO : 동시성 처리
        decreaseStockQuantity(products)

        val savedOrder = orderRepository.save(OrderEntity.create(products, registeredDateTime))

        registerProducts(savedOrder, products)

        return OrderResponse.of(savedOrder, products)
    }

    private fun findProductsBy(productNumbers: List<String>): List<ProductEntity> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)
        val productMap: Map<String, ProductEntity> = products.associateBy { it.productNumber }
        return productNumbers.map { productMap[it]!! }
    }

    private fun decreaseStockQuantity(products: List<ProductEntity>) {
        val stockTypeProductNumbers = filterStockTypeProductNumbers(products)

        val stockMap: Map<String, StockEntity> = createStockMapBy(stockTypeProductNumbers) // productNumber, StockEntity
        val productCountingMap: Map<String, Int> = createCountingMapBy(stockTypeProductNumbers) // productNumber, count

        // hashSet과 set의 차이?
        stockTypeProductNumbers.toHashSet().forEach { productNumber ->
            val stock = stockMap[productNumber]!!
            val productCount = productCountingMap[productNumber]!!

            if (stock.isQuantityEnough(productCount).not()) throw IllegalArgumentException("재고가 부족한 상품이 있습니다.")

            stock.decreaseQuantity(productCount)
        }
    }

    private fun filterStockTypeProductNumbers(products: List<ProductEntity>) =
        products
            .filter { product -> product.type.isStockType() }
            .map { stockTypeProduct -> stockTypeProduct.productNumber }

    private fun createStockMapBy(stockTypeProductNumbers: List<String>) =
        stockRepository
            .findAllByProductNumberIn(stockTypeProductNumbers)
            .associateBy({ it.productNumber }, { it })
    private fun createCountingMapBy(stockTypeProductNumbers: List<String>) =
        stockTypeProductNumbers.groupingBy { it }.eachCount()

    private fun registerProducts(order: OrderEntity, products: List<ProductEntity>) {
        products.map { orderProductRepository.save(OrderProductEntity(order, it)) }
    }
}
