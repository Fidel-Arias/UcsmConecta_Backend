package com.ucsm.conecta.ucsmconecta.domain.universidad.congress.location

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "location")
open class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Column(name = "is_active", nullable = false)
    open var isActive: Boolean = true,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open val createdAt: LocalDateTime? = null,
) {
    constructor(
        name: String,
    ) : this(
        id = null,
        name = name,
        isActive = true
    )
}