package com.ucsm.conecta.ucsmconecta.domain.university.congress

import com.ucsm.conecta.ucsmconecta.domain.users.participant.Participant
import com.ucsm.conecta.ucsmconecta.domain.users.participant.ParticipantCongressStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "participant_congress")
class ParticipantCongress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_id"))
    val participant: Participant,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_congress_id"))
    val congress: Congress,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: ParticipantCongressStatus = ParticipantCongressStatus.MATRICULADO,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
) {
}