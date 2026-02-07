package com.ucsm.conecta.ucsmconecta.services.university.congress.refreshment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.refreshment.Refreshment
import com.ucsm.conecta.ucsmconecta.repository.university.congress.refreshment.RefreshmentRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Optional

@Service
class RefreshmentService @Autowired constructor(
    private val refreshmentRepository: RefreshmentRepository
) {
    fun getRefreshmentById(id: Long): Refreshment = refreshmentRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Refreshment with id $id not found") }

    fun countByParticipantIdAndCongressIdAndDate(participantId: Long, congressId: Long, date: LocalDate): Int =
        refreshmentRepository.countByParticipant_IdAndCongress_IdAndDate(participantId, congressId, date)

    fun countByParticipantIdAndCongressId(participantId: Long, congressId: Long): Int =
        refreshmentRepository.countByParticipant_IdAndCongress_Id(participantId, congressId)

    fun findByParticipantDocumentNumber(documentNumber: String): List<Refreshment> =
        refreshmentRepository.findByParticipant_DocumentNumber(documentNumber)

    fun findTopByParticipantDocumentNumberOrderByDateDescTimeDesc(documentNumber: String): Optional<Refreshment> =
        refreshmentRepository.findTopByParticipant_DocumentNumberOrderByDateDescTimeDesc(documentNumber)
}
