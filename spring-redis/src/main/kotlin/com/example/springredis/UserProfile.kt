package com.example.springredis

import com.fasterxml.jackson.annotation.JsonProperty

data class UserProfile(
    @JsonProperty
    val name: String,

    @JsonProperty
    val age: Int,
)
