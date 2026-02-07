package com.ucsm.conecta.ucsmconecta.dto.congress.assessment.vote

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class VoteRequest(
    @get:NotNull(message = "The score is mandatory")
    @get:Min(value = 0, message = "The minimum score is 0")
    @get:Max(value = 5, message = "The maximum score is 5")
    val score: Int = 0,

    @get:NotNull(message = "The participant document is mandatory")
    @get:NotBlank(message = "The participant document cannot be blank")
    @get:NotEmpty(message = "The participant document cannot be empty")
    @get:Size(min = 8, message = "The document must be at least 8 characters")
    val participantDocument: String,

    @get:NotNull(message = "The 'presentation' id is mandatory")
    val presentationId: Long,

    @get:NotNull(message = "The 'congress_cod' code is mandatory")
    @get:NotBlank(message = "The 'congress_cod' code cannot be blank")
    @get:NotEmpty(message = "The 'congress_cod' code cannot be empty")
    @get:Size(min = 8, message = "The code must be at least 8 characters")
    val congressCode: String
)
