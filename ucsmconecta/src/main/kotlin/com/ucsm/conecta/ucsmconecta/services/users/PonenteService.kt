package com.ucsm.conecta.ucsmconecta.services.users

import com.ucsm.conecta.ucsmconecta.domain.users.ponente.Ponente
import com.ucsm.conecta.ucsmconecta.dto.congress.speaker.SpeakerRequest
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.users.ponente.PonenteRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.gradoacademico.GradoAcademicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class PonenteService @Autowired constructor(
    private val ponenteRepository: PonenteRepository,
    private val gradoAcademicoService: GradoAcademicoService,
    private val congresoService: CongresoService
) {
    // Metodo para crear un nuevo ponente
    @Transactional
    fun createPonente(@RequestBody @Valid speakerRequest: SpeakerRequest): Ponente {
        // Buscar grado academico relacionado
        val gradoAcademico = gradoAcademicoService.getGradoAcademicoById(speakerRequest.gradoAcademicoId)

        // Buscar congreso relacionado
        val congreso = congresoService.searchByCodigo(speakerRequest.congresoCod)

        return ponenteRepository.save(Ponente(
            nombres = speakerRequest.names,
            apellidos = speakerRequest.apellidos,
            gradoAcademico = gradoAcademico,
            congreso = congreso
        ))
    }

    // Método para obtener un ponente por su ID
    fun getPonenteById(id: Long): Ponente = ponenteRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Ponente con id $id no encontrado") }

    // Método para obtener todos los ponentes
    fun getAllPonentes(): List<Ponente> = ponenteRepository.findAllByOrderByIdAsc()

    // Método para obtener un ponente por sus names
    fun getPonenteByNombres(nombres: String): Ponente = ponenteRepository.findByNombres(nombres)
        .orElseThrow { ResourceNotFoundException("Ponente no encontrado por sus names") }

    // Método para eliminar un ponente por su ID
    @Transactional
    fun deletePonente(id: Long) {
        val ponente = getPonenteById(id)
        ponenteRepository.delete(ponente)
    }

    // Método para actualizar un ponente
    @Transactional
    fun updatePonente(id: Long, @RequestBody @Valid updateDataPonente: UpdateDataPonente): Ponente {
        // Obtener el ponente existente
        val ponente: Ponente = getPonenteById(id)

        // Solo actualiza si los campos no son nulos o vacíos
        updateDataPonente.nombres.takeIf { !it.isNullOrBlank() }?.let {
            ponente.nombres = it
        }

        updateDataPonente.apellidos.takeIf { !it.isNullOrBlank() }?.let {
            ponente.apellidos = it
        }

        // Si viene un nuevo grado académico, lo buscamos y asignamos
        updateDataPonente.gradoAcademicoId
            ?.let { gradoID ->
                val nuevoGrado = gradoAcademicoService.getGradoAcademicoById(gradoID)
                ponente.gradoAcademico = nuevoGrado
            }

        return ponenteRepository.save(ponente)
    }
}