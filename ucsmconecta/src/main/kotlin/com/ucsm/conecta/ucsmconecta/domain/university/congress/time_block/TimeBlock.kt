package com.ucsm.conecta.ucsmconecta.domain.university.congress.time_block

import com.ucsm.conecta.ucsmconecta.domain.university.congress.conference_day.ConferenceDay
import com.ucsm.conecta.ucsmconecta.domain.university.congress.location.Location
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "time_block")
open class TimeBlock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "start_time", nullable = false)
    open var startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    open var endTime: LocalTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_day_id", nullable = false)
    open val conferenceDay: ConferenceDay,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    open var location: Location,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null,
) {
    constructor(
        startTime: LocalTime,
        endTime: LocalTime,
        conferenceDay: ConferenceDay,
        location: Location,
    ) : this(
        id = null,
        startTime = startTime,
        endTime = endTime,
        conferenceDay = conferenceDay,
        location = location
    )
}