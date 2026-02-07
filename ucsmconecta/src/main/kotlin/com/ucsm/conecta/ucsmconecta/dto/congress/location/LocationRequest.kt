package com.ucsm.conecta.ucsmconecta.dto.congress.location

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class LocationRequest(
    @get:NotNull(message = "The name is mandatory")
    @get:NotBlank(message = "The name cannot be blank")
    @get:NotEmpty(message = "The name cannot be empty")
    val name: String,

    @get:NotNull(message = "The capacity is mandatory")
    val capacity: Int
)
