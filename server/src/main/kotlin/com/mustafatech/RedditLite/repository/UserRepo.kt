package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}