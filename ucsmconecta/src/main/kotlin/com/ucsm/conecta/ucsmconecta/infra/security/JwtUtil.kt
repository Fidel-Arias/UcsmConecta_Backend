package com.ucsm.conecta.ucsmconecta.infra.security

import com.ucsm.conecta.ucsmconecta.dto.register.CustomUserDetails
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private var jwtExpirationMs: Long = 0

    fun generateToken(user: CustomUserDetails): String {
        val claims = mapOf("role" to user.role)
        return Jwts.builder()
            .subject(user.email ?: user.numDocumento)
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean = try {
        Jwts.parser().setSigningKey(secret.toByteArray()).build().parseClaimsJws(token)
        true
    } catch (e: Exception) {
        false
    }

    fun getRole(token: String): String =
        Jwts.parser().setSigningKey(secret.toByteArray()).build().parseClaimsJws(token)
            .body["role"] as String

    fun getUsernameFromToken(token: String): String =
        Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body.subject
}
