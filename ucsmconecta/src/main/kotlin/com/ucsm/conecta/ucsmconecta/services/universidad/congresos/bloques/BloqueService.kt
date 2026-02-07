package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.bloques

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.bloques.Bloque
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ubicacion.Ubicacion
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.DataRequestBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.UpdateDataBloque
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.bloques.BloqueRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.dia.DiaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ponencias.PonenciaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ubicacion.UbicacionService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDate
import java.time.LocalTime

@Service
class BloqueService @Autowired constructor(
    private val bloqueRepository: BloqueRepository,
    private val ubicacionService: UbicacionService,
    private val ponenciaService: PonenciaService,
    private val diaService: DiaService
) {
    // Metodo para crear un nuevo bloque de congreso
    @Transactional
    fun createBloque(@RequestBody @Valid dataRequestBloque: DataRequestBloque): Bloque {
        // Obtener la ubicación asociada al bloque
        val ubicacion = ubicacionService.getUbicacionById(dataRequestBloque.ubicacionId)

        // Obtener la ponencia asociada al bloque
        val ponencia = ponenciaService.getPonenciaById(dataRequestBloque.ponenciaId)

        // Obtener el día asociado al bloque
        val dia = diaService.getDiaById(dataRequestBloque.diaId)

        // Validar que la hora final sea mayor a la inicial
        if (dataRequestBloque.horaFinal.isBefore(dataRequestBloque.horaInicial) ||
            dataRequestBloque.horaFinal == dataRequestBloque.horaInicial) {
            throw IllegalArgumentException("La 'hora final' debe ser mayor a la hora inicial")
        }

        // Validar que la hora no sea anterior al momento actual si el día es hoy
        val hoy = LocalDate.now()
        if (dia.fecha == hoy && dataRequestBloque.horaInicial.isBefore(LocalTime.now())) {
            throw IllegalArgumentException("La hora de inicio no puede estar en el pasado para el día actual")
        }

        // Verificar si la ubicación ya está ocupada en ese rango
        val existeOcupacion = bloqueRepository.existsByUbicacionAndDiaAndRangoHoras(
            ubicacion.id!!,
            dia.id!!,
            dataRequestBloque.horaInicial,
            dataRequestBloque.horaFinal
        )

        if (existeOcupacion) {
            throw IllegalArgumentException("La ubicación '${ubicacion.nombre}' ya está ocupada en ese horario.")
        }

        // Crear un nuevo bloque a partir de los datos recibidos
        return bloqueRepository.save(Bloque(
            horaInicio = dataRequestBloque.horaInicial,
            horaFinal = dataRequestBloque.horaFinal,
            dia = dia,
            ubicacion = ubicacion,
            ponencia = ponencia
        ))
    }

    // Método para obtener un bloque por su ID
    fun getBloqueById(id: Long): Bloque = bloqueRepository.findById(id).orElseThrow {
        ResourceNotFoundException("Bloque con id $id no encontrado")
    }

    // Método para obtener todos los bloques
    fun getAllBloques(): List<Bloque> = bloqueRepository.findAllByOrderByIdAsc()

    fun getAllBloquesByDia(): List<Bloque> {
        val diaActual = LocalDate.now()
        return bloqueRepository.findAllByOrderByIdAsc()
            .filter { it.dia.fecha == diaActual  }
    }

    // Metodo para obtener los bloques por Dia, hora y Ubicacion
    fun getALlBloquesByDiaAndHourAndUbicacion(ubicacionId: Long): List<Bloque> {
        val diaActual = LocalDate.now()
        val horaActual = LocalTime.now()
        val ubicacion: Ubicacion = ubicacionService.getUbicacionById(ubicacionId)
        return bloqueRepository.findAllByOrderByIdAsc()
            .filter { it.dia.fecha == diaActual && it.ubicacion.id == ubicacion.id && (horaActual.isBefore(it.horaFinal.plusMinutes(10))) }
    }

    // Metodo para eliminar un bloque por su ID
    @Transactional
    fun deleteBloqueById(id: Long) {
        val bloque = getBloqueById(id)
        bloqueRepository.delete(bloque)
    }

    // Metodo para actualizar un bloque
    @Transactional
    fun updateBloque(id: Long, @RequestBody @Valid updateDataBloque: UpdateDataBloque): Bloque {
        // Obtener el bloque a actualizar
        val bloque = getBloqueById(id)

        // Solo actualiza si los campos no son nulos o vacíos
        updateDataBloque.horaInicial?.let { bloque.horaInicio = it }
        updateDataBloque.horaFinal?.let { bloque.horaFinal = it }

        // Si viene un id de ubicación o ponencia, actualizar la relación
        updateDataBloque.ubicacionId?.let { ubicacionId ->
            val nuevaUbicacion = ubicacionService.getUbicacionById(ubicacionId)
            bloque.ubicacion = nuevaUbicacion
        }

        updateDataBloque.ponenciaId?.let { ponenciaId ->
            val nuevaPonencia = ponenciaService.getPonenciaById(ponenciaId)
            bloque.ponencia = nuevaPonencia
        }

        return bloqueRepository.save(bloque)
    }
}