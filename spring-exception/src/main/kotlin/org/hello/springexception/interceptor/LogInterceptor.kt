package org.hello.springexception.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*

class LogInterceptor: HandlerInterceptor {

    val logger = LoggerFactory.getLogger(this::class.java)!!
    val LOG_ID: String = "logId"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI

        val uuid = UUID.randomUUID().toString()
        request.setAttribute(LOG_ID, uuid)

        logger.info("REQUEST [$uuid][${request.dispatcherType}][$requestURI]")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info("postHandle [$modelAndView]")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val requestURI = request.requestURI
        val logId: String = request.getAttribute(LOG_ID) as String

        logger.info("RESPONSE [$logId][${request.dispatcherType}][$requestURI]")

        if (ex != null) {
            logger.error("afterCompletion error!!", ex)
        }
    }
}