package com.ucsm.conecta.ucsmconecta.dto.register.admin

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateDataAdministrador(
    val nombres: String? = null,
    val aPaterno: String? = null,
    val aMaterno: String? = null,

    @get:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @get:Size(max = 12, message = "La contraseña debe tener como maximo 12 caracteres")
    @get:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,12}\$",
        message = "La contraseña debe contener al menos una letra mayuscula, una letra minuscula, un numero y un caracter especial"
    )
    val password: String? = null
)