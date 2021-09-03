package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.dto.CommentsDto
import com.mustafatech.RedditLite.exception.PostNotFoundException
import com.mustafatech.RedditLite.model.Comment
import com.mustafatech.RedditLite.model.NotificationEmail
import com.mustafatech.RedditLite.model.Post
import com.mustafatech.RedditLite.model.User
import com.mustafatech.RedditLite.repository.CommentRepo
import com.mustafatech.RedditLite.repository.PostRepo
import com.mustafatech.RedditLite.repository.UserRepo
import lombok.AllArgsConstructor
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant


@Service
@AllArgsConstructor
class CommentService(
        val postRepo: PostRepo,
        val userRepo: UserRepo,
        val authService: AuthService,
        val commentRepo: CommentRepo,
        val mailContentBuilder: MailContentBuilder,
        val mailService: MailService
) {

    fun commentsDtoToComment(commentsDto: CommentsDto): Comment {
        val post = postRepo.findById(commentsDto.postId).orElseThrow{ PostNotFoundException("Post with id ${commentsDto.postId} not found") }
        val user = authService.getCurrentUser()
        return Comment(null, commentsDto.text, post, Instant.now(), user)
    }

    fun commentToCommentDto(comment: Comment): CommentsDto{
        return CommentsDto((comment.id?: -1L), (comment.post.postId?: -1L), comment.createdDate, comment.text, comment.user.username)
    }

    @Transactional
    fun save(commentsDto: CommentsDto) {
        val post: Post = postRepo.findById(commentsDto.postId)
                .orElseThrow { PostNotFoundException("Post with id ${commentsDto.postId} not found") }
        val comment: Comment = commentsDtoToComment(commentsDto)
        commentRepo.save(comment)
        val curAuthedUser = authService.getCurrentUser()
        val message = mailContentBuilder.build("${curAuthedUser.username} posted a comment on your post.$POST_URL")
        sendCommentNotification(message, curAuthedUser, post.user)
    }

    private fun sendCommentNotification(message: String, curAuthedUser: User, postUser: User) {
        mailService.sendMail(NotificationEmail(curAuthedUser.username + " Commented on your post", postUser.email, message))
    }

    @Transactional(readOnly = true)
    fun getAllCommentsForPost(postId: Long): List<CommentsDto> {
        val post: Post = postRepo.findById(postId).orElseThrow { PostNotFoundException("Post with id $postId not found") }
        return commentRepo.findByPost(post).map(this::commentToCommentDto)
    }

    @Transactional(readOnly = true)
    fun getAllCommentsForUser(userName: String): List<CommentsDto> {
        val user: User = userRepo.findByUsername(userName)?: throw UsernameNotFoundException("User with name $userName not found")
        return commentRepo.findByUser(user).map(this::commentToCommentDto)
    }

    companion object {
        private const val POST_URL = ""
    }
}