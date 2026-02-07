package com.ucsm.conecta.ucsmconecta.repository.university.congress.conference_day

import com.ucsm.conecta.ucsmconecta.domain.university.congress.conference_day.ConferenceDay
import org.springframework.data.jpa.repository.JpaRepository

interface ConferenceDayRepository: JpaRepository<ConferenceDay, Long> {
    fun findAllByIsActiveTrueOrderByIdAsc(): List<ConferenceDay>
}
