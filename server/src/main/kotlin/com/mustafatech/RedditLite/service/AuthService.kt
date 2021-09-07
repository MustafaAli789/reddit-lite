package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.config.AppConfig
import com.mustafatech.RedditLite.dto.AuthenticationResponseDto
import com.mustafatech.RedditLite.dto.LoginRequestDto
import com.mustafatech.RedditLite.dto.RefreshTokenRequestDto
import com.mustafatech.RedditLite.dto.RegisterRequestDto
import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.model.NotificationEmail
import com.mustafatech.RedditLite.model.User
import com.mustafatech.RedditLite.model.VerificationToken
import com.mustafatech.RedditLite.repository.UserRepo
import com.mustafatech.RedditLite.repository.VerificationTokenRepository
import com.mustafatech.RedditLite.security.JwtProvider
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*


@Service
class AuthService(val passEncoder: PasswordEncoder,
                  val userRepo: UserRepo,
                  val verificationTokenRepository: VerificationTokenRepository,
                  val authManager: AuthenticationManager,
                  val mailService: MailService,
                  val jwtProvider: JwtProvider,
                  val refreshTokenService: RefreshTokenService,
                  val appConfig: AppConfig) {

    @Transactional(readOnly = true)
    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.principal as String
        return userRepo.findByUsername(username)
                ?: throw UsernameNotFoundException("User name not found - $username")
    }

    fun isLoggedIn(): Boolean {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return authentication !is AnonymousAuthenticationToken && authentication.isAuthenticated
    }

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
                        "${appConfig.url}/api/auth/accountVerification/" + token
        ))
    }

    fun login(loginData: LoginRequestDto, servletPath: String): AuthenticationResponseDto {
        try {
            authManager.authenticate(
                    UsernamePasswordAuthenticationToken(loginData.username, loginData.password)
            )
        } catch (e: Exception) {
            throw SpringRedditException("Invalid username/password")
        }
        val user = userRepo.findByUsername(loginData.username)!!
        val accessToken = jwtProvider.generateAccessToken(user.username, servletPath, listOf("User"))
        return  AuthenticationResponseDto(
                accessToken, user.username, refreshTokenService.generateRefreshToken(user).token,
                Instant.now().plusMillis(JwtProvider.REFRESH_LIFESPAN)
        )
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

    fun verifyAccount(token: String) {
        val verToken = verificationTokenRepository.findByToken(token)?: throw SpringRedditException("Invalid Token")
        fetchUserAndEnable(verToken)
    }

    @Transactional
    fun fetchUserAndEnable(verToken: VerificationToken) {
        val username = verToken.user.username
        val user = userRepo.findByUsername(username)?: throw SpringRedditException("User with name $username not found")
        user.enabled = true
        userRepo.save(user)
    }

    fun refreshToken(refreshTokenRequest: RefreshTokenRequestDto, servletPath: String): AuthenticationResponseDto {
        val username = refreshTokenService.validateRefreshToken(refreshTokenRequest.refreshToken)
        val newAccessToken = jwtProvider.generateAccessToken(username, servletPath, listOf("User"))
        return AuthenticationResponseDto(
            newAccessToken, username, refreshTokenRequest.refreshToken,
                Instant.now().plusMillis(JwtProvider.REFRESH_LIFESPAN)
        )
    }

}