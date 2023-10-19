package com.example.concurrency.repository

import com.example.concurrency.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * 네임드 락은 로우 단위(데이터)에 락을 거는게 아니다.
 * 락을 얻고, 락을 해제하는 것만 지원한다. 그 사이에 다른 트랜잭션으로 데이터를 변경할 수 있다.
 * 즉, 네임드 락은 두개의 커넥션을 사용한다. 하나는 락을 얻기 위한 커넥션, 하나는 트랜잭션(로직)에 필요한 커넥션이다.
 * 그러므로 두개의 커넥션 풀을 설정하라
 * ---
 * 네임드 락은 주로 분산락을 구현할 때 사용한다.
 * pessimistic lock은 타임아웃을 구현하기 어렵지만, 네임드 락은 타임아웃을 구현하기 쉽다.
 * 또한, 데이터 삽입 시 데이터 정합성을 맞출 때 사용할 수 있다.
 * ---
 * 단, lock을 얻고 해제하는 관리를 잘 해주어야한다.
 */
interface NamedLockRepository : JpaRepository<Stock, Long> {

    /**
     * 커넥션 풀이 부족할 수 있기 때문에 실무에서는 lcok을 위한 데이터 소스를 분리해서 사용하라.
     * 이번 프로젝트에서는 동일한 데이터 소스를 사용하므로 커넥션 풀 사이즈를 크게 조정
     */
    @Query("select get_lock(:key, 3000)", nativeQuery = true)
    fun getLock(key: String)

    @Query("select release_lock(:key)", nativeQuery = true)
    fun releaseLock(key: String)
}
