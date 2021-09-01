package com.mustafatech.RedditLite.model

import lombok.Builder
import org.springframework.lang.Nullable
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Builder
data class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val postId: Long?,

        @NotBlank(message = "Post Name cannot be empty or Null")
        val postName: String,

        @Nullable
        val url: String,

        @Nullable
        @Lob
        val description: String,

        val voteCount: Int = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId", referencedColumnName = "userId")
        val user: User,

        val createdDate: Instant,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id", referencedColumnName = "id")
        val subreddit: Subreddit

)