package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.dto.VoteDto
import com.mustafatech.RedditLite.exception.PostNotFoundException
import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.Vote
import com.mustafatech.RedditLite.model.VoteType
import com.mustafatech.RedditLite.repository.PostRepo
import com.mustafatech.RedditLite.repository.VoteRepo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@AllArgsConstructor
class VoteService(val voteRepo: VoteRepo,
                  val postRepo: PostRepo,
                  val authService: AuthService) {

    @Transactional
    fun vote(voteDto: VoteDto) {
        val post: Post = postRepo.findById(voteDto.postId).orElseThrow { PostNotFoundException("Post Not Found with ID - " + voteDto.postId) }
        val voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser())
        if (voteByPostAndUser !== null && voteByPostAndUser.voteType == voteDto.voteType) {
            throw SpringRedditException("You have already " + voteDto.voteType + "'d for this post")
        }
        if (VoteType.UPVOTE == voteDto.voteType) {
            post.voteCount += 1
        } else {
            post.voteCount -= 1
        }
        voteRepo.save(mapToVote(voteDto, post))
        postRepo.save(post)
    }

    private fun mapToVote(voteDto: VoteDto, post: Post): Vote {
        return Vote(null, voteDto.voteType, post, authService.getCurrentUser())
    }
}