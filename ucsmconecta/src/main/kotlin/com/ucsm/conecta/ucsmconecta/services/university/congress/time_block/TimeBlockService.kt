package com.ucsm.conecta.ucsmconecta.services.university.congress.time_block

import com.ucsm.conecta.ucsmconecta.domain.university.congress.time_block.TimeBlock
import com.ucsm.conecta.ucsmconecta.repository.university.congress.time_block.TimeBlockRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class TimeBlockService @Autowired constructor(
    private val timeBlockRepository: TimeBlockRepository
) {
    fun getTimeBlockById(id: Long): TimeBlock = timeBlockRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Time Block with id $id not found") }

    fun existsByLocationAndConferenceDayAndHourRange(
        locationId: Long,
        conferenceDayId: Long,
        startTime: LocalTime,
        endTime: LocalTime
    ): Boolean = timeBlockRepository.existsByLocationAndConferenceDayAndHourRange(locationId, conferenceDayId, startTime, endTime)

    fun getAllTimeBlocks(): List<TimeBlock> = timeBlockRepository.findAllByOrderByIdAsc()
}
