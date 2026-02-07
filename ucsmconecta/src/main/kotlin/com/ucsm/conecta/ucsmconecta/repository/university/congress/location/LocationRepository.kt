package com.ucsm.conecta.ucsmconecta.repository.university.congress.location

import com.ucsm.conecta.ucsmconecta.domain.university.congress.location.Location
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LocationRepository: JpaRepository<Location, Long> {
    fun findByName(name: String): Optional<Location>

    fun findAllByIsActiveTrueOrderByIdAsc(): List<Location>
}
