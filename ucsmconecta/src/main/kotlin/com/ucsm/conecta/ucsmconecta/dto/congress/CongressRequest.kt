package com.ucsm.conecta.ucsmconecta.dto.congress

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class CongressRequest(
    @get:NotNull(message = "The name is mandatory")
    @get:NotBlank(message = "The name cannot be blank")
    @get:NotEmpty(message = "The name cannot be empty")
    val name: String,

    @get:NotNull(message = "The date is mandatory")
    @get:FutureOrPresent(message = "The start date must be today or in the future")
    val startDate: LocalDate,

    @get:NotNull(message = "The date is mandatory")
    @get:Future(message = "The end date must be in the future")
    val endDate: LocalDate,

    @get:NotNull(message = "The total number of attendances is mandatory")
    val minAttendanceRequired: Int,

    @get:NotNull(message = "The number of refreshments is mandatory")
    val maxRefreshmentsAllowed: Int,

    @get:NotNull(message = "The professional school code is mandatory")
    @get:NotBlank(message = "The professional school code cannot be blank")
    @get:NotEmpty(message = "The professional school code cannot be empty")
    @get:Size(min = 8, message = "The professional school code must be at least 8 characters")
    val professionalSchoolCode: String
)
