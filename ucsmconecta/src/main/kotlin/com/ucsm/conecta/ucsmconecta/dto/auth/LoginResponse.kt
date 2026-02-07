package com.ucsm.conecta.ucsmconecta.dto.auth

data class LoginResponse(
    val token: String,
    val role: String,
    val id: Long
)
