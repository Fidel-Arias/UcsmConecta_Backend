package com.ucsm.conecta.ucsmconecta.dto.congress.speaker

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class SpeakerRequest(
    @get:NotNull(message = "El nombre es obligatorio")
    @get:NotBlank(message = "El nombre no puede estar en blanco")
    @get:NotEmpty(message = "El nombre no puede estar vacio")
    var names: String,

    @get:NotNull(message = "Los 'apellidos' son obligatorios")
    @get:NotBlank(message = "Los 'apellidos' no pueden estar en blanco")
    @get:NotEmpty(message = "Los 'apellidos' no pueden estar vacio")
    var last_names: String,

    @get:NotNull(message = "El 'grado academico' es obligatorio")
    var academic_degree_id: Long,

    @get:NotNull(message = "El codigo del 'congreso' es obligatorio")
    @get:NotBlank(message = "El codigo del 'congreso' no puede estar en blanco")
    @get:NotEmpty(message = "El codigo del 'congreso' no puede estar vacio")
    var congress_cod: String
)
