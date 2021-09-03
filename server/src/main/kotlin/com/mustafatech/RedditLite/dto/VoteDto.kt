package com.mustafatech.RedditLite.dto

import com.mustafatech.RedditLite.model.VoteType

data class VoteDto(
        val voteType: VoteType,
        val postId: Long
)