package com.ucsm.conecta.ucsmconecta.repository.university.congress.attendance

import com.ucsm.conecta.ucsmconecta.domain.university.congress.attendance.Attendance
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository: JpaRepository<Attendance, Long> {
    fun existsByParticipantIdAndTimeBlockIdAndCongressId(
        participantId: Long,
        timeBlockId: Long,
        congressId: Long
    ): Boolean

    fun countByParticipantDocumentNumberAndCongressCode(
        participantDocumentNumber: String,
        congressCode: String
    ): Int
}
