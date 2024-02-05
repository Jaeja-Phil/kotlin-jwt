package com.example.kotlinjwt.controller.dto.request

data class UserCreateRequest(
    val email: String,
    val username: String,
    val password: String
)
