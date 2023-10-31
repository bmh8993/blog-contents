package sample.springtestcode.spring.common.persistence.repository

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

// TODO
// - @MappedSuperclass
// - @EntityListeners > main이 있는 클래스 위에 @EnableJpaAuditing 추가해야함.
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

    @CreatedDate
    private val createdDateTime: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    private val updatedDateTime: LocalDateTime = LocalDateTime.now()
)
