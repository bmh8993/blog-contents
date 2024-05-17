package org.hello.springexception.servlet

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ErrorPageController {
    val logger = LoggerFactory.getLogger(ErrorPageController::class.java)!!

    // RequestDispatcher 상수로 정의되어 있음
    val ERROR_EXCEPTION: String = "jakarta.servlet.error.exception"
    val ERROR_EXCEPTION_TYPE: String = "jakarta.servlet.error.exception_type"
    val ERROR_MESSAGE: String = "jakarta.servlet.error.message"
    val ERROR_REQUEST_URI: String = "jakarta.servlet.error.request_uri"
    val ERROR_SERVLET_NAME: String = "jakarta.servlet.error.servlet_name"
    val ERROR_STATUS_CODE: String = "jakarta.servlet.error.status_code"

    @RequestMapping("/error-page/404")
    fun errorPage400(request: HttpServletRequest, response: HttpServletResponse): String {
        logger.info("errorPage404")
        printErrorInfo(request)
        return "error-page/404"
    }

    @RequestMapping("/error-page/500")
    fun errorPage500(request: HttpServletRequest, response: HttpServletResponse): String {
        logger.info("errorPage500")
        printErrorInfo(request)
        return "error-page/500"
    }

    private fun printErrorInfo(request: HttpServletRequest) {
        logger.info("ERROR_EXCEPTION: ${request.getAttribute(ERROR_EXCEPTION)}")
        logger.info("ERROR_EXCEPTION_TYPE: ${request.getAttribute(ERROR_EXCEPTION_TYPE)}")
        logger.info("ERROR_MESSAGE: ${request.getAttribute(ERROR_MESSAGE)}")
        logger.info("ERROR_REQUEST_URI: ${request.getAttribute(ERROR_REQUEST_URI)}")
        logger.info("ERROR_SERVLET_NAME: ${request.getAttribute(ERROR_SERVLET_NAME)}")
        logger.info("ERROR_STATUS_CODE: ${request.getAttribute(ERROR_STATUS_CODE)}")
        logger.info("dispatchType: ${request.dispatcherType}")
    }
}