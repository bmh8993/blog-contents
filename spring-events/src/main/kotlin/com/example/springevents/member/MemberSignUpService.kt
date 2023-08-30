package com.example.springevents.member

import com.example.springevents.events.MemberSignUpEvent
import com.example.springevents.events.async.AsyncCustomSpringEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = mu.KotlinLogging.logger {}

@Service
class MemberSignUpService(
    private val memberRepository: MemberRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val asyncCustomSpringEventPublisher: AsyncCustomSpringEventPublisher
) {

    @Transactional
    fun signUp(dto: MemberSignUpRequest) {
        val member = createMember(dto.toEntity()) // 1. member 엔티티 영속화
        eventPublisher.publishEvent(MemberSignUpEvent(member)) // 2. 회원 가입 완료 이벤트 발행
        for (i in 1..10) {
            asyncCustomSpringEventPublisher.publishCustomEvent("회원 가입 완료 $i") // 3. 비동기 이벤트 발행
        }

    }

    private fun createMember(memberEntity: MemberEntity): MemberEntity {
        logger.info { memberEntity.toString() }
        return memberRepository.save(memberEntity)
    }
}
