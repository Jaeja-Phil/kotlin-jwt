package com.example.kotlinjwt.controller.dto.response

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
