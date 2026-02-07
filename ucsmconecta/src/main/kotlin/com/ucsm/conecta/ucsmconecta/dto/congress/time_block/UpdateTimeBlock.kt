package com.ucsm.conecta.ucsmconecta.dto.congress.time_block

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalTime

data class UpdateTimeBlock(
    @get:FutureOrPresent(message = "The start time cannot be in the past")
    val startTime: LocalTime? = null,

    @get:Future(message = "The 'end time' must be after the start time")
    val endTime: LocalTime? = null,

    val locationId: Long? = null,
    val presentationId: Long? = null
)
