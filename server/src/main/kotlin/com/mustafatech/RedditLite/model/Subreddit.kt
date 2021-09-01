package com.mustafatech.RedditLite.model

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Subreddit(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @NotBlank(message = "Community name is required")
        val name: String,

        @NotBlank(message = "Description is required")
        val description: String,

        @OneToMany(fetch = FetchType.LAZY)
        val posts: List<Post>,

        val createdDate: Instant,

        @ManyToOne(fetch = FetchType.LAZY)
        val user: User
)