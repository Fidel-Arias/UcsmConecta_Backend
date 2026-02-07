package com.ucsm.conecta.ucsmconecta.dto.auth

data class LoginRequest(
    val email: String? = null,
    val password: String? = null,
    val numDocumento: String? = null
)
