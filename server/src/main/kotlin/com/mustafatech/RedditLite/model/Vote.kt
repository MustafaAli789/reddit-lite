package com.mustafatech.RedditLite.model

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Vote(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val voteId: Long?,

        val voteType: VoteType,

        @NotNull
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId", referencedColumnName = "postId")
        val post: Post,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId", referencedColumnName = "userId")
        val user: User

)