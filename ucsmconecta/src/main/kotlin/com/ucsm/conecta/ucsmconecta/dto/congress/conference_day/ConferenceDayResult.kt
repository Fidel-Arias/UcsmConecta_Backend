package com.ucsm.conecta.ucsmconecta.dto.congress.conference_day

import java.time.LocalDate

data class ConferenceDayResult(
    val id: Long,
    val date: LocalDate,
    val congress: com.ucsm.conecta.ucsmconecta.dto.congress.CongressResult
)
