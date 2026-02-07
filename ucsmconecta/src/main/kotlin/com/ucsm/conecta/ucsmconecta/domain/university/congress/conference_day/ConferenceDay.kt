package com.ucsm.conecta.ucsmconecta.domain.university.congress.conference_day

import com.ucsm.conecta.ucsmconecta.domain.university.congress.Congress
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "conference_day")
open class ConferenceDay(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "date", nullable = false)
    open var date: LocalDate,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congress_id", nullable = false)
    open val congress: Congress
){
    constructor(
        date: LocalDate,
        congress: Congress
    ) : this(
        id = null,
        date = date,
        isActive = true,
        congress = congress
    )
}