package com.example.springevents.member

import com.example.springevents.events.MemberRecommendFollowingsEvent
import com.example.springevents.events.MemberSignUpEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = mu.KotlinLogging.logger {  }

@Service
class MemberSignUpServiceV3(
    private val memberRepository: MemberRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val memberService: MemberService,
) {

    @Transactional
    fun signUp(dto: MemberSignUpRequest) {
        val memberEntity = createMember(dto.toEntity()) // 1. member 엔티티 영속화
        eventPublisher.publishEvent(MemberSignUpEvent(memberEntity)) // 2. 회원 가입 완료 이메일 발송
        memberService.saveFollowings(memberEntity) // 3. 메신저의 친구를 팔로잉으로 저장
        eventPublisher.publishEvent(MemberRecommendFollowingsEvent(memberEntity)) // 4. 팔로윙할 멤버를 추천하는 알림을 발송
    }

    private fun createMember(memberEntity: MemberEntity): MemberEntity {
        logger.info("MemberSignUpServiceV3.createMember. tx:${TransactionSynchronizationManager.getCurrentTransactionName()}")
        return memberRepository.save(memberEntity)
    }
}
