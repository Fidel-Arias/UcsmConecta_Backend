package com.ucsm.conecta.ucsmconecta.repository.university.congress.assessment

import com.ucsm.conecta.ucsmconecta.domain.university.congress.assessment.Vote
import org.springframework.data.jpa.repository.JpaRepository

interface VoteRepository: JpaRepository<Vote, Long> {
}
