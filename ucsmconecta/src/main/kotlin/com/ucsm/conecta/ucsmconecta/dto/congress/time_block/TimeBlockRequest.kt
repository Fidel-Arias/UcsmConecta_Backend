package com.ucsm.conecta.ucsmconecta.dto.congress.time_block

import jakarta.validation.constraints.NotNull
import java.time.LocalTime

data class TimeBlockRequest(
    @get:NotNull(message = "The start time is mandatory")
    val startTime: LocalTime,

    @get:NotNull(message = "The end time is mandatory")
    val endTime: LocalTime,

    @get:NotNull(message = "The location id is mandatory")
    val locationId: Long,

    @get:NotNull(message = "The conference day id is mandatory")
    val conferenceDayId: Long
)
