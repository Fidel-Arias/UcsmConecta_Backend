package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.valoracion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.valoracion.Comentario
import org.springframework.data.jpa.repository.JpaRepository

interface ComentarioRepository: JpaRepository<Comentario, Long> {
    fun findAllByEstadoTrueOrderByIdAsc(): List<Comentario>
}