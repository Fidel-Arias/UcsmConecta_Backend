package com.ucsm.conecta.ucsmconecta.services.university.career

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolRequest
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.university.career.ProfessionalSchoolRepository
import com.ucsm.conecta.ucsmconecta.util.UUIDGenerator
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class ProfessionalSchoolService @Autowired constructor(
    private val professionalSchoolRepository: ProfessionalSchoolRepository
){
    // Method to search a professional school by its ID
    fun searchProfessionalSchoolById(id: Long): ProfessionalSchool = professionalSchoolRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Professional School not found by id $id") }

    // Method to search a professional school by its name
    fun searchByName(name: String): ProfessionalSchool = professionalSchoolRepository.findByName(name)
        .orElseThrow { ResourceNotFoundException("Professional School not found by name") }

    fun searchByCode(code: String): ProfessionalSchool = professionalSchoolRepository.findByCode(code)
        .orElseThrow { ResourceNotFoundException("Professional School not found") }

    // Method to create a professional school
    @Transactional
    fun createProfessionalSchool(@RequestBody @Valid professionalSchoolRequest: ProfessionalSchoolRequest): ProfessionalSchool {
        // Logic to create a professional school
        return professionalSchoolRepository.save(ProfessionalSchool(
            code = UUIDGenerator.generarCodigoUnico(),
            name = professionalSchoolRequest.name,
        ))
    }

    // Method to get all professional schools
    fun getAllProfessionalSchools(): List<ProfessionalSchool> = professionalSchoolRepository.findAll()

    // Method to delete a professional school by its ID
    @Transactional
    fun deleteProfessionalSchoolById(id: Long) {
        val professionalSchool = searchProfessionalSchoolById(id)
        professionalSchoolRepository.delete(professionalSchool)
    }
}
