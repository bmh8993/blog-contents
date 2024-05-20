package org.hello.springexception

import jakarta.servlet.DispatcherType
import org.hello.springexception.resolver.MyHandlerExceptionResolver
import org.hello.springexception.filter.LogFilter
import org.hello.springexception.interceptor.LogInterceptor
import org.hello.springexception.resolver.UserHandlerExceptionResolver
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        // interceptor는 dispatcherType이 REQUEST일 때만 동작한다.
        registry.addInterceptor(LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**", "*.ico", "/error/**", "/error-page/**") // css, ico, error는 로깅에서 제외
    }

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(MyHandlerExceptionResolver())
        resolvers.add(UserHandlerExceptionResolver())
    }

//    @Bean
    fun logFilter(): FilterRegistrationBean<LogFilter> {
        val filterRegistrationBean = FilterRegistrationBean<LogFilter>()
        filterRegistrationBean.filter = LogFilter()
        filterRegistrationBean.order = 1
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR) // 이 필터는 요청과 에러에 동작합니다.
        return filterRegistrationBean
    }
}