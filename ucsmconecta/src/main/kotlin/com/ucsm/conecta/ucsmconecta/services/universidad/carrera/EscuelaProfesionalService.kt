package com.ucsm.conecta.ucsmconecta.services.universidad.carrera

import com.ucsm.conecta.ucsmconecta.domain.university.carrera.EscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataRequestEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.carrera.EscuelaProfesionalRepository
import com.ucsm.conecta.ucsmconecta.util.UUIDGenerator
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class EscuelaProfesionalService @Autowired constructor(
    private val escuelaProfesionalRepository: EscuelaProfesionalRepository
){
    // Metodo para buscar una escuela profesional por su ID
    fun searchEscuelaProfesionalById(id: Long): EscuelaProfesional = escuelaProfesionalRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Escuela Profesional no encontrada por su id $id") }

    // Metodo para buscar una escuela profesional por su nombre
    fun searchByNombre(nombre: String): EscuelaProfesional = escuelaProfesionalRepository.findByNombre(nombre)
        .orElseThrow { ResourceNotFoundException("Escuela Profesional no encontrada por el nombre") }

    fun searchByCodigo(codigo: String): EscuelaProfesional = escuelaProfesionalRepository.findByCodigo(codigo)
        .orElseThrow { ResourceNotFoundException("Escuela Profesional no encontrada") }

    // Metodo para crear una escuela profesional
    @Transactional
    fun createEscuelaProfesional(@RequestBody @Valid dataRequestEscuelaProfesional: DataRequestEscuelaProfesional): EscuelaProfesional {
        // Lógica para crear una escuela profesional
        return escuelaProfesionalRepository.save(EscuelaProfesional(
            codigo = UUIDGenerator.generarCodigoUnico(),
            nombre = dataRequestEscuelaProfesional.nombre,
        ))
    }

    // Metodo para obtener todas las escuelas profesionales
    fun getAllEscuelasProfesionales(): List<EscuelaProfesional> = escuelaProfesionalRepository.findAll()

    // Metodo para eliminar una escuela profesional por su ID
    @Transactional
    fun deleteEscuelaProfesionalById(id: Long) {
        val escuelaProfesional = searchEscuelaProfesionalById(id)
        escuelaProfesionalRepository.delete(escuelaProfesional)
    }
}