package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.model.RefreshToken
import com.mustafatech.RedditLite.model.User
import com.mustafatech.RedditLite.repository.RefreshTokenRepo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*


@Service
@AllArgsConstructor
@Transactional
class RefreshTokenService(val refreshTokenRepo: RefreshTokenRepo) {
    fun generateRefreshToken(curAuthedUser: User): RefreshToken {
        val refreshToken = RefreshToken(
                null, UUID.randomUUID().toString(), Instant.now(), curAuthedUser
        )
        return refreshTokenRepo.save(refreshToken)
    }

    fun validateRefreshToken(token: String): String {
        val refreshToken = refreshTokenRepo.findByToken(token)?: throw SpringRedditException("Invalid refresh Token")
        return refreshToken.user.username
    }

    fun deleteRefreshToken(token: String) {
        refreshTokenRepo.deleteByToken(token)
    }
}