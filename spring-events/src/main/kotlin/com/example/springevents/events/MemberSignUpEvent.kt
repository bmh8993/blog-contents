package com.example.springevents.events

import com.example.springevents.member.MemberEntity

data class MemberSignUpEvent(
    val memberEntity: MemberEntity
)
