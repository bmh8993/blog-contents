package com.example.springredis

import com.example.springredis.pv2.ExampleDto
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ExampleService(
    private val exampleRepository: ExampleRepository,
) {
    @Cacheable(cacheNames = ["PackageA"], key = "#name")
    fun findByName(name: String): ExampleDto {
        val dataExampleString = exampleRepository.findByName(name)?.name ?: "not found"
        return ExampleDto(dataExampleString)
    }

    @Cacheable(cacheNames = ["PackageB"], key = "#name")
    fun findByNameV2(name: String): ExampleDtoV2 {
        val dataExampleString = exampleRepository.findByName(name)?.name ?: "not found"
        return ExampleDtoV2(dataExampleString)
    }
}
