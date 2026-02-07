package com.ucsm.conecta.ucsmconecta.services.universidad.gradoacademico

import com.ucsm.conecta.ucsmconecta.domain.university.gradoacademico.GradoAcademico
import com.ucsm.conecta.ucsmconecta.dto.university.gradoacademico.DataRequestGradoAcademico
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.gradoacademico.GradoAcademicoRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class GradoAcademicoService @Autowired constructor(
    private val gradoAcademicoRepository: GradoAcademicoRepository
){
    // Metodo para crear un grado académico
    @Transactional
    fun createGradoAcademico(@RequestBody @Valid dataRequestGradoAcademico: DataRequestGradoAcademico): GradoAcademico {
        // Lógica para crear un grado académico
        return gradoAcademicoRepository.save(GradoAcademico(
            descripcion = dataRequestGradoAcademico.descripcion,
        ))
    }

    // Metodo para obtener todos los grados académicos
    fun getAllGradosAcademicos(): List<GradoAcademico> = gradoAcademicoRepository.findAll()

    // Metodo para buscar un grado académico por id
    fun getGradoAcademicoById(id: Long): GradoAcademico = gradoAcademicoRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Grado Academico con id $id no encontrado") }

    // Metodo para buscar un grado académico por descripcion
    fun searchByDescripcion(descripcion: String): GradoAcademico = gradoAcademicoRepository.findByDescripcion(descripcion)
        .orElseThrow { ResourceNotFoundException("Grado Academico no encontrado") }
}