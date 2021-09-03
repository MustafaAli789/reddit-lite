package com.mustafatech.RedditLite.controller

import com.mustafatech.RedditLite.dto.CommentsDto
import com.mustafatech.RedditLite.service.CommentService
import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.mail.event.FolderEvent.CREATED


@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
class CommentsController(val commentService: CommentService) {

    @PostMapping
    fun createComment(@RequestBody commentsDto: CommentsDto): ResponseEntity<Any> {
        commentService.save(commentsDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/by-post/{postId}")
    fun getAllCommentsForPost(@PathVariable postId: Long): ResponseEntity<List<CommentsDto>> {
        return ResponseEntity.ok(commentService.getAllCommentsForPost(postId))
    }

    @GetMapping("/by-user/{userName}")
    fun getAllCommentsForUser(@PathVariable userName: String): ResponseEntity<List<CommentsDto>> {
        return ResponseEntity.ok(commentService.getAllCommentsForUser(userName))
    }
}