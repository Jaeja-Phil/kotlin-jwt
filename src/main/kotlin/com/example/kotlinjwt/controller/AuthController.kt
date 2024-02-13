package com.example.kotlinjwt.controller

import com.example.kotlinjwt.controller.dto.request.AuthenticationRequest
import com.example.kotlinjwt.controller.dto.request.RefreshTokenRequest
import com.example.kotlinjwt.controller.dto.response.AuthenticationResponse
import com.example.kotlinjwt.controller.dto.response.TokenResponse
import com.example.kotlinjwt.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        return authenticationService.authenticate(authenticationRequest)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        return authenticationService.refreshToken(refreshTokenRequest.token)
    }
}
