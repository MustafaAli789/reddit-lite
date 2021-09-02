package com.mustafatech.RedditLite.dto

import javax.management.monitor.StringMonitor

data class PostResponseDto(
        val id: Long,
        val postName: String,
        val url: String,
        val description: String,
        val userName: String,
        val subredditName: String,
        val voteCount: Int,
        val commentCount: Int,
        val duration: String,
        var upVote: Boolean,
        var downVote: Boolean
)