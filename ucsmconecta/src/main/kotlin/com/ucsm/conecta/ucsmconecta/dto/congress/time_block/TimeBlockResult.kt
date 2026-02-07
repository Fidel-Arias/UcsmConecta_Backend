package com.ucsm.conecta.ucsmconecta.dto.congress.time_block

import java.time.LocalTime

data class TimeBlockResult(
    val id: Long,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val conferenceDay: com.ucsm.conecta.ucsmconecta.dto.congress.conference_day.ConferenceDayAttendanceResult,
    val location: com.ucsm.conecta.ucsmconecta.dto.congress.location.LocationResult,
    val presentation: com.ucsm.conecta.ucsmconecta.dto.congress.presentations.PresentationResult
)
