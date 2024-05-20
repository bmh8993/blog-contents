package org.hello.springexception.resolver

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.hello.springexception.exception.UserException
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

class UserHandlerExceptionResolver : HandlerExceptionResolver {

    val logger = LoggerFactory.getLogger(this::class.java)!!
    val objectMapper = ObjectMapper()

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {
        try {
            if (ex is UserException) {
                logger.info("UserException resolver to 400")
                response.status = HttpServletResponse.SC_BAD_REQUEST

                if ("application/json" == request.getHeader("accept")) {
                    val errorResult = mapOf(
                        "ex" to ex.javaClass,
                        "message" to ex.message
                    )
                    val result = objectMapper.writeValueAsString(errorResult)
                    response.contentType = "application/json"
                    response.characterEncoding = "utf-8"
                    response.writer.write(result)
                    return ModelAndView()
                } else {
                    // TEXT/HTML
                    return ModelAndView("error/400")
                }
            }
        } catch (e: Exception) {
            logger.error("resolver ex", e)
        }

        return null
    }
}
