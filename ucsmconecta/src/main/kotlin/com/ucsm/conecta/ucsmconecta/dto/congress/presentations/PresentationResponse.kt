package com.ucsm.conecta.ucsmconecta.dto.congress.presentations

data class PresentationResponse(
    val id: Long,
    val name: String,
    val speakerName: String,
    val timeBlockId: Long
)
