package com.ucsm.conecta.ucsmconecta.repository.university.career

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ProfessionalSchoolRepository: JpaRepository<ProfessionalSchool, Long> {
    fun findByName(name: String): Optional<ProfessionalSchool>
    fun findByCode(code: String): Optional<ProfessionalSchool>
}
