package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.valoracion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.Congreso
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ponencias.Ponencia
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.valoracion.Votacion
import com.ucsm.conecta.ucsmconecta.domain.users.participant.Participante
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.valoracion.votaciones.DataRequestVotacion
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.valoracion.VotacionRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ponencias.PonenciaService
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class VotacionService @Autowired constructor(
    private val votacionRepository: VotacionRepository,
    private val participanteService: ParticipanteService,
    private val ponenciaService: PonenciaService,
    private val congresoService: CongresoService
) {
    // Metodo para crear una votacion de una ponencia
    @Transactional
    fun createVotacion(@RequestBody @Valid dataRequestVotacion: DataRequestVotacion): Votacion {
        // Buscar participante asociado
        val participante: Participante = participanteService.searchByNumDocumento(dataRequestVotacion.documentoParticipante)

        // Buscar ponencia asociada
        val ponencia: Ponencia = ponenciaService.getPonenciaById(dataRequestVotacion.ponenciaId)

        // Buscar congreso asociado
        val congreso: Congreso = congresoService.searchByCodigo(dataRequestVotacion.congresoCod)

        // Guardar Votacion
        return votacionRepository.save(Votacion(
            score = dataRequestVotacion.score,
            participante = participante,
            ponencia = ponencia,
            congreso = congreso
        ))
    }

    // Metodo para obtener la votacion por ID
    fun getVotacionById(id: Long): Votacion = votacionRepository.findById(id).
    orElseThrow { ResourceNotFoundException("Votaciòn con id $id no encontrado") }

    // Metodo para obtener todas las votaciones
    fun getALlVotaciones(): List<Votacion> = votacionRepository.findAll()
}