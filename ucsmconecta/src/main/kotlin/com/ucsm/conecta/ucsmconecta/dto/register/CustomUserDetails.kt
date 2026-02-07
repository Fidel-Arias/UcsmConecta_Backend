package com.ucsm.conecta.ucsmconecta.dto.register

data class CustomUserDetails(
    val id: Long,
    val email: String?,
    val numDocumento: String?,
    val password: String?,
    val role: String
)

