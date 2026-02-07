package com.ucsm.conecta.ucsmconecta.repository.university.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.assessment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByIsActiveTrueOrderByIdAsc(): List<Comment>
}
