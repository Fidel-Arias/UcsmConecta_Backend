package com.ucsm.conecta.ucsmconecta.domain.universidad.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.ParticipantCongress
import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.presentations.Presentation
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "vote")
open class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "score", nullable = false)
    open var score: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_congress_id"))
    open var participantCongress: ParticipantCongress,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id", nullable = false, foreignKey = ForeignKey(name = "FK_presentation_id"))
    open var presentation: Presentation,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null,

) {
    constructor(
        score: Int,
        participantCongress: ParticipantCongress,
        presentation: Presentation,
    ) : this(
        id = null,
        score = score,
        participantCongress = participantCongress,
        presentation = presentation
    )
}