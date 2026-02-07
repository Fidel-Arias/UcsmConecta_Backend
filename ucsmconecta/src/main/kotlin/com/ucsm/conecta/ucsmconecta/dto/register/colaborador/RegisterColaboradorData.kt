package com.ucsm.conecta.ucsmconecta.dto.register.colaborador

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern

data class RegisterColaboradorData(
    @get:NotNull(message = "El campo 'names' es obligatorio")
    @get:NotEmpty(message = "El campo 'names' no puede estar vacio")
    @get:NotBlank(message = "El campo 'names' no puede estar en blanco")
    val nombres: String,

    @get:NotNull(message = "El 'apellido paterno' es obligatorio")
    @get:NotEmpty(message = "El 'apellido paterno' no puede estar vacio")
    @get:NotBlank(message = "El 'apellido paterno' no puede estar en blanco")
    val aPaterno: String,

    @get:NotNull(message = "El 'apellido materno' es obligatorio")
    @get:NotEmpty(message = "El 'apellido materno' no puede estar vacio")
    @get:NotBlank(message = "El 'apellido materno' no puede estar en blanco")
    val aMaterno: String,

    @get:NotNull(message = "El email es obligatorio")
    @get:NotEmpty(message = "El email no puede estar vacio")
    @get:NotBlank(message = "El email no puede estar en blanco")
    @get:Email(message = "El email debe tener un formato valido")
    val email: String,

    @get:NotNull(message = "La contraseña es obligatoria")
    @get:NotBlank(message = "La contraseña no puede estar en blanco")
    @get:NotEmpty(message = "La contraseña no puede estar vacia")
    @get:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @get:Size(max = 12, message = "La contraseña debe tener como maximo 12 caracteres")
    @get:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,12}\$",
        message = "La contraseña debe contener al menos una letra mayuscula, una letra minuscula, un numero y un caracter especial"
    )
    val password: String,

    @get:NotNull(message = "El codigo de la escuela es obligatorio")
    @get:NotBlank(message = "El codigo de la escuela no puede estar en blanco")
    @get:NotEmpty(message = "El codigo de la escuela no puede estar vacia")
    @get:Size(min = 8, message = "El codigo es de al menos 8 caracteres")
    val escuelaProfesionalCod: String
)
