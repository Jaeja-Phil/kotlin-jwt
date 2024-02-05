package com.example.kotlinjwt.controller

import com.example.kotlinjwt.controller.dto.request.UserCreateRequest
import com.example.kotlinjwt.controller.dto.response.UserResponse
import com.example.kotlinjwt.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun findAll(): List<UserResponse> {
        return userService.getAll()
    }

    @GetMapping("/email")
    fun findByEmail(@RequestParam email: String): UserResponse? {
        return userService.getByEmail(email)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserResponse? {
        return userService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody userCreateRequest: UserCreateRequest): UserResponse {
        return userService.create(userCreateRequest)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Unit {
        return userService.delete(id)
    }
}