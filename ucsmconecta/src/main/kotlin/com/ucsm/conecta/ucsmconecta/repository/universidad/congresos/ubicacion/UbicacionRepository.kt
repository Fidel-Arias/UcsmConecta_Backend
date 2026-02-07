package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.ubicacion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ubicacion.Ubicacion
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UbicacionRepository: JpaRepository<Ubicacion, Long> {
    fun findByNombre(nombre: String): Optional<Ubicacion>

    fun findAllByEstadoTrueOrderByIdAsc(): List<Ubicacion>
}