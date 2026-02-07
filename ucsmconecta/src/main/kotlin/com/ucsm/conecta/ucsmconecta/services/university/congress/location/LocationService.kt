package com.ucsm.conecta.ucsmconecta.services.university.congress.location

import com.ucsm.conecta.ucsmconecta.domain.university.congress.location.Location
import com.ucsm.conecta.ucsmconecta.repository.university.congress.location.LocationRepository
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocationService @Autowired constructor(
    private val locationRepository: LocationRepository
) {
    fun getLocationById(id: Long): Location = locationRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Location with id $id not found") }

    fun searchByName(name: String): Location = locationRepository.findByName(name)
        .orElseThrow { ResourceNotFoundException("Location not found") }

    fun getAllLocations(): List<Location> = locationRepository.findAllByIsActiveTrueOrderByIdAsc()
}
