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
import sample.springtestcode.spring.kiosk.api.request.OrderCreateRequest
import sample.springtestcode.spring.kiosk.service.OrderService

@ActiveProfiles("test")
@WebMvcTest(controllers = [OrderController::class])
class OrderControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var orderService: OrderService

    @DisplayName("신규 주문을 등록한다.")
    @Test
    fun createOrder() {
        // given
        val request = OrderCreateRequest(
            productNumbers = listOf("001")
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/orders/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @DisplayName("신규 주문을 등록할 때 주문하려는 상품번호는 필수이다.")
    @Test
    fun createOrderWithoutProductNumbers() {
        // given
        val request = OrderCreateRequest(
            productNumbers = emptyList()
        )

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/orders/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("주문하려는 상품번호는 필수입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)


    }
}