package com.example.kotlinjwt.controller

import com.example.kotlinjwt.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun findAll() = userService.getAll()

    @GetMapping("/email")
    fun findByEmail(@RequestParam email: String) = userService.getByEmail(email)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = userService.getById(id)
}