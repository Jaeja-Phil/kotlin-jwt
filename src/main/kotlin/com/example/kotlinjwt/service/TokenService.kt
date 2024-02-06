package com.example.kotlinjwt.service

import com.example.kotlinjwt.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val jwtProperties: JwtProperties
) {
    private val key = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        extraClaims: Map<String, Any> = emptyMap()
    ): String {
        return Jwts.builder()
            .claims()
            .subject(userDetails.username) // userDetails.username is the email in this service.
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(extraClaims)
            .and()
            .signWith(key)
            .compact()
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return email == userDetails.username && !isExpired(token)
    }

    fun isExpired(token: String): Boolean {
        return getClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }

    fun extractEmail(token: String): String? {
        return getClaims(token).subject
    }


    private fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}