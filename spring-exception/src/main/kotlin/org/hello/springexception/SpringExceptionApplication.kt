package org.hello.springexception

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringExceptionApplication

fun main(args: Array<String>) {
    runApplication<SpringExceptionApplication>(*args)
}
