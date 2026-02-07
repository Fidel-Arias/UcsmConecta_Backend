package com.ucsm.conecta.ucsmconecta.repository.university.congress.time_block

import com.ucsm.conecta.ucsmconecta.domain.university.congress.time_block.TimeBlock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalTime

interface TimeBlockRepository: JpaRepository<TimeBlock, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
        FROM TimeBlock b
        WHERE b.location.id = :locationId
        AND b.conferenceDay.id = :conferenceDayId
        AND (
            (:startTime < b.endTime AND :endTime > b.startTime)
        )
    """)
    fun existsByLocationAndConferenceDayAndHourRange(
        locationId: Long,
        conferenceDayId: Long,
        startTime: LocalTime,
        endTime: LocalTime
    ): Boolean

    fun findAllByOrderByIdAsc(): List<TimeBlock>
}
