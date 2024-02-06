package com.example.kotlinjwt.config

import com.example.kotlinjwt.repository.UserRepository
import com.example.kotlinjwt.service.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    /**
     * create a bean of UserDetailsService which replaces the default UserDetailsService
     * to our custom implementation
     */
    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return CustomUserDetailsService(userRepository)
    }

    /**
     * create a bean of PasswordEncoder which replaces the default PasswordEncoder (NoOpPasswordEncoder)
     * to BCryptPasswordEncoder in order to hash the password stored inside the database
     */
    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * create a bean of AuthenticationProvider and specify the UserDetailsService and PasswordEncoder to use
     */
    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider {
        return DaoAuthenticationProvider()
            .apply {
                setUserDetailsService(userDetailsService(userRepository))
                setPasswordEncoder(encoder())
            }
    }

    /**
     * create a bean of AuthenticationManager which returns the AuthenticationManager from the AuthenticationConfiguration
     */
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}