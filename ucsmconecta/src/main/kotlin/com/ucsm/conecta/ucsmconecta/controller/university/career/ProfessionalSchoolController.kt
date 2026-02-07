package com.ucsm.conecta.ucsmconecta.controller.university.career

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolRequest
import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolResponse
import com.ucsm.conecta.ucsmconecta.services.university.career.ProfessionalSchoolService
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
@RequestMapping("/api/professional-schools")
class ProfessionalSchoolController @Autowired constructor(
    private val professionalSchoolService: ProfessionalSchoolService
) {
    @PostMapping
    fun createProfessionalSchool(@RequestBody @Valid professionalSchoolRequest: ProfessionalSchoolRequest, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<ProfessionalSchoolResponse> {
        // Logic to create a professional school
        val professionalSchool: ProfessionalSchool = professionalSchoolService.createProfessionalSchool(professionalSchoolRequest)

        // Pass created data to DataResponseProfessionalSchool for visualization
        val professionalSchoolResponse: ProfessionalSchoolResponse = ProfessionalSchoolResponse(
            id = professionalSchool.id!!,
            name = professionalSchool.name,
            code = professionalSchool.code
        )

        // Build the URI of the new resource created
        val uri = uriComponentsBuilder.path("/api/professional-schools/{id}")
            .buildAndExpand(professionalSchool.id)
            .toUri()

        // Return response with status code 201 Created and location of the new resource
        return ResponseEntity.created(uri).body(professionalSchoolResponse)
    }

    @GetMapping
    fun getAllProfessionalSchools(): ResponseEntity<List<ProfessionalSchoolResponse>> {
        val professionalSchools = professionalSchoolService.getAllProfessionalSchools()

        if (professionalSchools.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val professionalSchoolResponse = professionalSchools.map { school ->
            ProfessionalSchoolResponse(
                id = school.id!!,
                name = school.name,
                code = school.code
            )
        }
        return ResponseEntity.ok(professionalSchoolResponse)
    }

    @GetMapping("/search/code")
    fun getProfessionalSchoolByCode(@RequestParam code: String): ResponseEntity<ProfessionalSchoolResponse> {
        // Logic to get a professional school by its code
        val professionalSchool: ProfessionalSchool = professionalSchoolService.searchByCode(code)

        // Pass obtained data to DataResponseProfessionalSchool for visualization
        val professionalSchoolResponse = ProfessionalSchoolResponse(
            id = professionalSchool.id!!,
            name = professionalSchool.name,
            code = professionalSchool.code
        )

        // Return response with status code 200 OK and professional school data
        return ResponseEntity.ok(professionalSchoolResponse)
    }

    @GetMapping("/search/name")
    fun searchProfessionalSchoolByName(@RequestParam name: String): ResponseEntity<ProfessionalSchoolResponse> {
        // Logic to search a professional school by its name
        val professionalSchool: ProfessionalSchool = professionalSchoolService.searchByName(name)

        // Pass obtained data to DataResponseProfessionalSchool for visualization
        val professionalSchoolResponse = ProfessionalSchoolResponse(
            id = professionalSchool.id!!,
            name = professionalSchool.name,
            code = professionalSchool.code
        )

        // Return response with status code 200 OK and professional school data
        return ResponseEntity.ok(professionalSchoolResponse)
    }

    @DeleteMapping("/{id}")
    fun deleteProfessionalSchoolById(@PathVariable id: Long): ResponseEntity<Void> {
        // Logic to delete a professional school by its ID
        professionalSchoolService.deleteProfessionalSchoolById(id)

        // Return response with status code 204 No Content
        return ResponseEntity.noContent().build()
    }
}
