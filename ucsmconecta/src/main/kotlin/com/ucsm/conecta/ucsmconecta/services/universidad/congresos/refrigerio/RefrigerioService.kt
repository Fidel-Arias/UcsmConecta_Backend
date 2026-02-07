package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.refrigerio

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.refrigerio.Refrigerio
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.refrigerio.DataRequestRefrigerio
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.refrigerio.RefrigerioRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.dia.DiaService
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.ZoneId

@Service
class RefrigerioService @Autowired constructor(
    private val refrigerioRepository: RefrigerioRepository,
    private val participanteService: ParticipanteService,
    private val congresoService: CongresoService,
    private val diaService: DiaService
) {
    // Metodo para crear refrigerio
    @Transactional
    fun createRefrigerio(@RequestBody @Valid dataRequestRefrigerio: DataRequestRefrigerio): Refrigerio {
        // Buscar participante asociado al refrigerio
        val participante = participanteService.searchByNumDocumento(dataRequestRefrigerio.documentoParticipante)

        // Buscar congreso asociado al refrigerio
        val congreso = congresoService.searchByCodigo(dataRequestRefrigerio.congresoCod)

        // Obtener fecha actual del servidor (no del cliente)
        val fechaHoy = LocalDate.now(ZoneId.of("America/Lima")) // zona horaria local

        // Validar la cantidad de refrigerios asignados al participante en el congreso
        val refrigeriosHoy = refrigerioRepository.countByParticipante_IdAndCongreso_IdAndFecha(participante.id!!, congreso.id!!, fechaHoy)

        if (refrigeriosHoy >= 1) {
            throw IllegalStateException("Límite alcanzado por hoy")
        }

        // Validar que no supere el total de refrigerios permitidos por congreso
        val totalRefrigerios = refrigerioRepository.countByParticipante_IdAndCongreso_Id(
            participante.id!!,
            congreso.id!!
        )

        if (totalRefrigerios >= congreso.numRefrigerios) {
            throw IllegalStateException("Límite de refrigerios del congreso alcanzado")
        }

        // Crear refrigerio
        return refrigerioRepository.save(
            Refrigerio(
                participante = participante,
                congreso = congreso
            )
        )
    }

    // Metodo para obtener refrigerio por id
    fun getAllRefrigeriosByNumDocumento(numDocumento: String): List<Refrigerio> {
        return refrigerioRepository.findByParticipante_NumDocumento(numDocumento)
    }

    //Metodo para obtener el refrigerio mas reciente del participante por su numero de documento
    fun getRefrigerioParticipanteByNumDocumento(numDocumento: String): Refrigerio = refrigerioRepository.findTopByParticipante_NumDocumentoOrderByFechaDescHoraDesc(numDocumento)
        .orElseThrow { ResourceNotFoundException("El participante con el numero de documento $numDocumento no tiene refrigerios registrados") }

}