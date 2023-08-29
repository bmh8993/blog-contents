package com.example.springevents.async

import org.springframework.context.ApplicationEvent

class AsyncCustomSpringEvent(
    source: Any?,
    val message: String,
) : ApplicationEvent(source!!)
