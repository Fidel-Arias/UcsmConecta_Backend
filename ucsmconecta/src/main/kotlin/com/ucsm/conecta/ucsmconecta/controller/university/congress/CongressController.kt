package com.ucsm.conecta.ucsmconecta.controller.university.congress

import com.ucsm.conecta.ucsmconecta.domain.university.congress.Congress
import com.ucsm.conecta.ucsmconecta.dto.academic.professional_school.ProfessionalSchoolResponse
import com.ucsm.conecta.ucsmconecta.services.university.congress.CongressService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/congresses")
class CongressController @Autowired constructor(
    private val congressService: CongressService
) {
    @PostMapping
    fun createCongress(@RequestBody @Valid congressRequest: com.ucsm.conecta.ucsmconecta.dto.congress.CongressRequest, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse> {
        val congress: Congress = congressService.createCongress(congressRequest)

        val congressResponse = com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse(
            id = congress.id!!,
            code = congress.code,
            name = congress.name,
            startDate = congress.startDate,
            endDate = congress.endDate,
            minAttendanceRequired = congress.minAttendanceRequired,
            maxRefreshmentsAllowed = congress.maxRefreshmentsAllowed,
            professionalSchool = ProfessionalSchoolResponse(
                id = congress.professionalSchool.id!!,
                name = congress.professionalSchool.name,
                code = congress.professionalSchool.code
            )
        )

        val uri = uriComponentsBuilder.path("/api/congresses/{id}")
            .buildAndExpand(congress.id)
            .toUri()

        return ResponseEntity.created(uri).body(congressResponse)
    }

    @GetMapping("/{id}")
    fun getCongressById(@PathVariable id: Long): ResponseEntity<com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse> {
        val congress: Congress = congressService.getCongressById(id)
        val congressResponse = com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse(
            id = congress.id!!,
            code = congress.code,
            name = congress.name,
            startDate = congress.startDate,
            endDate = congress.endDate,
            minAttendanceRequired = congress.minAttendanceRequired,
            maxRefreshmentsAllowed = congress.maxRefreshmentsAllowed,
            professionalSchool = ProfessionalSchoolResponse(
                id = congress.professionalSchool.id!!,
                name = congress.professionalSchool.name,
                code = congress.professionalSchool.code
            )
        )
        return ResponseEntity.ok(congressResponse)
    }

    @GetMapping("/search/code")
    fun getCongressByCode(@RequestParam code: String): ResponseEntity<com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse> {
        val congress: Congress = congressService.searchByCode(code)
        val congressResponse = com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse(
            id = congress.id!!,
            code = congress.code,
            name = congress.name,
            startDate = congress.startDate,
            endDate = congress.endDate,
            minAttendanceRequired = congress.minAttendanceRequired,
            maxRefreshmentsAllowed = congress.maxRefreshmentsAllowed,
            professionalSchool = ProfessionalSchoolResponse(
                id = congress.professionalSchool.id!!,
                name = congress.professionalSchool.name,
                code = congress.professionalSchool.code
            )
        )
        return ResponseEntity.ok(congressResponse)
    }

    @GetMapping("/search/name")
    fun searchCongressByName(@RequestParam name: String): ResponseEntity<com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse> {
        val congress: Congress = congressService.searchByName(name)
        val congressResponse = com.ucsm.conecta.ucsmconecta.dto.congress.CongressResponse(
            id = congress.id!!,
            code = congress.code,
            name = congress.name,
            startDate = congress.startDate,
            endDate = congress.endDate,
            minAttendanceRequired = congress.minAttendanceRequired,
            maxRefreshmentsAllowed = congress.maxRefreshmentsAllowed,
            professionalSchool = ProfessionalSchoolResponse(
                id = congress.professionalSchool.id!!,
                name = congress.professionalSchool.name,
                code = congress.professionalSchool.code
            )
        )
        return ResponseEntity.ok(congressResponse)
    }
}
