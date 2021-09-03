package com.mustafatech.RedditLite.dto

import javax.validation.constraints.NotBlank

data class RefreshTokenRequestDto(
        @NotBlank
        val refreshToken: String
)