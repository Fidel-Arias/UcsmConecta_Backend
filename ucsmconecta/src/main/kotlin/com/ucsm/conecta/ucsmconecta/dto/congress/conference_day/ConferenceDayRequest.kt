package com.ucsm.conecta.ucsmconecta.dto.congress.conference_day

import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class ConferenceDayRequest(
    @get:NotNull(message = "The date is mandatory")
    val date: LocalDate,

    @get:NotNull(message = "The congress_cod id is mandatory")
    val congressId: Long
)
