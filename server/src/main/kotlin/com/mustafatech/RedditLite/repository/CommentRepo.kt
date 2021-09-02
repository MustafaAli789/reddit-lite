package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Comment
import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CommentRepo: JpaRepository<Comment, Long> {
    fun findByPost(post: Post): List<Comment>

    fun findAllByUser(user: User): List<Comment>
}