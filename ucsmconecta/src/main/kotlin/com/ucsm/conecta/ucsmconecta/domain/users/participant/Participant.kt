package com.ucsm.conecta.ucsmconecta.domain.users.participant

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import com.ucsm.conecta.ucsmconecta.domain.university.congress.ParticipantCongress
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "participant")
open class Participant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "names", nullable = false, length = 40)
    open var names: String,

    @Column(name = "paternal_surname", nullable = false, length = 25)
    open var paternalSurname: String,

    @Column(name = "maternal_surname", nullable = false, length = 25)
    open var maternalSurname: String,

    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    open var documentNumber: String,

    @Column(name = "email", nullable = false, unique = true)
    open val email: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_type_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_type_id"))
    open val participantType: ParticipantType,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professional_school_id", nullable = false, foreignKey = ForeignKey(name = "FK_professional_school_id"))
    open val professionalSchool: ProfessionalSchool,

    @Column(name = "qr_code", nullable = true)
    open var qrCode: String?,

    @OneToMany(mappedBy = "participant")
    open val participantCongresses: MutableList<ParticipantCongress> = mutableListOf(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        names: String,
        paternalSurname: String,
        maternalSurname: String,
        documentNumber: String,
        email: String,
        participantType: ParticipantType,
        professionalSchool: ProfessionalSchool,
        qrCode: String? = null,
    ): this(
        null,
        names,
        paternalSurname,
        maternalSurname,
        documentNumber,
        email,
        participantType,
        professionalSchool,
        qrCode,
    )
}