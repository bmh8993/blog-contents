package com.example.springevents.events

import com.example.springevents.common.EmailSenderService
import com.example.springevents.member.MemberService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = mu.KotlinLogging.logger {  }

@Component
class MemberEventHandler(
    private val emailSenderService: EmailSenderService,
    private val memberService: MemberService
) {
    @TransactionalEventListener
    fun memberSignUpEventListener(event: MemberSignUpEvent) {
        logger.info("MemberEventHandler.memberSignUpEventListener. tx:${TransactionSynchronizationManager.getCurrentTransactionName()}")
        emailSenderService.sendSignUpEmail(event.memberEntity)
    }

    @Async
    @TransactionalEventListener
    fun memberRecommendFollowingsEventListener(event: MemberRecommendFollowingsEvent) {
        logger.info("MemberEventHandler.memberRecommendFollowingsEventListener. tx:${TransactionSynchronizationManager.getCurrentTransactionName()}")
        memberService.recommendFollowings(event.memberEntity)
    }
}
