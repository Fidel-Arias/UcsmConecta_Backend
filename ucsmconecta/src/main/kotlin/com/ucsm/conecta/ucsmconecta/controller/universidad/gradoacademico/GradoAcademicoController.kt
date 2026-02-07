package com.ucsm.conecta.ucsmconecta.controller.universidad.gradoacademico

import com.ucsm.conecta.ucsmconecta.domain.university.gradoacademico.GradoAcademico
import com.ucsm.conecta.ucsmconecta.dto.university.gradoacademico.DataRequestGradoAcademico
import com.ucsm.conecta.ucsmconecta.dto.university.gradoacademico.DataResponseGradoAcademico
import com.ucsm.conecta.ucsmconecta.services.universidad.gradoacademico.GradoAcademicoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/grados-academicos")
class GradoAcademicoController @Autowired constructor(
    private val gradoAcademicoService: GradoAcademicoService
) {
    @PostMapping
    fun createGradoAcademico(@RequestBody @Valid dataRequestGradoAcademico: DataRequestGradoAcademico, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<DataResponseGradoAcademico> {
        // Lógica para crear un grado académico
        val gradoAcademico: GradoAcademico = gradoAcademicoService.createGradoAcademico(dataRequestGradoAcademico)

        // Se pasan los datos creados a DataResponseGradoAcademico para visualizarlos
        val dataResponseGradoAcademico = DataResponseGradoAcademico(
            id = gradoAcademico.id!!,
            descripcion = gradoAcademico.descripcion
        )

        // Construir la URI del nuevo recurso creado
        val uri = uriComponentsBuilder.path("/api/grados-academicos/{id}")
            .buildAndExpand(gradoAcademico.id)
            .toUri()

        // Retornar la respuesta con el código de estado 201 Created y la ubicación del nuevo recurso
        return ResponseEntity.created(uri).body(dataResponseGradoAcademico)
    }

    @GetMapping
    fun getAllGradosAcademicos(): ResponseEntity<List<DataResponseGradoAcademico>> {
        val gradosAcademicos = gradoAcademicoService.getAllGradosAcademicos()

        if (gradosAcademicos.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseGradoAcademico = gradosAcademicos.map { grado ->
            DataResponseGradoAcademico(
                id = grado.id!!,
                descripcion = grado.descripcion
            )
        }
        return ResponseEntity.ok(dataResponseGradoAcademico)
    }

    @GetMapping("/{id}")
    fun getGradoAcademicoById(@PathVariable id: Long): ResponseEntity<DataResponseGradoAcademico> {
        // Lógica para obtener un grado académico por su ID
        val gradoAcademico: GradoAcademico = gradoAcademicoService.getGradoAcademicoById(id)
        val dataResponseGradoAcademico = DataResponseGradoAcademico(
            id = gradoAcademico.id!!,
            descripcion = gradoAcademico.descripcion
        )
        return ResponseEntity.ok(dataResponseGradoAcademico)
    }

}