package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepo : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?

    fun deleteByToken(token: String)
}