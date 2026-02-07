package com.ucsm.conecta.ucsmconecta.domain.users.user

import com.ucsm.conecta.ucsmconecta.domain.university.professional_school.ProfessionalSchool
import com.ucsm.conecta.ucsmconecta.domain.university.congress.CongressUser
import com.ucsm.conecta.ucsmconecta.domain.users.participant.ParticipantType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "names", nullable = false, length = 40)
    open var names: String,

    @Column(name = "paternal_surname", nullable = false, length = 25)
    open var paternalSurname: String,

    @Column(name = "maternal_surname", nullable = false, length = 25)
    open var maternalSurname: String,

    @Column(name = "email", nullable = false, unique = true)
    open val email: String,

    @Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professional_school_id", nullable = false, foreignKey = ForeignKey(name = "FK_professional_school_id"))
    open val professionalSchool: ProfessionalSchool,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_type_id", nullable = false, foreignKey = ForeignKey(name = "FK_participant_type_id"))
    open val participantType: ParticipantType,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val congressUsers: MutableList<CongressUser> = mutableListOf()

) {
    constructor(
        names: String,
        paternalSurname: String,
        maternalSurname: String,
        email: String,
        password: String,
        professionalSchool: ProfessionalSchool,
        participantType: ParticipantType,
    ): this(
        null,
        names,
        paternalSurname,
        maternalSurname,
        email,
        password,
        true,
        professionalSchool,
        participantType
    )

    fun changePassword(encodedPassword: String) {
        this.password = encodedPassword
    }
}