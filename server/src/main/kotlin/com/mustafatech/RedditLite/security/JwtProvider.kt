package com.mustafatech.RedditLite.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.security.Key
import java.security.PrivateKey
import java.util.*
import javax.crypto.Cipher.SECRET_KEY
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


@Service
class JwtProvider {

    fun generateToken(auth: Authentication, issuer: String): String {
        val user = auth.principal as User
        return createJWT(issuer, user.username, System.currentTimeMillis()+60*1000)
    }

    //https://docs.oracle.com/javase/7/docs/api/javax/xml/bind/DatatypeConverter.html
    private fun createJWT(issuer: String, subject: String, ttlMillis: Long): String {

        //The JWT signature algorithm we will be using to sign the token
        val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
        val nowMillis = System.currentTimeMillis()
        val now = Date(nowMillis)

        //We will sign our JWT with our ApiKey secret
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary("J@NcRfUjXn2r5u8x!A%D*G-KaPdSgVkY")
        val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

        //Let's set the JWT Claims
        val builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(
                        SignatureAlgorithm.HS256,
                        "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="
                )

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            val expMillis = nowMillis + ttlMillis
            val exp = Date(expMillis)
            builder.setExpiration(exp)
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact()
    }

}