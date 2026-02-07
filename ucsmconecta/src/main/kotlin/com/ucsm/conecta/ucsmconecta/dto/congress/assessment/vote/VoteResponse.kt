package com.ucsm.conecta.ucsmconecta.dto.congress.assessment.vote

import com.ucsm.conecta.ucsmconecta.dto.users.profile.participant.DataResultParticipant

data class VoteResponse(
    val id: Long,
    val score: Int,
    val participant: DataResultParticipant,
    val presentation: com.ucsm.conecta.ucsmconecta.dto.congress.presentations.PresentationResult,
    val congress: com.ucsm.conecta.ucsmconecta.dto.congress.CongressResult,
)
