package sample.springtestcode.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class SpringTestCodeApplication

fun main(args: Array<String>) {
    runApplication<SpringTestCodeApplication>(*args)
}
