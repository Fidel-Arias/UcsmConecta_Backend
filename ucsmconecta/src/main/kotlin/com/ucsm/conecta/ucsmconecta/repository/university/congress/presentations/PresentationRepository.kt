package com.ucsm.conecta.ucsmconecta.repository.university.congress.presentations

import com.ucsm.conecta.ucsmconecta.domain.university.congress.presentations.Presentation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PresentationRepository: JpaRepository<Presentation, Long> {
    fun findByName(name: String): Optional<Presentation>
    fun findAllByIsActiveTrueOrderByIdAsc(): List<Presentation>
}
