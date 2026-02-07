package com.ucsm.conecta.ucsmconecta.dto.congress.time_block

import java.time.LocalTime

data class TimeBlockResponse(
    val id: Long,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val locationId: Long,
    val conferenceDayId: Long
)
