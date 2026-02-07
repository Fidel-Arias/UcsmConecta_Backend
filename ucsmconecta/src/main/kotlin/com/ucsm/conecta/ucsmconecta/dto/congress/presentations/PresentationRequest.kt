package com.ucsm.conecta.ucsmconecta.dto.congress.presentations

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PresentationRequest(
    @get:NotNull(message = "The name is mandatory")
    @get:NotBlank(message = "The name cannot be blank")
    @get:NotEmpty(message = "The name cannot be empty")
    val name: String,

    @get:NotNull(message = "The speaker name is mandatory")
    @get:NotBlank(message = "The speaker name cannot be blank")
    @get:NotEmpty(message = "The speaker name cannot be empty")
    val speakerName: String,

    @get:NotNull(message = "The time block id is mandatory")
    val timeBlockId: Long
)
