package com.example.springredis

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExampleRepository : JpaRepository<ExampleEntity, Long> {
    fun findByName(name: String): ExampleEntity?
}
