package com.ucsm.conecta.ucsmconecta.dto.congress

import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolResponse
import java.time.LocalDate

data class CongressResponse(
    val id: Long,
    val code: String,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val minAttendanceRequired: Int,
    val maxRefreshmentsAllowed: Int,
    val professionalSchool: ProfessionalSchoolResponse?
)
