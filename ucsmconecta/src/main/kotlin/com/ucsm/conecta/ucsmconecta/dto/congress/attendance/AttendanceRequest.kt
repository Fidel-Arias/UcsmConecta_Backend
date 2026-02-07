package com.ucsm.conecta.ucsmconecta.dto.congress.attendance

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AttendanceRequest(
    @get:NotNull(message = "The participant document is mandatory")
    @get:NotBlank(message = "The participant document cannot be blank")
    @get:NotEmpty(message = "The participant document cannot be empty")
    @get:Size(min = 8, message = "The document must be at least 8 characters")
    val participantDocument: String,

    @get:NotNull(message = "The 'time block' id is mandatory")
    val timeBlockId: Long,

    @get:NotNull(message = "The 'congress_cod' code is mandatory")
    @get:NotBlank(message = "The 'congress_cod' code cannot be blank")
    @get:NotEmpty(message = "The 'congress_cod' code cannot be empty")
    @get:Size(min = 8, message = "The code must be at least 8 characters")
    val congressCode: String
)
