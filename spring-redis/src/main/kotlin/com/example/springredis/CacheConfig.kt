package com.example.springredis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.BatchStrategies
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.config.ConfigureRedisAction
import java.time.Duration

@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory, BatchStrategies.scan(1000)))
            .cacheDefaults(redisCacheConfiguration(Duration.ofDays(3L)))
            .build()
    }

    private fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            registerModule(JavaTimeModule())
            registerModule(KotlinModule.Builder().build())
        }
    }

    private fun redisCacheConfiguration(duration: Duration): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()),
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    Jackson2JsonRedisSerializer(objectMapper(), CacheType::class.java),
                ),
            )
            .disableCachingNullValues()
            .entryTtl(duration)
    }

    private fun packageARedisCacheConfiguration(duration: Duration): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()),
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    Jackson2JsonRedisSerializer(objectMapper(), PackageACacheType::class.java),
                ),
            )
            .disableCachingNullValues()
            .entryTtl(duration)
    }

    private fun packageBRedisCacheConfiguration(duration: Duration): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()),
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    Jackson2JsonRedisSerializer(objectMapper(), PackageBCacheType::class.java),
                ),
            )
            .disableCachingNullValues()
            .entryTtl(duration)
    }

    @Primary
    @Bean
    fun multiCacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory, BatchStrategies.scan(1000)))
            .cacheDefaults(redisCacheConfiguration(Duration.ofDays(3L)))
            .withInitialCacheConfigurations(
                mapOf(
                    "PackageA" to packageARedisCacheConfiguration(Duration.ofDays(3L)),
                    "PackageB" to packageBRedisCacheConfiguration(Duration.ofMinutes(30L)),
                ),
            )
            .build()
    }

    @Bean
    fun configureRedisAction(): ConfigureRedisAction {
        return ConfigureRedisAction.NO_OP
    }
}
