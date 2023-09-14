package com.example.springevents.member

import com.example.springevents.events.MemberSignUpEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSignUpServiceV2(
    private val memberRepository: MemberRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val memberService: MemberService,
) {

    @Transactional
    fun signUp(dto: MemberSignUpRequest) {
        val memberEntity = createMember(dto.toEntity()) // 1. member 엔티티 영속화
        eventPublisher.publishEvent(MemberSignUpEvent(memberEntity)) // 2. 회원 가입 완료 이메일 발송
        memberService.saveFollowings(memberEntity) // 3. 메신저의 친구를 팔로잉으로 저장
    }

    private fun createMember(memberEntity: MemberEntity): MemberEntity {
        return memberRepository.save(memberEntity)
    }
}
