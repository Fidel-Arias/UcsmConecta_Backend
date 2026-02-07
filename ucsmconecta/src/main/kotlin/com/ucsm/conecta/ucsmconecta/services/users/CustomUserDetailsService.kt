package com.ucsm.conecta.ucsmconecta.services.users

import com.ucsm.conecta.ucsmconecta.dto.auth.LoginRequest
import com.ucsm.conecta.ucsmconecta.dto.register.CustomUserDetails
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.users.admin.AdminRepository
import com.ucsm.conecta.ucsmconecta.repository.users.colaborador.ColaboradorRepository
import com.ucsm.conecta.ucsmconecta.repository.users.participante.ParticipanteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService @Autowired constructor(
    private val adminRepository: AdminRepository,
    private val colaboradorRepository: ColaboradorRepository,
    private val participanteRepository: ParticipanteRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun authenticate(request: LoginRequest): CustomUserDetails {
        // 🔹 Login por email (Admin o Colaborador)
        if (!request.email.isNullOrBlank() && !request.password.isNullOrBlank()) {
            // Buscar primero en Administrador
            val admin = adminRepository.findByEmail(request.email)
            if (admin.isPresent) {
                val entity = admin.get()
                if (passwordEncoder.matches(request.password, entity.getPassword())) {
                    return CustomUserDetails(entity.id!!, entity.email, null, entity.getPassword(), "ADMIN")
                } else {
                    throw IllegalArgumentException("Contraseña incorrecta")
                }
            }

            // Si no es admin, buscar en Colaborador
            val colaborador = colaboradorRepository.findByEmail(request.email)
            if (colaborador.isPresent) {
                val entity = colaborador.get()
                if (passwordEncoder.matches(request.password, entity.getPassword())) {
                    return CustomUserDetails(entity.id!!, entity.email, null, entity.getPassword(), "COLABORADOR")
                } else {
                    throw IllegalArgumentException("Contraseña incorrecta")
                }
            }

            // Si no se encontró en ninguno
            throw ResourceNotFoundException("El usuario no existe")
        }

        // Login por número de documento (Participante)
        if (!request.numDocumento.isNullOrBlank()) {
            val participante = participanteRepository.findByNumDocumento(request.numDocumento)
                .orElseThrow { ResourceNotFoundException("El participante no existe") }

            return CustomUserDetails(
                participante.id!!,
                null,
                participante.numDocumento,
                null,
                "PARTICIPANTE"
            )
        }
        throw IllegalArgumentException("Debe proporcionar credenciales válidas (email o número de documento)")
    }
}