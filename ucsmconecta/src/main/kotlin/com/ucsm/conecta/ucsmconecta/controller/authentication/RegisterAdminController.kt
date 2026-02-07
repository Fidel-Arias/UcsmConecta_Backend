package com.ucsm.conecta.ucsmconecta.controller.authentication

import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResponseCongreso
import com.ucsm.conecta.ucsmconecta.dto.users.profile.admin.DataResponseAdmin
import com.ucsm.conecta.ucsmconecta.dto.register.admin.RegisterAdminWithCongresoData
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.dia.DiaService
import com.ucsm.conecta.ucsmconecta.services.users.AdminService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/register")
class RegisterAdminController @Autowired constructor(
    private val congresoService: CongresoService,
    private val diaService: DiaService,
    private val adminService: AdminService
) {
    // Endpoint para crear un nuevo administrador con el congreso
    @PostMapping("/administrador")
    fun createAdministradorWithCongreso(@RequestBody @Valid request: RegisterAdminWithCongresoData,
                                        uriBuilder: ServletUriComponentsBuilder
    ): ResponseEntity<Map<String, Any>> {
        // 1. Crear el congreso primero
        val congreso = congresoService.createCongreso(request.congreso)
        diaService.createDia(request.congreso.fechaInicio, request.congreso.fechaFin, congreso.id!!)

        val admin = adminService.createAdmin(request.admin, congreso)

        // 3. Responder con ambos resultados
        val responseBody = mapOf(
            "admin" to DataResponseAdmin(
                id = admin.id!!,
                nombres = admin.nombres,
                aPaterno = admin.aPaterno,
                aMaterno = admin.aMaterno,
                estado = admin.estado,
                escuelaProfesional = DataResponseEscuelaProfesional(
                    id = admin.escuelaProfesional.id!!,
                    nombre = admin.escuelaProfesional.nombre,
                    codigo = admin.escuelaProfesional.codigo
                )
            ),
            "congreso" to DataResponseCongreso(
                id = congreso.id!!,
                codigo = congreso.codigo,
                nombre = congreso.nombre,
                fechaInicio = congreso.fechaInicio,
                fechaFin = congreso.fechaFin,
                numAsistencias = congreso.numAsistencias,
                numRefrigerios = congreso.numRefrigerios,
                escuelaProfesional = DataResponseEscuelaProfesional(
                    id = congreso.escuelaProfesional.id!!,
                    nombre = congreso.escuelaProfesional.nombre,
                    codigo = congreso.escuelaProfesional.codigo
                )
            )
        )

        // Crear la URI para el nuevo recurso creado
        val uri = uriBuilder.path("/api/administrador/{id}")
            .buildAndExpand(admin.id).toUri()

        return ResponseEntity.created(uri).body(responseBody)
    }
}