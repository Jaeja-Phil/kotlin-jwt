package com.example.kotlinjwt.service

import com.example.kotlinjwt.controller.dto.request.UserCreateRequest
import com.example.kotlinjwt.controller.dto.response.UserResponse
import com.example.kotlinjwt.domain.User
import com.example.kotlinjwt.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAll(): List<UserResponse> {
        return userRepository.findAll()
            .map { UserResponse(requireNotNull(it.id), it.email, it.username, it.role) }
    }

    fun getByEmail(email: String): UserResponse? {
        return userRepository.findByEmail(email)
            ?.let { UserResponse(requireNotNull(it.id), it.email, it.username, it.role) }
    }

    fun getById(id: Long): UserResponse? {
        return userRepository.findById(id)
            .map { UserResponse(requireNotNull(it.id), it.email, it.username, it.role) }
            .orElse(null)
    }

    fun create(user: UserCreateRequest): UserResponse {
        // Check if email already exists
        userRepository.findByEmail(user.email)?.let {
            // TODO: Change to custom exception
            throw IllegalArgumentException("Email already exists")
        }

        val newUser = userRepository.save(User(
            email = user.email,
            username = user.username,
            password = user.password
        ))

        return UserResponse(requireNotNull(newUser.id), newUser.email, newUser.username, newUser.role)
    }

    fun delete(id: Long): Unit {
        // check if user exists
        userRepository.findById(id).orElseThrow {
            // TODO: Change to custom exception
            IllegalArgumentException("User not found")
        }

        return userRepository.deleteById(id)
    }
}