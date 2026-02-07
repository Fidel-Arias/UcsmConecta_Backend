package com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador

import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional

data class DataResultColaborador(
    val id: Long,
    val nombres: String,
    val escuelaProfesional: DataResponseEscuelaProfesional?
)
