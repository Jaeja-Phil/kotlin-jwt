package com.example.kotlinjwt.controller.dto.request

data class AuthenticationRequest(
    val email: String,
    val password: String
)
