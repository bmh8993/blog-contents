package com.example.springredis

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@cacheType")
@JsonSubTypes(
    JsonSubTypes.Type(value = ExampleDtoV2::class, name = ExampleDtoV2.CACHE_TYPE_NAME),
)
interface PackageBCacheType
