package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.dto.RegisterRequestDto
import com.mustafatech.RedditLite.model.NotificationEmail
import com.mustafatech.RedditLite.model.User
import com.mustafatech.RedditLite.model.VerificationToken
import com.mustafatech.RedditLite.repository.UserRepo
import com.mustafatech.RedditLite.repository.VerificationTokenRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class AuthService(val passEncoder: PasswordEncoder,
                  val userRepo: UserRepo,
                  val verificationTokenRepository: VerificationTokenRepository,
val mailService: MailService) {

    @Transactional
    fun signup(registerRequestDto: RegisterRequestDto) {
        val user = User(null, registerRequestDto.username,
                passEncoder.encode(registerRequestDto.password),
                registerRequestDto.email,
                Instant.now(),
        false)
        userRepo.save(user)

        val token = generateVerificationToken(user)
        mailService.sendMail(NotificationEmail(
                "Please Activate your Account",
                user.email,
                "Thank you for signing up to RedditLite. Please click the link below to activate your account :" +
                        "http://localhost:8080/api/auth/accountVerification/" + token
        ))
    }

    // 60 min expiry time
    private fun generateVerificationToken(user: User): String {
        val tokenVal = UUID.randomUUID().toString()
        val verificationToken = VerificationToken(null,
                tokenVal,
                user,
                Date(System.currentTimeMillis() + 60*60*1000).toInstant())
        verificationTokenRepository.save(verificationToken)
        return tokenVal
    }

}