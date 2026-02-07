package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ponencias

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.Congreso
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ponencias.Ponencia
import com.ucsm.conecta.ucsmconecta.domain.users.ponente.Ponente
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.DataRequestPonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.UpdateDataPonencia
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.ponencias.PonenciaRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.users.PonenteService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class PonenciaService @Autowired constructor(
    private val ponenciaRepository: PonenciaRepository,
    private val ponenteService: PonenteService,
    private val congresoService: CongresoService,
) {
    // Metodo para crear una nueva ponencia
    @Transactional
    fun createPonencia(@RequestBody @Valid dataRequestPonencia: DataRequestPonencia): Ponencia {
        // Buscar ponente relacionado
        val ponente: Ponente = ponenteService.getPonenteById(dataRequestPonencia.ponenteId)

        // Buscar congreso relacionado
        val congreso: Congreso = congresoService.searchByCodigo(dataRequestPonencia.congresoCod)

        return ponenciaRepository.save(Ponencia(
            nombre = dataRequestPonencia.nombre,
            ponente = ponente,
            congreso = congreso
        ))
    }

    // Metodo para buscar una ponencia por su id
    fun getPonenciaById(id: Long, includeInactive: Boolean = false): Ponencia {
        val ponencia = ponenciaRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ponencia con id $id no encontrada") }

        if (!ponencia.estado && !includeInactive)
            throw ResourceNotFoundException("Ponencia con id $id está desactivado o no disponible")

        return ponencia
    }

    // Metodo para obtener todas las ponencias activas
    fun getAllPonencias(): List<Ponencia> = ponenciaRepository.findAllByEstadoTrueOrderByIdAsc()

    fun getPonenciaByNombre(nombre: String): Ponencia = ponenciaRepository.findByNombre(nombre)
        .orElseThrow { ResourceNotFoundException("Ponencia no encontrada por su nombre") }

    // Metodo para desactivar una ponencia
    @Transactional
    fun deactivatePonencia(id: Long): Ponencia {
        val ponencia: Ponencia = getPonenciaById(id)
        ponencia.estado = false
        return ponenciaRepository.save(ponencia)
    }

    // Metodo para activar una ponencia
    @Transactional
    fun activatePonencia(id: Long): Ponencia {
        val ponencia: Ponencia = getPonenciaById(id, includeInactive = true)
        if (ponencia.estado)
            throw IllegalStateException("El ponente ya está activo")
        ponencia.estado = true
        return ponenciaRepository.save(ponencia)
    }

    // Metodo para actualizar una ponencia
    @Transactional
    fun updatePonencia(id: Long, @RequestBody @Valid updateDataPonencia: UpdateDataPonencia): Ponencia {
        val ponencia: Ponencia = getPonenciaById(id)

        // Solo actualiza si los campos no son nulos o vacíos
        updateDataPonencia.nombre.takeIf { !it.isNullOrBlank() }?.let {
            ponencia.nombre = it
        }

        updateDataPonencia.ponenteID?.let { id ->
            val newPonente: Ponente = ponenteService.getPonenteById(id)
            ponencia.ponente = newPonente
        }

        return ponenciaRepository.save(ponencia)
    }
}