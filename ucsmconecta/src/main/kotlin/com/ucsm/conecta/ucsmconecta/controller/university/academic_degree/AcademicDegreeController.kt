package com.ucsm.conecta.ucsmconecta.controller.university.academic_degree

import com.ucsm.conecta.ucsmconecta.domain.university.academic_degree.AcademicDegree
import com.ucsm.conecta.ucsmconecta.dto.academic.academic_degree.AcademicDegreeRequest
import com.ucsm.conecta.ucsmconecta.dto.academic.academic_degree.AcademicDegreeResponse
import com.ucsm.conecta.ucsmconecta.services.university.academic_degree.AcademicDegreeService
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
@RequestMapping("/api/academic-degrees")
class AcademicDegreeController @Autowired constructor(
    private val academicDegreeService: AcademicDegreeService
) {
    @PostMapping
    fun createAcademicDegree(@RequestBody @Valid academicDegreeRequest: AcademicDegreeRequest, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<AcademicDegreeResponse> {
        // Logic to create an academic degree
        val academicDegree: AcademicDegree = academicDegreeService.createAcademicDegree(academicDegreeRequest)

        // Pass created data to DataResponseAcademicDegree for visualization
        val academicDegreeResponse = AcademicDegreeResponse(
            id = academicDegree.id!!,
            description = academicDegree.description
        )

        // Build the URI of the new resource created
        val uri = uriComponentsBuilder.path("/api/academic-degrees/{id}")
            .buildAndExpand(academicDegree.id)
            .toUri()

        // Return response with status code 201 Created and location of the new resource
        return ResponseEntity.created(uri).body(academicDegreeResponse)
    }

    @GetMapping
    fun getAllAcademicDegrees(): ResponseEntity<List<AcademicDegreeResponse>> {
        val academicDegrees = academicDegreeService.getAllAcademicDegrees()

        if (academicDegrees.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val academicDegreeResponse = academicDegrees.map { degree ->
            AcademicDegreeResponse(
                id = degree.id!!,
                description = degree.description
            )
        }
        return ResponseEntity.ok(academicDegreeResponse)
    }

    @GetMapping("/{id}")
    fun getAcademicDegreeById(@PathVariable id: Long): ResponseEntity<AcademicDegreeResponse> {
        // Logic to get an academic degree by its ID
        val academicDegree: AcademicDegree = academicDegreeService.getAcademicDegreeById(id)
        val academicDegreeResponse = AcademicDegreeResponse(
            id = academicDegree.id!!,
            description = academicDegree.description
        )
        return ResponseEntity.ok(academicDegreeResponse)
    }

}
