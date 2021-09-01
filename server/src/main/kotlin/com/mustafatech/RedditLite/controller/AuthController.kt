package com.mustafatech.RedditLite.controller

import com.mustafatech.RedditLite.dto.AuthenticationResponseDto
import com.mustafatech.RedditLite.dto.LoginRequestDto
import com.mustafatech.RedditLite.dto.RegisterRequestDto
import com.mustafatech.RedditLite.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(@RequestBody registerRequest: RegisterRequestDto): ResponseEntity<Any> {
        authService.signup(registerRequest)
        return ResponseEntity.ok("User Registration Successful")
    }

    @GetMapping("/accountVerification/{token}")
    fun verifyAccount(@PathVariable token: String): ResponseEntity<String> {
        authService.verifyAccount(token)
        return ResponseEntity.ok("Account Activated Successfuly")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginData: LoginRequestDto, req: HttpServletRequest): AuthenticationResponseDto {
        return authService.login(loginData, req.servletPath)
    }

}