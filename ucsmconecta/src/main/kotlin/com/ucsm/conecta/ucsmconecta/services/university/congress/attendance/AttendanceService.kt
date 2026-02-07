package com.ucsm.conecta.ucsmconecta.services.university.congress.attendance

import com.ucsm.conecta.ucsmconecta.domain.university.congress.attendance.Attendance
import com.ucsm.conecta.ucsmconecta.repository.university.congress.attendance.AttendanceRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AttendanceService @Autowired constructor(
    private val attendanceRepository: AttendanceRepository
) {
    fun getAttendanceById(id: Long): Attendance = attendanceRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Attendance with id $id not found") }

    fun existsByParticipantIdAndTimeBlockIdAndCongressId(
        participantId: Long,
        timeBlockId: Long,
        congressId: Long
    ): Boolean = attendanceRepository.existsByParticipantIdAndTimeBlockIdAndCongressId(participantId, timeBlockId, congressId)

    fun countByParticipantDocumentNumberAndCongressCode(
        participantDocumentNumber: String,
        congressCode: String
    ): Int = attendanceRepository.countByParticipantDocumentNumberAndCongressCode(participantDocumentNumber, congressCode)
}
