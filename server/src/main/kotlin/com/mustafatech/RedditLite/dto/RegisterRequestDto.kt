package com.mustafatech.RedditLite.dto

data class RegisterRequestDto(
        val email: String,
        val username: String,
        val password: String
)