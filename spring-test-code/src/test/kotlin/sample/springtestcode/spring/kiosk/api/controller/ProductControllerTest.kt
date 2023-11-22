package sample.springtestcode.spring.kiosk.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import sample.springtestcode.spring.kiosk.api.request.ProductCreateRequest
import sample.springtestcode.spring.kiosk.persistence.code.ProductSellingStatus
import sample.springtestcode.spring.kiosk.persistence.code.ProductType
import sample.springtestcode.spring.kiosk.service.ProductService
import org.mockito.Mockito.`when`
import sample.springtestcode.spring.kiosk.api.response.ProductResponse

@ActiveProfiles("test")
@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var productService: ProductService

    @DisplayName("신규 상품을 등록한다.")
    @Test
    fun createProduct() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "아메리카노",
            price = 4000,
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @DisplayName("신규 상품을 등록할 때 상품명은 필수 입니다.")
    @Test
    fun createProductWithoutProductType() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "",
            price = 4000,
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(
                    objectMapper.writeValueAsString(request)
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품명을 입력해주세요."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(""))
    }

    @DisplayName("신규 상품을 등록할 때 가격은 0보다 커야 합니다.")
    @Test
    fun createProductWithZeroPrice() {
        // given
        val request = ProductCreateRequest(
            type = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.SELLING,
            name = "아메리카노",
            price = 0,
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(
                    objectMapper.writeValueAsString(request)
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 가격은 0보다 커야합니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(""))
    }

    @DisplayName("판매상품을 조회한다.")
    @Test
    fun getSellingProducts() {
        // when
        val result: List<ProductResponse> = emptyList()
        `when`(productService.getSellingProducts()).thenReturn(result)


        // then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/products/selling")
//                .queryParam("type", "HANDMADE")
//                .queryParam("sellingStatus", "SELLING")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray)
    }

}