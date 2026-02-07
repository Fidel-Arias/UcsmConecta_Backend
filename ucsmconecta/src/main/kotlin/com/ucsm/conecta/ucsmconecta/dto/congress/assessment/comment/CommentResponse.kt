package com.ucsm.conecta.ucsmconecta.dto.congress.assessment.comment

import com.ucsm.conecta.ucsmconecta.dto.users.profile.participant.DataResultParticipant
import java.time.LocalDate
import java.time.LocalTime

data class CommentResponse(
    val id: Long,
    val comment: String,
    val date: LocalDate,
    val time: LocalTime,
    val participant: DataResultParticipant
)
