package com.example.kotlinjwt.service

import com.example.kotlinjwt.config.JwtProperties
import com.example.kotlinjwt.controller.dto.request.AuthenticationRequest
import com.example.kotlinjwt.controller.dto.response.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
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
        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthenticationResponse(accessToken)
    }
}
