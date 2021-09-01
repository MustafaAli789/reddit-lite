package com.mustafatech.RedditLite.model

import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
@NoArgsConstructor
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val userId: Long?,

        @NotBlank(message = "Username is required")
        val username: String,

        @NotBlank(message = "Password is required")
        val password: String,

        @Email
        @NotEmpty(message = "Email is required")
        val email: String,

        val createdDate: Instant,

        val enabled: Boolean
)