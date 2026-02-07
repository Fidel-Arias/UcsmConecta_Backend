package com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.valoracion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.valoracion.Votacion
import org.springframework.data.jpa.repository.JpaRepository

interface VotacionRepository: JpaRepository<Votacion, Long> {
}