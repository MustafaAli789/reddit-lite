package com.mustafatech.RedditLite.model

import java.time.Instant
import javax.persistence.*

data class VerificationToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        val token: String,

        @OneToOne(fetch = FetchType.LAZY)
        val user: User,

        val expiryDate: Instant
)