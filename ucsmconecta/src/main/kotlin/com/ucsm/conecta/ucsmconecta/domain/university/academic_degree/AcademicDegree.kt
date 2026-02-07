package com.ucsm.conecta.ucsmconecta.domain.university.academic_degree

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "academic_degree")
open class AcademicDegree(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "description", nullable = false, length = 20)
    open var description: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null
) {
    constructor(
        description: String
    ) : this(
        id = null,
        description = description
    )
}