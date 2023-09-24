package sample.springtestcode.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTestCodeApplication

fun main(args: Array<String>) {
    runApplication<SpringTestCodeApplication>(*args)
}
