package com.example.kotlinjwt.service

import com.example.kotlinjwt.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
            ?.let {
                User.builder()
                    .username(it.username)
                    .password(it.password)
                    .roles(it.role.name)
                    .build()
            }
            ?: throw UsernameNotFoundException("User not found") // TODO: consider creating a custom exception
    }
}