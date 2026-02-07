package com.ucsm.conecta.ucsmconecta.repository.users.participante

import com.ucsm.conecta.ucsmconecta.domain.users.participant.Participante
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipanteBusquedaDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ParticipanteRepository : JpaRepository<Participante, Long> {
    fun findByNumDocumento(numDocumento: String): Optional<Participante>

    @Query(
        value = "SELECT * FROM buscar_participante_nombres(:names)",
        nativeQuery = true
    )
    fun findByNombres(@Param("names") nombres: String): List<ParticipanteBusquedaDTO>

    @Query(
        value = "SELECT * FROM buscar_participante_apellidos(:busqueda)",
        nativeQuery = true
    )
    fun findByApellidos(@Param("busqueda") busqueda: String): List<ParticipanteBusquedaDTO>

    @Query("SELECT p.numDocumento FROM Participante p")
    fun findAllNumDocumentos(): List<String>

    fun existsByNumDocumento(numDocumento: String): Boolean
}