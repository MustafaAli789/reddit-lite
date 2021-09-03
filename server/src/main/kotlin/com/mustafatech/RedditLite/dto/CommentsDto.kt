package com.mustafatech.RedditLite.dto

import java.time.Instant

data class CommentsDto(
    val id: Long?,
    val postId: Long,
    val createdDate: Instant?,
    val text: String,
    val username: String
)