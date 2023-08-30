package com.example.springevents

import com.example.springevents.member.MemberEntity
import org.springframework.stereotype.Service

@Service
class EmailSenderService {

    /**
     * 외부 인프라 서비스를 호출한다고 가정한다
     */
    fun sendSignUpEmail(member: MemberEntity) {
        println(
            """
                ${member.name} + " 님 회원가입을 축하드립니다."
            """.trimIndent(),
        )
    }
}
