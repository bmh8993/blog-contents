package com.example.springevents.member

import com.example.springevents.common.EntityAuditing
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tb_member")
class MemberEntity(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "is_connected_messenger", nullable = false)
    var isConnectedMessenger: Boolean
) : EntityAuditing() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberEntity

        if (name != other.name) return false
        return isConnectedMessenger == other.isConnectedMessenger
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isConnectedMessenger.hashCode()
        return result
    }

    override fun toString(): String {
        return "MemberEntity(name='$name', isConnectedMessenger=$isConnectedMessenger)"
    }


}