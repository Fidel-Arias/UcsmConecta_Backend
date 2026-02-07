package com.ucsm.conecta.ucsmconecta.repository.university.congress.refreshment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.refreshment.Refreshment
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional

interface RefreshmentRepository: JpaRepository<Refreshment, Long> {
    // Method to count the number of refreshments per participant
    fun countByParticipant_IdAndCongress_IdAndDate(participantId: Long, congressId: Long, date: LocalDate): Int
    fun countByParticipant_IdAndCongress_Id(participantId: Long, congressId: Long): Int

    // Method to find the participant's refreshment by their document number
    fun findByParticipant_DocumentNumber(documentNumber: String): List<Refreshment>

    // Method to get a participant's refreshment by their document number (the most recent one)
    fun findTopByParticipant_DocumentNumberOrderByDateDescTimeDesc(documentNumber: String): Optional<Refreshment>
}
