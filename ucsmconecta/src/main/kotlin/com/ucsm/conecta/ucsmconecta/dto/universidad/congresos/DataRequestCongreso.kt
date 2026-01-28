package com.ucsm.conecta.ucsmconecta.dto.universidad.congresos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class DataRequestCongreso(
    @get:NotNull(message = "El nombre es obligatorio")
    @get:NotBlank(message = "El nombre no puede estar en blanco")
    @get:NotEmpty(message = "El nombre no puede estar vacio")
    val nombre: String,

    @get:NotNull(message = "La fecha es obligatoria")
    @get:FutureOrPresent(message = "La fecha inicial debe ser hoy o en el futuro")
    val fechaInicio: LocalDate,

    @get:NotNull(message = "La fecha es obligatoria")
    @get:Future(message = "La fecha final debe ser en el futuro")
    val fechaFin: LocalDate,

    @get:NotNull(message = "El numero total de asistencias es obligatorio")
    val numAsistencias: Int,

    @get:NotNull(message = "El numero de refrigerios es obligatorio")
    val numRefrigerios: Int,

    @get:NotNull(message = "El codigo de la escuela profesional es obligatorio")
    @get:NotBlank(message = "El codigo de la escuela profesional no puede estar en blanco")
    @get:NotEmpty(message = "El codigo de la escuela profesional no puede estar vacia")
    @get:Size(min = 8, message = "El codigo de la escuela profesional es de al menos 8 caracteres")
    val escuelaProfesionalCod: String
)
