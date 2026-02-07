package com.ucsm.conecta.ucsmconecta.services.users

import com.ucsm.conecta.ucsmconecta.domain.users.colaborador.CongresoColaborador
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.users.colaborador.CongresoColaboradorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CongresoColaboradorService@Autowired constructor(
    private val congresoColaboradorRepository: CongresoColaboradorRepository,
) {
    // Metodo para buscar Colaborador y congreso asociados
    fun getColaboradorWithCongresoById(id: Long): CongresoColaborador = congresoColaboradorRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Colaborador con congreso no encontrado") }
}