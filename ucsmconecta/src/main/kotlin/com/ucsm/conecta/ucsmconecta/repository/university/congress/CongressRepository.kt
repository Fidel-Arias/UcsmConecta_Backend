package com.ucsm.conecta.ucsmconecta.repository.university.congress

import com.ucsm.conecta.ucsmconecta.domain.university.congress.Congress
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CongressRepository: JpaRepository<Congress, Long> {
    fun findByName(name: String): Optional<Congress>
    fun findByCode(code: String): Optional<Congress>
}
