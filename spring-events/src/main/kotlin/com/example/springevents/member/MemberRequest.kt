package com.example.springevents.member

data class MemberSignUpRequest(
    val name: String,
) {
    fun toEntity(): MemberEntity {
        return MemberEntity(name)
    }
}
