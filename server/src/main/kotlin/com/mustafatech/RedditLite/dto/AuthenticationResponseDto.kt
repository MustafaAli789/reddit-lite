package com.mustafatech.RedditLite.dto

import java.time.Instant

data class AuthenticationResponseDto(
    val authenticationToken: String,
    val username: String,
    val refreshToken: String,
    val expiresAt: Instant
    )