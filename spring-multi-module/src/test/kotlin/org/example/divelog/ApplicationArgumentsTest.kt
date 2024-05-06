package org.example.divelog

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(args = ["--app.name=dive-log-test"])
class ApplicationArgumentsTest {
    @Autowired
    lateinit var applicationArguments: ApplicationArguments

    @DisplayName("")
    @Test
    fun testApplicationArguments() {
        // given

        // when

        // then
        Assertions.assertThat(applicationArguments.optionNames).containsOnly("app.name")
        Assertions.assertThat(applicationArguments.getOptionValues("app.name")).containsOnly("dive-log-test")
    }
}
