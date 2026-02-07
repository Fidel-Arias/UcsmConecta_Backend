package com.ucsm.conecta.ucsmconecta.services.university.academic_degree

import com.ucsm.conecta.ucsmconecta.domain.university.academic_degree.AcademicDegree
import com.ucsm.conecta.ucsmconecta.dto.academic.academic_degree.AcademicDegreeRequest
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.university.academic_degree.AcademicDegreeRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class AcademicDegreeService @Autowired constructor(
    private val academicDegreeRepository: AcademicDegreeRepository
){
    // Method to create an academic degree
    @Transactional
    fun createAcademicDegree(@RequestBody @Valid academicDegreeRequest: AcademicDegreeRequest): AcademicDegree {
        // Logic to create an academic degree
        return academicDegreeRepository.save(AcademicDegree(
            description = academicDegreeRequest.description,
        ))
    }

    // Method to get all academic degrees
    fun getAllAcademicDegrees(): List<AcademicDegree> = academicDegreeRepository.findAll()

    // Method to search an academic degree by id
    fun getAcademicDegreeById(id: Long): AcademicDegree = academicDegreeRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Academic Degree with id $id not found") }

    // Method to search an academic degree by description
    fun searchByDescription(description: String): AcademicDegree = academicDegreeRepository.findByDescription(description)
        .orElseThrow { ResourceNotFoundException("Academic Degree not found") }
}
