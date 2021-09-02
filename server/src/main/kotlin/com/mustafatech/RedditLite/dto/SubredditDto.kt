package com.mustafatech.RedditLite.dto

data class SubredditDto(
        var id: Long?,
        val subredditName: String,
        val description: String,
        var numOfPosts: Int?,
)