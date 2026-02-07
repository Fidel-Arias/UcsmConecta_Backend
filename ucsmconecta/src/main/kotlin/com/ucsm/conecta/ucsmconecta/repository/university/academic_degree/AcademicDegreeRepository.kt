package com.ucsm.conecta.ucsmconecta.repository.university.academic_degree

import com.ucsm.conecta.ucsmconecta.domain.university.academic_degree.AcademicDegree
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AcademicDegreeRepository: JpaRepository<AcademicDegree, Long> {
    fun findByDescription(description: String): Optional<AcademicDegree>
}
