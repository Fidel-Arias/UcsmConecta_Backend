package com.ucsm.conecta.ucsmconecta.repository.universidad.gradoacademico

import com.ucsm.conecta.ucsmconecta.domain.university.gradoacademico.GradoAcademico
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GradoAcademicoRepository: JpaRepository<GradoAcademico, Long> {
    fun findByDescripcion(descripcion: String): Optional<GradoAcademico>
}