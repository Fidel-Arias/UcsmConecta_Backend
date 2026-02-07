package com.ucsm.conecta.ucsmconecta.dto.participant

import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResultCongreso

data class ParticipantResponse(
    val id: Long,
    val nombres: String,
    val apPaterno: String,
    val apMaterno: String,
    val estado: String,
    val numDocumento: String,
    val tipoParticipante: ParticipantTypeResponse?,
    val escuelaProfesional: DataResponseEscuelaProfesional?,
    val congreso: DataResultCongreso?,
    val qrCode: String
)