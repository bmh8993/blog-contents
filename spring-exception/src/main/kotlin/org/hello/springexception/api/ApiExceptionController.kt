package org.hello.springexception.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    fun getMember(@PathVariable id: String): MemberDto {
        if (id == "exist")
            throw RuntimeException("member not found")

        return MemberDto(id)
    }

    data class MemberDto private constructor(
        val id: String,
        val name: String
    ) {
        constructor(id: String) : this(id, "hello $id")
    }
}