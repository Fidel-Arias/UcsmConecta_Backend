package com.ucsm.conecta.ucsmconecta.services.university.congress.conference_day

import com.ucsm.conecta.ucsmconecta.domain.university.congress.conference_day.ConferenceDay
import com.ucsm.conecta.ucsmconecta.repository.university.congress.conference_day.ConferenceDayRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConferenceDayService @Autowired constructor(
    private val conferenceDayRepository: ConferenceDayRepository
) {
    fun getConferenceDayById(id: Long): ConferenceDay = conferenceDayRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Conference Day with id $id not found") }

    fun getAllConferenceDays(): List<ConferenceDay> = conferenceDayRepository.findAllByIsActiveTrueOrderByIdAsc()
}
