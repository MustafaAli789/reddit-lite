package com.mustafatech.RedditLite.controller

import com.mustafatech.RedditLite.dto.PostRequestDto
import com.mustafatech.RedditLite.dto.PostResponseDto
import com.mustafatech.RedditLite.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/posts")
class PostController(val postService: PostService) {

    @PostMapping
    fun createPost(@RequestBody postRequest: PostRequestDto): ResponseEntity<PostResponseDto> {
        val postResponseDto = postService.save(postRequest)
        return status(HttpStatus.CREATED).body(postResponseDto)
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostResponseDto>> {
        return status(HttpStatus.OK).body(postService.getAllPosts())
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<PostResponseDto> {
        return status(HttpStatus.OK).body(postService.getPost(id))
    }

    @GetMapping("/by-subreddit/{id}")
    fun getPostsBySubreddit(@PathVariable id: Long): ResponseEntity<List<PostResponseDto>> {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id))
    }

    @GetMapping("/by-user/{name}")
    fun getPostsByUsername(@PathVariable name: String): ResponseEntity<List<PostResponseDto>> {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name))
    }


}