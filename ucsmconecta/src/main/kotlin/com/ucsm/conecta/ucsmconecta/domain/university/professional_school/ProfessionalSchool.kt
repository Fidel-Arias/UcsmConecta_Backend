package com.ucsm.conecta.ucsmconecta.domain.university.professional_school

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "professional_school")
open class ProfessionalSchool(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "code", nullable = false, length = 8)
    open val code: String,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        code: String,
        name: String
    ) : this(
        null,
        code,
        name
    )
}