package com.example.springevents.member

import com.example.springevents.common.EntityAuditing
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tb_member")
class MemberEntity(
    @Column(name = "name", nullable = false)
    var name: String
) : EntityAuditing() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberEntity

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "MemberEntity(name='$name')"
    }
}