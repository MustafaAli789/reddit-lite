package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepo: JpaRepository<Post, Long>