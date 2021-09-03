package com.mustafatech.RedditLite.controller

import com.mustafatech.RedditLite.dto.VoteDto
import com.mustafatech.RedditLite.service.VoteService
import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
class VoteController(val voteService: VoteService) {

    @PostMapping
    fun vote(@RequestBody voteDto: VoteDto): ResponseEntity<Void> {
        voteService.vote(voteDto)
        return ResponseEntity(HttpStatus.OK)
    }
}