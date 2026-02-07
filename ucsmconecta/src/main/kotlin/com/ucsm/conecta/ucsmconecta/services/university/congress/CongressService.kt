package com.ucsm.conecta.ucsmconecta.services.university.congress

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import com.ucsm.conecta.ucsmconecta.domain.university.congress.Congress
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.university.congress.CongressRepository
import com.ucsm.conecta.ucsmconecta.services.university.career.ProfessionalSchoolService
import com.ucsm.conecta.ucsmconecta.util.UUIDGenerator
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class CongressService @Autowired constructor(
    private val congressRepository: CongressRepository,
    private val professionalSchoolService: ProfessionalSchoolService
){
    fun getCongressById(id: Long): Congress = congressRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Congress with id $id not found") }

    fun searchByName(name: String): Congress = congressRepository.findByName(name)
        .orElseThrow { ResourceNotFoundException("Congress not found") }

    fun searchByCode(code: String): Congress = congressRepository.findByCode(code)
        .orElseThrow { ResourceNotFoundException("Congress not found") }

    @Transactional
    fun createCongress(@RequestBody @Valid congressRequest: com.ucsm.conecta.ucsmconecta.dto.congress.CongressRequest): Congress {
        val professionalSchool: ProfessionalSchool = professionalSchoolService.searchByCode(
            congressRequest.professionalSchoolCode
        )

        return congressRepository.save(
            Congress(
                code = UUIDGenerator.generarCodigoUnico(),
                name = congressRequest.name,
                startDate = congressRequest.startDate,
                endDate = congressRequest.endDate,
                minAttendanceRequired = congressRequest.minAttendanceRequired,
                maxRefreshmentsAllowed = congressRequest.maxRefreshmentsAllowed,
                professionalSchool = professionalSchool
            )
        )
    }
}
