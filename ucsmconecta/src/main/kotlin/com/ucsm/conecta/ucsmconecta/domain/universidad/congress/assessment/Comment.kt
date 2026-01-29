package com.ucsm.conecta.ucsmconecta.domain.universidad.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.users.participante.Participant
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
open class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participante_id", nullable = false, foreignKey = ForeignKey(name = "FK_participante_id"))
    var participant: Participant,

    @Column(name = "content", nullable = false)
    var content: String,

    @CreationTimestamp
    @Column(name = "commented_at", nullable = false)
    val commentedAt: LocalDateTime? = null,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean,
) {
    constructor(
        content: String,
        participant: Participant,
        isActive: Boolean
    ) : this(
        id = null,
        participant = participant,
        content = content,
        isActive = isActive
    )
}