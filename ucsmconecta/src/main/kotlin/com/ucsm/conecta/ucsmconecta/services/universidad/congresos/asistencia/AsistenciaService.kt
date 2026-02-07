package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.asistencia

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.Congreso
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.asistencia.Asistencia
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.bloques.Bloque
import com.ucsm.conecta.ucsmconecta.domain.users.participant.Participante
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.asistencia.DataRequestAsistencia
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.asistencia.AsistenciaRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.bloques.BloqueService
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.LocalTime

@Service
class AsistenciaService @Autowired constructor(
    private val asistenciaRepository: AsistenciaRepository,
    private val participanteService: ParticipanteService,
    private val bloqueService: BloqueService,
    private val congresoService: CongresoService
) {
    // Metodo para crear una nueva asistencia
    @Transactional
    fun createAsistencia(@RequestBody @Valid dataRequestAsistencia: DataRequestAsistencia): Asistencia {
        // Buscar el participante asociado
        val participante: Participante = participanteService.searchByNumDocumento(dataRequestAsistencia.documentoParticipante)

        // Buscar el bloque asociado
        val bloque: Bloque = bloqueService.getBloqueById(dataRequestAsistencia.bloqueId)

        // Buscar el congreso asociado
        val congreso: Congreso = congresoService.searchByCodigo(dataRequestAsistencia.congresoCod)

        // Verificar si ya existe una asistencia para el mismo participante en el mismo bloque y congreso
        if (asistenciaRepository.existsByParticipanteIdAndBloqueIdAndCongresoId(participante.id!!, bloque.id!!, congreso.id!!)) {
            throw IllegalArgumentException("La asistencia ya existe")
        }

        // Obtener hora y fecha actual del sistema
        val fechaActual = LocalDate.now()
        val horaActual = LocalTime.now()

        // 1. Verificar que la fecha coincida con la del bloque
        if (!fechaActual.equals(bloque.dia.fecha)) {
            throw IllegalArgumentException("Asistencia fuera de la fecha del bloque (${bloque.dia.fecha})")
        }

        // 2. Definir la ventana de tolerancia
        val horaInicioConTolerancia = bloque.horaInicio.minusMinutes(30) // 30 min antes del inicio
        val horaFinalConTolerancia = bloque.horaFinal.plusMinutes(10)    // 10 min después del final

        // Validar si la hora actual está dentro del rango permitido
        if (horaActual.isBefore(horaInicioConTolerancia) || horaActual.isAfter(horaFinalConTolerancia)) {
            throw IllegalArgumentException(
                "Asistencia fuera del rango permitido ($horaInicioConTolerancia - $horaFinalConTolerancia)"
            )
        }

        return asistenciaRepository.save(Asistencia(
            participante = participante,
            bloque = bloque,
            congreso = congreso
        ))
    }

    fun contarAsistencias(numDocumento: String, congresoCod: String): Map<String, Any> {
        // Buscar si existe participante
        val participante = participanteService.searchByNumDocumento(numDocumento)

        // Cuenta el total de asistencias
        val totalAsistencias = asistenciaRepository.countByParticipanteNumDocumentoAndCongresoCodigo(numDocumento, congresoCod)

        return mapOf(
            "totalAsistencias" to totalAsistencias
        )
    }

    // Metodo para obtener la asistencia por id
    fun getAsistenciaById(id: Long) = asistenciaRepository.findById(id).
            orElseThrow { ResourceNotFoundException("Asistencia con id $id no encontrado") }
}