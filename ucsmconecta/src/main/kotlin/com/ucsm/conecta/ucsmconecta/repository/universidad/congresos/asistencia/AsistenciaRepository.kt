package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.asistencia

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.asistencia.Asistencia
import org.springframework.data.jpa.repository.JpaRepository

interface AsistenciaRepository: JpaRepository<Asistencia, Long> {
    fun existsByParticipanteIdAndBloqueIdAndCongresoId(
        participanteId: Long,
        bloqueId: Long,
        congresoId: Long
    ): Boolean

    fun countByParticipanteNumDocumentoAndCongresoCodigo(
        participanteNumDocumento: String,
        congresoCodigo: String
    ): Int
}