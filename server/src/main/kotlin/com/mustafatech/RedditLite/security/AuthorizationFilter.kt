package com.mustafatech.RedditLite.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mustafatech.RedditLite.exception.SpringRedditException
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationFilter(val jwtProvider: JwtProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, fc: FilterChain) {
        if (req.servletPath == "/api/login" || req.servletPath == "/api/auth/signup") {
            fc.doFilter(req, res)
        } else {
            val authHeader = req.getHeader(AUTHORIZATION)
            if (authHeader !== null && authHeader.startsWith("Bearer ")) {
                try {
                    val decodedJWT = jwtProvider.validateToken(authHeader.substring(7))

                    val userName = decodedJWT.subject
                    val roles = ObjectMapper().readValue<Array<String>>(decodedJWT.getClaim("roles").toString())
                    val authorities = ArrayList<SimpleGrantedAuthority>()
                    roles.forEach { role ->
                        authorities.add(SimpleGrantedAuthority(role))
                    }
                    val authToken = UsernamePasswordAuthenticationToken(userName, null, authorities)

                    SecurityContextHolder.getContext().authentication = authToken
                    fc.doFilter(req, res)
                } catch (e: Exception) {
                    logger.error("Error logging in: " + e.message)
                    throw SpringRedditException("Error logging in")
                }
            } else {
                fc.doFilter(req, res)
            }
        }
    }

    fun getJwtFromReq(req: HttpServletRequest): String {
        val bearerToken = req.getHeader("Authorization")
        return bearerToken.substring(7)
    }
}