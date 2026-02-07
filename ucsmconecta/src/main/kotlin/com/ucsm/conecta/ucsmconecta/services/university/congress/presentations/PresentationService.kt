package com.ucsm.conecta.ucsmconecta.services.university.congress.presentations

import com.ucsm.conecta.ucsmconecta.domain.university.congress.presentations.Presentation
import com.ucsm.conecta.ucsmconecta.repository.university.congress.presentations.PresentationRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PresentationService @Autowired constructor(
    private val presentationRepository: PresentationRepository
) {
    fun getPresentationById(id: Long): Presentation = presentationRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Presentation with id $id not found") }

    fun searchByName(name: String): Presentation = presentationRepository.findByName(name)
        .orElseThrow { ResourceNotFoundException("Presentation not found") }

    fun getAllPresentations(): List<Presentation> = presentationRepository.findAllByIsActiveTrueOrderByIdAsc()
}
