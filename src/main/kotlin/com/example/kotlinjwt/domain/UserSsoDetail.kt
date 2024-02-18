package com.example.kotlinjwt.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_sso_details")
data class UserSsoDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val providerType: ProviderType,
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User,
    @Column(nullable = false)
    val identifier: String
)

enum class ProviderType {
    APPLE
}
