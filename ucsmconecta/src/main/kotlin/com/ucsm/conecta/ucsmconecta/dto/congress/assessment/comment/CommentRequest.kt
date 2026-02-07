package com.ucsm.conecta.ucsmconecta.dto.congress.assessment.comment

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CommentRequest(
    @get:NotNull(message = "The text is mandatory")
    @get:NotBlank(message = "The text cannot be blank")
    @get:NotEmpty(message = "The text cannot be empty")
    val text: String,

    @get:NotNull(message = "The participant document is mandatory")
    @get:NotBlank(message = "The participant document cannot be blank")
    @get:NotEmpty(message = "The participant document cannot be empty")
    @get:Size(min = 8, message = "The document must be at least 8 characters")
    val participantDocument: String,
)
