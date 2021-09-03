package com.mustafatech.RedditLite.model

import java.time.Instant
import javax.persistence.*

@Entity
data class RefreshToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        val token: String,

        val createdDate: Instant,

        @OneToOne
        val user: User

)