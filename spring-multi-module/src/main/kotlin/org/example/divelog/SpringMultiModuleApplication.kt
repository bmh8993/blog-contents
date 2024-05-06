package org.example.divelog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringMultiModuleApplication

fun main(args: Array<String>) {
    for (arg in args) {
        println("Argument: $arg")
    }
    runApplication<SpringMultiModuleApplication>(*args)
}
