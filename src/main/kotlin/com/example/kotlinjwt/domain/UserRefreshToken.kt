package com.example.kotlinjwt.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_refresh_tokens")
data class UserRefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, length = 50) // TODO: change length to global variable (ex. EMAIL_MAX_LENGTH)
    val email: String,
    @Column(nullable = false)
    val token: String
)
