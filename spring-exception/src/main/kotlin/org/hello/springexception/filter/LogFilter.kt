package org.hello.springexception.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import java.util.*

class LogFilter: Filter {
    val logger = LoggerFactory.getLogger(LogFilter::class.java)!!

    override fun init(filterConfig: FilterConfig) {
        logger.info("log filter init")
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI

        val uuid = UUID.randomUUID().toString()

        try {
            logger.info("REQUEST [$uuid][${request.dispatcherType}][$requestURI]")
            chain.doFilter(request, response)
        } catch (e: Exception) {
            logger.info("EXCEPTION: ${e.message}")
            throw e // WAS까지 예외가 전파된다.
        } finally {
            logger.info("RESPONSE [$uuid][${request.dispatcherType}][$requestURI]")
        }
    }

    override fun destroy() {
        logger.info("log filter destroy")
    }
}