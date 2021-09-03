package com.mustafatech.RedditLite.service

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.mustafatech.RedditLite.dto.PostRequestDto
import com.mustafatech.RedditLite.dto.PostResponseDto
import com.mustafatech.RedditLite.exception.PostNotFoundException
import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.exception.SubredditNotFoundException
import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.Subreddit
import com.mustafatech.RedditLite.model.User
import com.mustafatech.RedditLite.model.VoteType
import com.mustafatech.RedditLite.repository.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class PostService(val subredditRepo: SubredditRepo,
                  val authService: AuthService,
                  val commentRepo: CommentRepo,
                  val voteRepo: VoteRepo,
                  val postRepo: PostRepo,
                  val userRepo: UserRepo) {

    private fun postReqDtoToPost(postReq: PostRequestDto, subreddit: Subreddit, user: User): Post {
        return Post(null, postReq.postName, postReq.url, postReq.description, 0, user, Instant.now(), subreddit)
    }

    private fun postToPostResponseDto(post: Post): PostResponseDto {
        val commentCount = commentRepo.findByPost(post).size
        val duration = TimeAgo.using(post.createdDate.toEpochMilli())
        val postResponseDto = PostResponseDto(
                post.postId!!, post.postName, post.url, post.description,
                post.user.username, post.subreddit.name, post.voteCount,
                commentCount, duration, upVote = false, downVote = false
        )
        if (authService.isLoggedIn()) {
            val vote = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser())
            if (vote != null) {
                if (vote.voteType == (VoteType.UPVOTE)) {
                    postResponseDto.upVote = true
                } else if (vote.voteType == VoteType.DOWNVOTE) {
                    postResponseDto.downVote = true
                }
            }
        }
        return postResponseDto
    }


    fun save(postReq: PostRequestDto): PostResponseDto {
        val subreddit = subredditRepo.findByName(postReq.subredditName)?: throw SpringRedditException("Subreddit with name ${postReq.subredditName} does nto exist")
        val curAuthedUser = authService.getCurrentUser()
        val post = postReqDtoToPost(postReq, subreddit, curAuthedUser)
        postRepo.save(post)
        return postToPostResponseDto(post)
    }

    @Transactional(readOnly = true)
    fun getPost(id: Long): PostResponseDto {
        val post = postRepo.findById(id).orElseThrow { PostNotFoundException("Post with id $id not found") }
        return postToPostResponseDto(post)
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostResponseDto> {
        return postRepo.findAll().map(this::postToPostResponseDto)
    }

    @Transactional(readOnly = true)
    fun getPostsBySubreddit(subredditId: Long): List<PostResponseDto> {
        val subreddit = subredditRepo.findById(subredditId).orElseThrow { SubredditNotFoundException("Subreddit with id $subredditId not found") }
        val postsInSubreddit = postRepo.findAllBySubreddit(subreddit)
        return postsInSubreddit.map(this::postToPostResponseDto)
    }

    @Transactional(readOnly = true)
    fun getPostsByUsername(username: String): List<PostResponseDto> {
        val user = userRepo.findByUsername(username)?: throw UsernameNotFoundException("User with name $username not found")
        return postRepo.findByUser(user).map(this::postToPostResponseDto)
    }

}