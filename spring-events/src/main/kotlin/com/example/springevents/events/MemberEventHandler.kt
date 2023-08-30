package com.example.springevents.events

import com.example.springevents.EmailSenderService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MemberEventHandler(
    private val emailSenderService: EmailSenderService,
) {
    @TransactionalEventListener
    fun memberSignUpEventListener(event: MemberSignUpEvent) {
        emailSenderService.sendSignUpEmail(event.member)
    }
}
