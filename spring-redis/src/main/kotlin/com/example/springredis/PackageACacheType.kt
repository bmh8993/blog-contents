package com.example.springredis

import com.example.springredis.pv2.ExampleDto
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@cacheType")
@JsonSubTypes(
    JsonSubTypes.Type(value = ExampleDto::class, name = ExampleDto.CACHE_TYPE_NAME),
)
interface PackageACacheType
