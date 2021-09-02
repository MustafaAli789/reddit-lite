package com.mustafatech.RedditLite.model

import lombok.Builder
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Builder
data class Subreddit(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @NotBlank(message = "Community name is required")
        val name: String,

        @NotBlank(message = "Description is required")
        val description: String,

        @OneToMany(fetch = FetchType.LAZY)
        val posts: List<Post> = LinkedList(),

        val createdDate: Instant? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        val user: User? = null
)