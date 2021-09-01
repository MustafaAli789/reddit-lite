package com.mustafatech.RedditLite.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtProvider {

    //Should preferably not be hardcoded here
    private val algorithm = Algorithm.HMAC256("YOUR_SECRET_HERE")

    //10 min default
    private val JWT_LIFESPAN = 10*60*1000

    fun generateAccessToken(username: String, issuer: String, authorities: List<String>): String {
        return createJWT(issuer, username, authorities)
    }

    private fun createJWT(issuer: String, subject: String, authorities: List<String>): String {

        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(Date(System.currentTimeMillis() + JWT_LIFESPAN))
                .withIssuer(issuer)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .withClaim("roles", authorities)
                .sign(algorithm)
    }

    fun validateToken(token: String): DecodedJWT {
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

}