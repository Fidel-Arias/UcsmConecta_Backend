package com.ucsm.conecta.ucsmconecta.dto.congress.conference_day

import java.time.LocalDate

data class ConferenceDayResponse(
    val id: Long,
    val date: LocalDate,
    val congressId: Long
)
