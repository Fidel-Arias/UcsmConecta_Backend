package com.ucsm.conecta.ucsmconecta.dto.congress.refreshment

import com.ucsm.conecta.ucsmconecta.dto.users.profile.participant.DataResultParticipant
import java.time.LocalDate
import java.time.LocalTime

data class RefreshmentResponse(
    val id: Long,
    val date: LocalDate,
    val time: LocalTime,
    val participant: DataResultParticipant,
    val congress: com.ucsm.conecta.ucsmconecta.dto.congress.CongressResult
)
