package com.ucsm.conecta.ucsmconecta.dto.congress.attendance

import com.ucsm.conecta.ucsmconecta.dto.users.profile.participant.DataResultParticipant
import java.time.LocalDate
import java.time.LocalTime

data class AttendanceResponse(
    val id: Long,
    val date: LocalDate,
    val time: LocalTime,
    val participant: DataResultParticipant,
    val timeBlock: com.ucsm.conecta.ucsmconecta.dto.congress.time_block.TimeBlockResult,
    val congress: com.ucsm.conecta.ucsmconecta.dto.congress.CongressResult
)
