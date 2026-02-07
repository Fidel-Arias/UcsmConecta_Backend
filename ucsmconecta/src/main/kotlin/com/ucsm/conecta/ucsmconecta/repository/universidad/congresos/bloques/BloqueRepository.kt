package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.bloques

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.bloques.Bloque
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalTime

interface BloqueRepository: JpaRepository<Bloque, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
        FROM Bloque b
        WHERE b.ubicacion.id = :ubicacionId
        AND b.dia.id = :diaId
        AND (
            (:horaInicio < b.horaFinal AND :horaFinal > b.horaInicio)
        )
    """)
    fun existsByUbicacionAndDiaAndRangoHoras(
        ubicacionId: Long,
        diaId: Long,
        horaInicio: LocalTime,
        horaFinal: LocalTime
    ): Boolean

    fun findAllByOrderByIdAsc(): List<Bloque>
}