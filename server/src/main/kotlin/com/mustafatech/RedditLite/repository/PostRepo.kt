package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.Subreddit
import com.mustafatech.RedditLite.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface PostRepo: JpaRepository<Post, Long>{

    fun findAllBySubreddit(subreddit: Subreddit): List<Post>

    fun findByUser(user: User): List<Post>

}