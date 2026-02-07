package com.ucsm.conecta.ucsmconecta.domain.university.congress.presentations

import com.ucsm.conecta.ucsmconecta.domain.university.congress.ParticipantCongress
import com.ucsm.conecta.ucsmconecta.domain.university.congress.time_block.TimeBlock
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "presentation_registration")
class PresentationRegistration(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_congress_id"))
    open val participantCongress: ParticipantCongress,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "presentation_id", nullable = false, foreignKey = ForeignKey(name = "FK_presentation_id"))
    open val presentation: Presentation,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "time_block_id", nullable = false, foreignKey = ForeignKey(name = "FK_time_block_id"))
    open var timeBlock: TimeBlock,

    @CreationTimestamp
    @Column(name = "registered_at", nullable = false)
    open val registeredAt: LocalDateTime? = null
) {
    constructor(
        participantCongress: ParticipantCongress,
        presentation: Presentation,
        timeBlock: TimeBlock

    ): this(
        null,
        participantCongress,
        presentation,
        timeBlock
    )
}