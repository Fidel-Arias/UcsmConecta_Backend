package com.ucsm.conecta.ucsmconecta.services.users

import com.ucsm.conecta.ucsmconecta.domain.users.participant.TipoParticipante
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.users.participante.TipoParticipanteRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TipoParticipanteService @Autowired constructor(
    private val tipoParticipanteRepository: TipoParticipanteRepository
){
    // Metodo para buscar un TipoParticipante por su descripción
    fun searchByDescripcion(descripcion: String): TipoParticipante = tipoParticipanteRepository.findByDescripcion(descripcion)
        .orElseThrow { ResourceNotFoundException("Tipo de Participante no encontrado") }

    // Metodo para buscar un TipoParticipante por su id
    fun searchById(id: Long): TipoParticipante = tipoParticipanteRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Tipo de Participante con id $id no encontrado") }

    // Metodo para crear un nuevo TipoParticipante
    @Transactional
    fun createTipoParticipante(@Valid descripcion: String): TipoParticipante {
        val nuevoTipoParticipante = TipoParticipante(
            descripcion = descripcion
        )

        return tipoParticipanteRepository.save(nuevoTipoParticipante)
    }

    // Metodo para obtener todos los Tipos de Participantes
    fun getAllTiposParticipantes(): List<TipoParticipante> = tipoParticipanteRepository.findAll()

    // Metodo para eliminar un TipoParticipante por su id
    @Transactional
    fun deleteTipoParticipanteById(id: Long) {
        val tipoParticipante = searchById(id)
        tipoParticipanteRepository.delete(tipoParticipante)
    }
}