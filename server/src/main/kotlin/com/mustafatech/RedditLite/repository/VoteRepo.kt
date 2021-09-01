package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepo: JpaRepository<Vote, Long>