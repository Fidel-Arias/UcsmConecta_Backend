package com.ucsm.conecta.ucsmconecta.dto.users.profile.admin

import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional

data class DataResponseAdmin(
    val id: Long,
    val nombres: String,
    val aPaterno: String,
    val aMaterno: String,
    val estado: Boolean,
    val escuelaProfesional: DataResponseEscuelaProfesional?
)
