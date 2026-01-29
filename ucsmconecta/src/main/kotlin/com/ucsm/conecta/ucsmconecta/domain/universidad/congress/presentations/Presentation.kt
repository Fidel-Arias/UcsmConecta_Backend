package com.ucsm.conecta.ucsmconecta.domain.universidad.congress.presentations

import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.Congress
import com.ucsm.conecta.ucsmconecta.domain.users.speaker.Speaker
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity(name = "Ponencia")
@Table(name = "Ponencia")
open class Presentation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Long?,

    @Column(name = "title", nullable = false)
    open var title: String,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "speaker_id", nullable = false, foreignKey = ForeignKey(name = "FK_speaker_id"))
    open var speaker: Speaker,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_congress_id"))
    open val congress: Congress,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null

) {
    constructor(
        title: String,
        speaker: Speaker,
        congress: Congress
    ) : this(
        id = null,
        title = title,
        isActive = true,
        speaker = speaker,
        congress = congress
    )
}