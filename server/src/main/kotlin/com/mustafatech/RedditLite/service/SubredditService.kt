package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.dto.SubredditDto
import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.model.Subreddit
import com.mustafatech.RedditLite.repository.SubredditRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubredditService(val subredditRepo: SubredditRepo) {

    @Transactional
    fun save(subredditDto: SubredditDto): SubredditDto {
        val subreddit = mapSubredditDto(subredditDto)
        subredditRepo.save(subreddit)
        subredditDto.id = subreddit.id
        subredditDto.numOfPosts = 0
        return subredditDto

    }

    private fun mapSubredditDto(subredditDto: SubredditDto): Subreddit {
        return Subreddit(name = subredditDto.subredditName, description = subredditDto.description)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<SubredditDto> {
        return subredditRepo.findAll().map { subreddit ->
            SubredditDto(subreddit.id, subreddit.name, subreddit.description, subreddit.posts.size)
        }
    }

    @Transactional(readOnly = true)
    fun getSubreddit(id: Long): SubredditDto{
        val subreddit = subredditRepo.findById(id).orElseThrow {
            SpringRedditException("No subreddit found with id $id")
        }
        return SubredditDto(subreddit.id, subreddit.name, subreddit.description, subreddit.posts.size)
    }
}