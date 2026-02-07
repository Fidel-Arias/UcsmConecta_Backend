package com.ucsm.conecta.ucsmconecta.repository.users.participante

import com.ucsm.conecta.ucsmconecta.domain.users.participant.TipoParticipante
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TipoParticipanteRepository: JpaRepository<TipoParticipante, Long> {
    fun findByDescripcion(descripcion: String): Optional<TipoParticipante>
}