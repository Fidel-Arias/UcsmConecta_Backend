package com.ucsm.conecta.ucsmconecta.domain.universidad.congress

import com.ucsm.conecta.ucsmconecta.domain.users.user.User
import jakarta.persistence.*

@Entity
@Table(name = "congress_user")
class CongressUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_congress_user_congress"))
    open val congress: Congress,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = ForeignKey(name = "FK_congress_user_user"))
    open val user: User
) {
}