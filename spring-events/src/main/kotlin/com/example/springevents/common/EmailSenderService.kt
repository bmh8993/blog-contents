package com.example.springevents.common

import com.example.springevents.member.MemberEntity
import org.springframework.stereotype.Service

@Service
class EmailSenderService {

    fun sendSignUpEmail(memberEntity: MemberEntity) {
        println("${memberEntity.name}님 회원가입을 축하드립니다.")
    }
}
