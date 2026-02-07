package com.ucsm.conecta.ucsmconecta.controller.authentication

import com.ucsm.conecta.ucsmconecta.dto.auth.LoginRequest
import com.ucsm.conecta.ucsmconecta.dto.auth.LoginResponse
import com.ucsm.conecta.ucsmconecta.infra.security.JwtUtil
import com.ucsm.conecta.ucsmconecta.services.users.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController @Autowired constructor(
    private val userService: CustomUserDetailsService,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user = userService.authenticate(request)
        val token = jwtUtil.generateToken(user)
        return ResponseEntity.ok(LoginResponse(token, user.role, user.id))
    }
}