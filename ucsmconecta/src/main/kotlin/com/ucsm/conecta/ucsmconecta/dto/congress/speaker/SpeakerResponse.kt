package com.ucsm.conecta.ucsmconecta.dto.congress.speaker

import com.ucsm.conecta.ucsmconecta.dto.congress.CongressResult
import com.ucsm.conecta.ucsmconecta.dto.academic.academic_degree.AcademicDegreeResponse

data class SpeakerResponse(
    val id: Long,
    val names: String,
    val last_names: String,
    val academic_degree_id: AcademicDegreeResponse,
    val congress_cod: CongressResult
)
