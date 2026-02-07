package com.ucsm.conecta.ucsmconecta.controller.universidad.carrera

import com.ucsm.conecta.ucsmconecta.domain.university.carrera.EscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataRequestEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.services.universidad.carrera.EscuelaProfesionalService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/escuelas-profesionales")
class EscuelaProfesionalController @Autowired constructor(
    private val escuelaProfesionalService: EscuelaProfesionalService
) {
    @PostMapping
    fun createEscuelaProfesional(@RequestBody @Valid dataRequestEscuelaProfesional: DataRequestEscuelaProfesional, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<DataResponseEscuelaProfesional> {
        // Lógica para crear una escuela profesional
        val escuelaProfesional: EscuelaProfesional = escuelaProfesionalService.createEscuelaProfesional(dataRequestEscuelaProfesional)

        // Se pasan los datos creados a DataResponseEscuelaProfesional para visualizarlos
        val dataResponseEscuelaProfesional: DataResponseEscuelaProfesional = DataResponseEscuelaProfesional(
            id = escuelaProfesional.id!!,
            nombre = escuelaProfesional.nombre,
            codigo = escuelaProfesional.codigo
        )

        // Construir la URI del nuevo recurso creado
        val uri = uriComponentsBuilder.path("/api/escuelas-profesionales/{id}")
            .buildAndExpand(escuelaProfesional.id)
            .toUri()

        // Retornar la respuesta con el código de estado 201 Created y la ubicación del nuevo recurso
        return ResponseEntity.created(uri).body(dataResponseEscuelaProfesional)
    }

    @GetMapping
    fun getAllEscuelasProfesionales(): ResponseEntity<List<DataResponseEscuelaProfesional>> {
        val escuelasProfesionales = escuelaProfesionalService.getAllEscuelasProfesionales()

        if (escuelasProfesionales.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseEscuelaProfesional = escuelasProfesionales.map { escuela ->
            DataResponseEscuelaProfesional(
                id = escuela.id!!,
                nombre = escuela.nombre,
                codigo = escuela.codigo
            )
        }
        return ResponseEntity.ok(dataResponseEscuelaProfesional)
    }

    @GetMapping("/search/code")
    fun getEscuelaProfesionalByCodigo(@RequestParam codigo: String): ResponseEntity<DataResponseEscuelaProfesional> {
        // Lógica para obtener una escuela profesional por su ID
        val escuelaProfesional: EscuelaProfesional = escuelaProfesionalService.searchByCodigo(codigo)

        // Se pasan los datos obtenidos a DataResponseEscuelaProfesional para visualizarlos
        val dataResponseEscuelaProfesional = DataResponseEscuelaProfesional(
            id = escuelaProfesional.id!!,
            nombre = escuelaProfesional.nombre,
            codigo = escuelaProfesional.codigo
        )

        // Retornar la respuesta con el código de estado 200 OK y los datos de la escuela profesional
        return ResponseEntity.ok(dataResponseEscuelaProfesional)
    }

    @GetMapping("/search/name")
    fun searchEscuelaProfesionalByNombre(@RequestParam nombre: String): ResponseEntity<DataResponseEscuelaProfesional> {
        // Lógica para buscar una escuela profesional por su nombre
        val escuelaProfesional: EscuelaProfesional = escuelaProfesionalService.searchByNombre(nombre)

        // Se pasan los datos obtenidos a DataResponseEscuelaProfesional para visualizarlos
        val dataResponseEscuelaProfesional = DataResponseEscuelaProfesional(
            id = escuelaProfesional.id!!,
            nombre = escuelaProfesional.nombre,
            codigo = escuelaProfesional.codigo
        )

        // Retornar la respuesta con el código de estado 200 OK y los datos de la escuela profesional
        return ResponseEntity.ok(dataResponseEscuelaProfesional)
    }

    @DeleteMapping("/{id}")
    fun deleteEscuelaProfesionalById(@PathVariable id: Long): ResponseEntity<Void> {
        // Lógica para eliminar una escuela profesional por su ID
        escuelaProfesionalService.deleteEscuelaProfesionalById(id)

        // Retornar la respuesta con el código de estado 204 No Content
        return ResponseEntity.noContent().build()
    }
}