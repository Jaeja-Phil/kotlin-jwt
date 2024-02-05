package com.example.kotlinjwt.controller.dto.response

import com.example.kotlinjwt.domain.UserRoleType

data class UserResponse(
    val id: Long,
    val email: String,
    val username: String,
    val role: UserRoleType
)
