package com.ucsm.conecta.ucsmconecta.dto.congress.presentations

import com.ucsm.conecta.ucsmconecta.dto.users.profile.speakers.DataResultSpeaker

data class PresentationResult(
    val id: Long,
    val name: String,
    val speaker: DataResultSpeaker
)
