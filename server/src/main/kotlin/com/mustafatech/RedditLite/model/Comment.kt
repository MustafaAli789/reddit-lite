package com.mustafatech.RedditLite.model

import lombok.Builder
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Builder
data class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @NotEmpty
        val text: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId", referencedColumnName = "postId")
        val post: Post,

        val createdDate: Instant,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId", referencedColumnName = "userId")
        val user: User

)