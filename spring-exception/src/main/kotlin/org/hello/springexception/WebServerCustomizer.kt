package org.hello.springexception

import org.springframework.boot.web.server.ConfigurableWebServerFactory
import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class WebServerCustomizer: WebServerFactoryCustomizer<ConfigurableWebServerFactory>{
    override fun customize(factory: ConfigurableWebServerFactory) {
        val errorPage404 = ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404")
        val errorPage500 = ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500")

        val errorPageRuntimeEx = ErrorPage(RuntimeException::class.java, "/error-page/500")

        factory.addErrorPages(errorPage404, errorPage500, errorPageRuntimeEx)
    }
}