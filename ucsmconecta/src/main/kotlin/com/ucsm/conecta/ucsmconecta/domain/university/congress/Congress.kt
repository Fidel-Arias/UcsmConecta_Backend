package com.ucsm.conecta.ucsmconecta.domain.university.congress

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "congress_cod")
open class Congress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Long?,

    @Column(name = "code", nullable = false, length = 8)
    open val code: String,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Column(name = "start_date", nullable = false)
    open var startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    open var endDate: LocalDate,

    @Column(name = "min_attendance_required", nullable = false)
    open var minAttendanceRequired: Int = 0,

    @Column(name = "max_refreshments_allowed", nullable = false)
    open var maxRefreshmentsAllowed: Int = 0,

    @Column(name = "daily_refreshment_limit", nullable = false)
    open var dailyRefreshmentLimit: Int = 0,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professional_school_id", nullable = false, foreignKey = ForeignKey(name = "FK_professional_school_id"))
    open val professionalSchool: ProfessionalSchool,

    @OneToMany(mappedBy = "congress")
    open val participantCongresses: MutableList<ParticipantCongress> = mutableListOf(),

    @OneToMany(mappedBy = "congress", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val congressUsers: MutableList<CongressUser> = mutableListOf(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        code: String,
        name: String,
        startDate: LocalDate,
        endDate: LocalDate,
        minAttendanceRequired: Int = 0,
        maxRefreshmentsAllowed: Int = 0,
        dailyRefreshmentLimit: Int = 0,
        isActive: Boolean = true,
        professionalSchool: ProfessionalSchool,
    ) : this(
        null,
        code,
        name,
        startDate,
        endDate,
        minAttendanceRequired,
        maxRefreshmentsAllowed,
        dailyRefreshmentLimit,
        isActive,
        professionalSchool
    )
}