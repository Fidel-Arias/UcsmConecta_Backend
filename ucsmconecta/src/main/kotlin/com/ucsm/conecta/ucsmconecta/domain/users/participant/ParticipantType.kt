package com.ucsm.conecta.ucsmconecta.domain.users.participant

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "participant_type")
open class ParticipantType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "description", nullable = false, length = 30)
    open var description: String,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        description: String
    ) : this(
        null,
        description
    )
}