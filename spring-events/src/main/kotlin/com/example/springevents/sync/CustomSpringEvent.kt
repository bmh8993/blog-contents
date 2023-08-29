package com.example.springevents.sync

import org.springframework.context.ApplicationEvent

class CustomSpringEvent(
    source: Any?,
    val message: String,
) : ApplicationEvent(source!!)
