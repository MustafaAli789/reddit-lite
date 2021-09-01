package com.mustafatech.RedditLite.model

data class NotificationEmail(
        val subject: String,
        val recipient: String,
        val body: String
)