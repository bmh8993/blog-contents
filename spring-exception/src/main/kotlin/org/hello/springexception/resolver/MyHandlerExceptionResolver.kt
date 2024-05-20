package org.hello.springexception.resolver

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException

class MyHandlerExceptionResolver : HandlerExceptionResolver {
    val logger = LoggerFactory.getLogger(this::class.java)!!

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse, handler: Any?, ex: Exception
    ): ModelAndView? {
        try {
            if (ex is IllegalArgumentException) {
                logger.info("IllegalArgumentException resolver to 400")
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.message)

                return ModelAndView()
            }
        } catch (e: IOException) {
            logger.error("resolver ex", e)
        }
        return null
    }
}