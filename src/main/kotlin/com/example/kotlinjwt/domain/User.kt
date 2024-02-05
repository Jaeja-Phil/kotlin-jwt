package com.example.kotlinjwt.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY to use auto increment
    val id: Long? = null,
    @Column(unique = true, length = 50, nullable = false)
    val email: String,
    @Column(length = 100, nullable = false)
    val username: String,
    @Column(length = 100, nullable = false)
    val password: String,
    @Column(length = 20, nullable = false)
    val role: UserRoleType = UserRoleType.USER // default to USER
)

enum class UserRoleType {
    USER, ADMIN
}
