package com.ucsm.conecta.ucsmconecta.services.university.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.assessment.Comment
import com.ucsm.conecta.ucsmconecta.repository.university.congress.assessment.CommentRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(
    private val commentRepository: CommentRepository
) {
    fun getCommentById(id: Long): Comment = commentRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Comment with id $id not found") }

    fun getAllComments(): List<Comment> = commentRepository.findAllByIsActiveTrueOrderByIdAsc()
}
