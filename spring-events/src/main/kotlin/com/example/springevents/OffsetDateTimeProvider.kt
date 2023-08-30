package com.example.springevents

import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.temporal.TemporalAccessor
import java.util.*

@Component("offsetDateTimeProvider")
class OffsetDateTimeProvider : DateTimeProvider {

    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(OffsetDateTime.now())
    }
}