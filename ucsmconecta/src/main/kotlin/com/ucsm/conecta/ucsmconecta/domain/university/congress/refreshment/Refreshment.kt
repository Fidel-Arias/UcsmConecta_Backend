package com.ucsm.conecta.ucsmconecta.domain.university.congress.refreshment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.ParticipantCongress
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "refreshment")
open class Refreshment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_congress_id"))
    open val participantCongress: ParticipantCongress,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        participantCongress: ParticipantCongress,
    ) : this(
        null,
        participantCongress,
    )
}