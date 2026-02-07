package com.ucsm.conecta.ucsmconecta.controller.users.participante

import com.ucsm.conecta.ucsmconecta.domain.users.participant.TipoParticipante
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantTypeRequest
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantTypeResponse
import com.ucsm.conecta.ucsmconecta.services.users.TipoParticipanteService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/tipos-participantes")
class TipoParticipanteController @Autowired constructor(
    private val tipoParticipanteService: TipoParticipanteService
){
    @PostMapping
    fun createTipoParticipante(@RequestBody @Valid participantTypeRequest: ParticipantTypeRequest, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<ParticipantTypeResponse> {

        // Lógica para crear un tipo de participante
        val tipoParticipante: TipoParticipante = tipoParticipanteService.createTipoParticipante(participantTypeRequest.descripcion)

        // Se pasan los datos creados a DataResponseTipoParticipante para visualizarlos
        val participantTypeResponse = ParticipantTypeResponse(
            id = tipoParticipante.id!!,
            descripcion = tipoParticipante.descripcion
        )

        // Construir la URI del nuevo recurso creado
        val uri = uriComponentsBuilder.path("/api/tipos-participantes/{id}")
            .buildAndExpand(tipoParticipante.id)
            .toUri()

        // Retornar la respuesta con el código de estado 201 Created y la ubicación del nuevo recurso
        return ResponseEntity.created(uri).body(participantTypeResponse)

    }

    @GetMapping
    fun getAllTiposParticipantes(): ResponseEntity<List<ParticipantTypeResponse>> {
        val tiposParticipantes = tipoParticipanteService.getAllTiposParticipantes()

        if (tiposParticipantes.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseTiposParticipantes = tiposParticipantes.map { tipo ->
            ParticipantTypeResponse(
                id = tipo.id!!,
                descripcion = tipo.descripcion
            )
        }
        return ResponseEntity.ok(dataResponseTiposParticipantes)
    }

    @GetMapping("/{id}")
    fun getTipoParticipanteById(@PathVariable id: Long): ResponseEntity<ParticipantTypeResponse> {
        val tipoParticipante: TipoParticipante = tipoParticipanteService.searchById(id)

        val participantTypeResponse = ParticipantTypeResponse(
            id = tipoParticipante.id!!,
            descripcion = tipoParticipante.descripcion
        )
        return ResponseEntity.ok(participantTypeResponse)
    }
}