package com.ucsm.conecta.ucsmconecta.domain.universidad.congress.attendance

import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.time_block.TimeBlock
import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.ParticipantCongress
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "attendance")
open class Attendance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_congress_id"))
    open var participantCongress: ParticipantCongress,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "time_block_id", nullable = false, foreignKey = ForeignKey(name = "FK_time_block_id"))
    open var timeBlock: TimeBlock,

    @CreationTimestamp
    @Column(name = "attended_at", nullable = false, updatable = false)
    open val attendedAt: LocalDateTime? = null
) {
    constructor(
        participantCongress: ParticipantCongress,
        timeBlock: TimeBlock,
    ) : this(
        id = null,
        participantCongress = participantCongress,
        timeBlock = timeBlock
    )
}