package com.example.springredis.pv2

import com.example.springredis.PackageACacheType
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(ExampleDto.CACHE_TYPE_NAME)
data class ExampleDto(
    val dataExample: String,
) : PackageACacheType {
    companion object {
        const val CACHE_TYPE_NAME = "ExampleDto"
    }
}
