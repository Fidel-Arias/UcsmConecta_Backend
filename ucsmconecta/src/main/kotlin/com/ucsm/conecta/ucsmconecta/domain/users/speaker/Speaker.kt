package com.ucsm.conecta.ucsmconecta.domain.users.speaker

import com.ucsm.conecta.ucsmconecta.domain.universidad.congress.Congress
import com.ucsm.conecta.ucsmconecta.domain.universidad.academic_degree.AcademicDegree
import jakarta.persistence.*

@Entity
@Table(name = "speaker")
open class Speaker (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Long?,

    @Column(name = "names", nullable = false, length = 40)
    open var names: String,

    @Column(name = "last_name", nullable = false, length = 40)
    open var lastName: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academic_degree_id", nullable = false, foreignKey = ForeignKey(name = "FK_academic_degree_id"))
    open var academicDegree: AcademicDegree,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "congress_id", nullable = false, foreignKey = ForeignKey(name = "FK_congress_id"))
    open val congress: Congress
){
    constructor(
        names: String,
        lastName: String,
        academicDegree: AcademicDegree,
        congress: Congress
    ) : this(
        null,
        names,
        lastName,
        academicDegree,
        congress
    )
}