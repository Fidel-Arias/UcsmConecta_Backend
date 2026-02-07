package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.refrigerio

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.refrigerio.Refrigerio
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional

interface RefrigerioRepository: JpaRepository<Refrigerio, Long> {
    // Metodo para contar la cantidad de refrigerios por participante
    fun countByParticipante_IdAndCongreso_IdAndFecha(participanteId: Long, congresoId: Long, fecha: LocalDate): Int
    fun countByParticipante_IdAndCongreso_Id(participanteId: Long, congresoId: Long): Int

    //Metodo para encontrar el refrigerio del participante por su numDocumento
    fun findByParticipante_NumDocumento(numDocumento: String): List<Refrigerio>

    //Metodo para obtener un refrigerio del participante por su numDocumento (el mas reciente)
    fun findTopByParticipante_NumDocumentoOrderByFechaDescHoraDesc(numDocumento: String): Optional<Refrigerio>
}