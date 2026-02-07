package com.ucsm.conecta.ucsmconecta.dto.users.profile.collaborator

import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolResponse

data class DataResultCollaborator(
    val id: Long,
    val names: String,
    val professionalSchool: ProfessionalSchoolResponse?
)
