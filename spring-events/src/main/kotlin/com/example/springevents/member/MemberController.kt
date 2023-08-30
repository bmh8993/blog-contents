package com.example.springevents.member

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberApi(
    private val memberSignUpService: MemberSignUpService
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody req: MemberSignUpRequest) {
        memberSignUpService.signUp(req)
    }
}