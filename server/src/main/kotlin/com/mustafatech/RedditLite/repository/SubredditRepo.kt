package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.Subreddit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubredditRepo: JpaRepository<Subreddit, Long>