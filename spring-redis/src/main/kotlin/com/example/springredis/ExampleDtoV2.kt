package com.example.springredis

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(ExampleDtoV2.CACHE_TYPE_NAME)
data class ExampleDtoV2(
    val dataExample: String,
) : PackageBCacheType {
    companion object {
        const val CACHE_TYPE_NAME = "ExampleDtoV2"
    }
}


