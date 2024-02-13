package com.example.kotlinjwt.repository

import com.example.kotlinjwt.domain.UserRefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface UserRefreshTokenRepository : JpaRepository<UserRefreshToken, Long> {
    fun findByToken(token: String): UserRefreshToken?
}