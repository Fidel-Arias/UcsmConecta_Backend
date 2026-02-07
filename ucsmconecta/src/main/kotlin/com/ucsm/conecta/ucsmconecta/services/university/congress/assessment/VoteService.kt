package com.ucsm.conecta.ucsmconecta.services.university.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.assessment.Vote
import com.ucsm.conecta.ucsmconecta.repository.university.congress.assessment.VoteRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VoteService @Autowired constructor(
    private val voteRepository: VoteRepository
) {
    fun getVoteById(id: Long): Vote = voteRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Vote with id $id not found") }
}
