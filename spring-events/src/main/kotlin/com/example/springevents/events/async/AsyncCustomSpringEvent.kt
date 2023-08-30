package com.example.springevents.events.async

import org.springframework.context.ApplicationEvent

class AsyncCustomSpringEvent(
    source: Any?,
    val message: String,
) : ApplicationEvent(source!!)
