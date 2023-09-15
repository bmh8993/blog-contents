package com.example.springevents.aop

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

private val logger = mu.KotlinLogging.logger {}

@Aspect
@Component
class TransactionLoggerAspect {
    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun logTransactionCompletion() {
        logger.info("Transaction completed successfully")
    }
}