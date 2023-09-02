package com.example.springevents.member

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberApi(
    private val memberSignUpServiceV1: MemberSignUpServiceV1,
    private val memberSignUpServiceV2: MemberSignUpServiceV2,
    private val memberSignUpServiceV3: MemberSignUpServiceV3,
) {

    @PostMapping("/sign-up-v1")
    fun signUpV1(@RequestBody req: MemberSignUpRequest) {
        memberSignUpServiceV1.signUp(req)
    }

    @PostMapping("/sign-up-v2")
    fun signUpV2(@RequestBody req: MemberSignUpRequest) {
        memberSignUpServiceV2.signUp(req)
    }

    @PostMapping("/sign-up-v3")
    fun signUpV3(@RequestBody req: MemberSignUpRequest) {
        memberSignUpServiceV3.signUp(req)
    }
}