package com.ucsm.conecta.ucsmconecta.services.users

import com.ucsm.conecta.ucsmconecta.domain.university.carrera.EscuelaProfesional
import com.ucsm.conecta.ucsmconecta.domain.users.colaborador.Colaborador
import com.ucsm.conecta.ucsmconecta.domain.users.colaborador.CongresoColaborador
import com.ucsm.conecta.ucsmconecta.dto.register.colaborador.RegisterColaboradorData
import com.ucsm.conecta.ucsmconecta.dto.register.colaborador.UpdateDataColaborador
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.ColaboradorBusquedaDTO
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.users.colaborador.ColaboradorRepository
import com.ucsm.conecta.ucsmconecta.repository.users.colaborador.CongresoColaboradorRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.carrera.EscuelaProfesionalService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class ColaboradorService @Autowired constructor(
    private val colaboradorRepository: ColaboradorRepository,
    private val congresoColaboradorRepository: CongresoColaboradorRepository,
    private val congresoService: CongresoService,
    private val passwordEncoder: PasswordEncoder,
    private val escuelaProfesionalService: EscuelaProfesionalService
){
    // Metodo para crear un nuevo colaborador
    @Transactional
    fun createColaboradorWithCongreso(@RequestBody @Valid registerColaboradorData: RegisterColaboradorData, codCongreso: String): CongresoColaborador {
        // Buscar escuela profesional
        val escuelaProfesional: EscuelaProfesional = escuelaProfesionalService.searchByCodigo(registerColaboradorData.escuelaProfesionalCod)

        val encodedPassword = passwordEncoder.encode(registerColaboradorData.password)

        // Encontrar congreso
        val congreso = congresoService.searchByCodigo(codCongreso)

        val colaborador = colaboradorRepository.save(Colaborador(
            nombres = registerColaboradorData.nombres,
            aPaterno = registerColaboradorData.aPaterno,
            aMaterno = registerColaboradorData.aMaterno,
            email = registerColaboradorData.email,
            password = encodedPassword,
            escuelaProfesional = escuelaProfesional
        ))

        // Crear y guardar el colaborador con el congreso
        return congresoColaboradorRepository.save(CongresoColaborador(
            colaborador = colaborador,
            congreso = congreso
        ))
    }

    // Metodo para buscar colaboradores por apellidos
    fun searchByApellidos(aPaterno: String?, aMaterno: String?): List<ColaboradorBusquedaDTO> {
        val busqueda = when {
            !aPaterno.isNullOrBlank() && !aMaterno.isNullOrBlank() -> "$aPaterno $aMaterno"
            !aPaterno.isNullOrBlank() -> aPaterno
            !aMaterno.isNullOrBlank() -> aMaterno
            else -> return emptyList()
        }
        return colaboradorRepository.findByApellidos(busqueda)
    }

    // Metodo para buscar colaboradores por names
    fun searchByNombres(nombres: String): List<ColaboradorBusquedaDTO> {
        val resultados = colaboradorRepository.findByNombres(nombres)
            .filter { it.estado }
        return resultados
    }

    // Metodo para obtener un colaborador por su ID
    fun getColaboradorById(id: Long, includeInactive: Boolean = false): Colaborador {
        val colaborador = colaboradorRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Colaborador con id $id no encontrado") }

        if (!colaborador.estado && !includeInactive)
            throw ResourceNotFoundException("Colaborador con id $id está desactivado o no disponible")

        return colaborador
    }

    // Metodo para obtener todos los colaboradores activos
    fun getAllColaboradores(): List<Colaborador> = colaboradorRepository.findAllByEstadoTrueOrderByIdAsc()

    // Metodo para desactivar un colaborador por su ID
    @Transactional
    fun deactivateColaboradorById(id: Long): Colaborador {
        val colaborador: Colaborador = getColaboradorById(id)
        colaborador.estado = false
        return colaboradorRepository.save(colaborador)
    }

    // Metodo para activar un colaborador por su ID
    @Transactional
    fun activateColaboradorById(id: Long): Colaborador {
        val colaborador: Colaborador = getColaboradorById(id, includeInactive = true)

        if (colaborador.estado) {
            throw IllegalStateException("El colaborador ya está activo")
        }

        colaborador.estado = true
        return colaboradorRepository.save(colaborador)
    }

    @Transactional
    // Metodo para eliminar un colaborador por su ID
    fun deleteColaboradorById(id: Long) {
        val colaborador: Colaborador = getColaboradorById(id)
        colaboradorRepository.delete(colaborador)
    }

    // Metodo para editar un colaborador
    @Transactional
    fun editColaborador(id: Long, updatedColaboradorData: UpdateDataColaborador): Colaborador {
        val colaborador: Colaborador = getColaboradorById(id)

        // Solo actualiza si los campos no son nulos o vacíos
        updatedColaboradorData.nombres.takeIf { !it.isNullOrBlank() }?.let {
            colaborador.nombres = it
        }

        updatedColaboradorData.aPaterno.takeIf { !it.isNullOrBlank() }?.let {
            colaborador.aPaterno = it
        }

        updatedColaboradorData.aMaterno.takeIf { !it.isNullOrBlank() }?.let {
            colaborador.aMaterno = it
        }

        updatedColaboradorData.password.takeIf { !it.isNullOrBlank() }?.let {
            colaborador.changePassword(it)
        }

        return colaboradorRepository.save(colaborador)
    }
}