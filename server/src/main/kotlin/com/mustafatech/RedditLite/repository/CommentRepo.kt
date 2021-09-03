package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Comment
import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CommentRepo: JpaRepository<Comment, Long> {
    fun findByPost(post: Post): List<Comment>

    fun findByUser(user: User): List<Comment>
}