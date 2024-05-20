package org.hello.springexception.api

import org.hello.springexception.exception.UserException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiExceptionController {

    // curl -X GET http://localhost:8080/api/members/ex -H 'Content-Type: application/json' -H 'Accept: application/json'
    @GetMapping("/api/members/{id}")
    fun getMember(@PathVariable id: String): MemberDto {
        if (id == "ex") throw RuntimeException("잘못된 사용자")
        if (id == "bad") throw IllegalArgumentException("잘못된 입력 값")
        if (id == "user-ex") throw UserException("사용자 오류")

        return MemberDto(id)
    }

    data class MemberDto private constructor(
        val id: String,
        val name: String
    ) {
        constructor(id: String) : this(id, "hello $id")
    }
}