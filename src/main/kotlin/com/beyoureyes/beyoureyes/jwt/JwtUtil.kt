package com.beyoureyes.beyoureyes.jwt

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import javax.crypto.SecretKey
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*


@Component
class JwtUtil {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(userId : Long) : String{
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(secretKey)
            .compact()

    }

    fun validateToken(token: String) : Boolean {
        return try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJwt(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserIdFromToken(token: String) :Long {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJwt(token).body
        return claims.subject.toLong()
    }

}