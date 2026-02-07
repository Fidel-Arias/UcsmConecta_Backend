package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.Congreso
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CongresoRepository: JpaRepository<Congreso, Long> {
    fun findByNombre(nombre: String): Optional<Congreso>
    fun findByCodigo(codigo: String): Optional<Congreso>
}