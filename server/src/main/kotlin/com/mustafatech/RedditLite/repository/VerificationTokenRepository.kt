package com.mustafatech.RedditLite.repository

import com.mustafatech.RedditLite.model.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByTokenOrNull(token: String): VerificationToken?
}