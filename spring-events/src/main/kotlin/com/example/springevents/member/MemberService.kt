package com.example.springevents.member

import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = mu.KotlinLogging.logger {  }

@Service
class MemberService {
    fun recommendFollowings(memberEntity: MemberEntity) {
        println("팔로잉으로 추천할 멤버 조회")
        println("알림 발송")
    }

    fun saveFollowings(memberEntity: MemberEntity) {
//        throw RuntimeException("메신저의 친구를 팔로잉으로 저장 실패")

        if (memberEntity.isConnectedMessenger) {
            logger.info("MemberService.saveFollowings. tx:${TransactionSynchronizationManager.getCurrentTransactionName()}")
            println("메신저의 친구를 팔로잉으로 저장합니다.")
        }
    }
}