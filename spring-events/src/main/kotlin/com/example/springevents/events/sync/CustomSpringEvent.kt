package com.example.springevents.events.sync

import org.springframework.context.ApplicationEvent

class CustomSpringEvent(
    source: Any?,
    val message: String,
) : ApplicationEvent(source!!)
