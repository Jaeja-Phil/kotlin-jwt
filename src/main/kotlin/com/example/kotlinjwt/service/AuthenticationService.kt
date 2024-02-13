package com.example.kotlinjwt.service

import com.example.kotlinjwt.config.JwtProperties
import com.example.kotlinjwt.controller.dto.request.AuthenticationRequest
import com.example.kotlinjwt.controller.dto.response.AuthenticationResponse
import com.example.kotlinjwt.controller.dto.response.TokenResponse
import com.example.kotlinjwt.domain.UserRefreshToken
import com.example.kotlinjwt.repository.UserRefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.Date

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val userRefreshTokenRepository: UserRefreshTokenRepository
){
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        /**
         * authenticate method throws an exception if authentication fails
         */
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val accessToken = createToken(user, Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))
        val refreshToken = createToken(user, Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration))
        userRefreshTokenRepository.save(UserRefreshToken(email = authenticationRequest.email, token = refreshToken))

        return AuthenticationResponse(accessToken, refreshToken)
    }

    fun refreshToken(refreshToken: String): TokenResponse {
        val userRefreshToken = userRefreshTokenRepository.findByToken(refreshToken) ?: throw Exception("Invalid refresh token")
        if (tokenService.isExpired(refreshToken)) throw Exception("Expired refresh token")

        val user = userDetailsService.loadUserByUsername(userRefreshToken.email)
        val accessToken = createToken(user, Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))
        /**
         * do not create a new refresh token and force the user to login again after refresh token expires
         */
        return TokenResponse(accessToken)
    }

    private fun createToken(userDetails: UserDetails, expirationDate: Date): String {
        return tokenService.generate(userDetails, expirationDate)
    }
}
