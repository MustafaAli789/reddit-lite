package com.mustafatech.RedditLite.dto

data class PostRequestDto(
        val postId: Long?,
        val subredditName: String,
        val postName: String,
        val url: String,
        val description: String
)