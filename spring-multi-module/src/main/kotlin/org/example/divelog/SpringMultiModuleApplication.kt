package org.example.divelog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringMultiModuleApplication

fun main(args: Array<String>) {
    for (arg in args) { // 여기서 args는 애플리케이션을 실행할 때 전달한 인자들을 의미한다.
        println("Argument: $arg")
    }
    runApplication<SpringMultiModuleApplication>(*args)
}
