package org.hello.springexception

import jakarta.servlet.DispatcherType
import org.hello.springexception.filter.LogFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    @Bean
    fun logFilter(): FilterRegistrationBean<LogFilter> {
        val filterRegistrationBean = FilterRegistrationBean<LogFilter>()
        filterRegistrationBean.filter = LogFilter()
        filterRegistrationBean.order = 1
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR) // 이 필터는 요청과 에러에 동작합니다.
        return filterRegistrationBean
    }
}