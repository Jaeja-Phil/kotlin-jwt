package com.example.kotlinjwt.service

import com.example.kotlinjwt.entity.User
import com.example.kotlinjwt.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAll() : List<User> {
        return userRepository.findAll()
    }

    fun getByEmail(email: String) : User? {
        return userRepository.findByEmail(email)
    }

    fun getById(id: Long) : User? {
        return userRepository.findById(id).orElse(null)
    }
}