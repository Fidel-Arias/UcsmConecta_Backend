package com.ucsm.conecta.ucsmconecta.dto.register.participante

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterParticipanteData(
    @get:NotNull(message = "El 'nombre' es obligatorio")
    @get:NotEmpty(message = "El 'nombre' no puede estar vacio")
    @get:NotBlank(message = "El 'nombre' no puede estar en blanco")
    val nombres: String,

    @get:NotNull(message = "El 'apellido paterno' es obligatorio")
    @get:NotEmpty(message = "El 'apellido paterno' no puede estar vacio")
    @get:NotBlank(message = "El 'apellido paterno' no puede estar en blanco")
    val apPaterno: String,

    @get:NotNull(message = "El 'apellido materno' es obligatorio")
    @get:NotEmpty(message = "El 'apellido materno' no puede estar vacio")
    @get:NotBlank(message = "El 'apellido materno' no puede estar en blanco")
    val apMaterno: String,

    @get:NotNull(message = "El 'numero de documento' es obligatorio")
    @get:NotEmpty(message = "El 'numero de documento' no puede estar vacio")
    @get:NotBlank(message = "El 'numero de documento' no puede estar en blanco")
    @get:Size(min = 8, max = 20, message = "El 'numero de documento' debe tener entre 8 y 20 caracteres")
    val numDocumento: String,

    @get:NotNull(message = "El email es obligatorio")
    @get:NotEmpty(message = "El email no puede estar vacio")
    @get:NotBlank(message = "El email no puede estar en blanco")
    @get:Email(message = "El email debe tener un formato valido")
    val email: String,

    @get:NotNull(message = "El campo 'tipo de participante' es obligatorio")
    @get:NotEmpty(message = "El campo 'tipo de participante' no puede estar vacio")
    @get:NotBlank(message = "El campo 'tipo de participante' no puede estar en blanco")
    val tipoParticipante: String,

    @get:NotNull(message = "El 'estado' es obligatorio")
    @get:NotEmpty(message = "El 'estado' no puede estar vacio")
    @get:NotBlank(message = "El 'estado' no puede estar en blanco")
    @get:Size(min = 1, max = 17, message = "El 'estado' debe tener entre 1 y 14 caracteres")
    val estado: String,
)
