package com.mustafatech.RedditLite.controller

import com.mustafatech.RedditLite.dto.SubredditDto
import com.mustafatech.RedditLite.service.SubredditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subreddit")
class SubredditController(val subredditService: SubredditService){

    @PostMapping
    fun createSubreddit(@RequestBody subredditDto: SubredditDto): ResponseEntity<SubredditDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<SubredditDto>> {
        return ResponseEntity.ok(subredditService.getAll())
    }

}