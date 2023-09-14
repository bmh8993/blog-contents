package com.example.springevents.member

data class MemberSignUpRequest(
    val name: String,
    val isConnectedMessenger: Boolean
) {
    fun toEntity(): MemberEntity {
        return MemberEntity(name, isConnectedMessenger)
    }
}
