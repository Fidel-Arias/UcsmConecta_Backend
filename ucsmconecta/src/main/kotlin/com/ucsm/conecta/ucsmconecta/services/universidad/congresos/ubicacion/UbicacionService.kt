package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ubicacion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ubicacion.Ubicacion
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataRequestUbicacion
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.ubicacion.UbicacionRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class UbicacionService @Autowired constructor(
    private val ubicacionRepository: UbicacionRepository
) {
    // Metodo para crear una nueva Ubicacion
    @Transactional
    fun createUbicacion(@RequestBody @Valid dataRequestUbicacion: DataRequestUbicacion): Ubicacion = ubicacionRepository.save(Ubicacion(
        nombre = dataRequestUbicacion.nombre
    ))

   // Metodo para obtener una Ubicacion por su nombre
    fun getUbicacionByNombre(nombre: String): Ubicacion? {
        return ubicacionRepository.findByNombre(nombre)
            .orElseThrow{ ResourceNotFoundException("Ubicacion no encontrada") }
    }

    // Metodo para obtener todas las Ubicaciones
    fun getAllUbicaciones(): List<Ubicacion> = ubicacionRepository.findAllByEstadoTrueOrderByIdAsc()

    // Metodo para obtener una Ubicacion por su ID
    fun getUbicacionById(id: Long, includeInactive: Boolean = false): Ubicacion {
        val ubicacion: Ubicacion = ubicacionRepository.findById(id)
            .orElseThrow{ ResourceNotFoundException("Ubicacion con id $id no encontrada") }

        if (!ubicacion.estado && !includeInactive)
            throw ResourceNotFoundException("Ubicacion con id $id está desactivado o no disponible")

        return ubicacion
    }

    // Metodo para desactivar una Ubicacion
    @Transactional
    fun deactivateUbicacion(id: Long): Ubicacion {
        val ubicacion = getUbicacionById(id)
        ubicacion.estado = false
        return ubicacionRepository.save(ubicacion)
    }

    // Metodo para activar una Ubicacion
    @Transactional
    fun activateUbicacion(id: Long): Ubicacion {
        val ubicacion = getUbicacionById(id, includeInactive = true)
        if (ubicacion.estado)
            throw IllegalStateException("La ubicación ya está activada")
        ubicacion.estado = true
        return ubicacionRepository.save(ubicacion)
    }

    // Metodo para actualizar una Ubicacion
    @Transactional
    fun updateUbicacion(id: Long, @RequestBody @Valid dataRequestUbicacion: DataRequestUbicacion): Ubicacion {
        val ubicacion = getUbicacionById(id)
        ubicacion.nombre = dataRequestUbicacion.nombre
        return ubicacionRepository.save(ubicacion)
    }

}