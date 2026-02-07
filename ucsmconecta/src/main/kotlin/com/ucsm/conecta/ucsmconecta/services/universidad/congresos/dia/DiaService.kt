package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.dia

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.dia.Dia
import com.ucsm.conecta.ucsmconecta.exceptions.ResourceNotFoundException
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.dia.DiaRepository
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiaService @Autowired constructor(
    private val diaRepository: DiaRepository,
    private val congresoService: CongresoService
) {
    // Metodo para crear un nuevo día de congreso
    @Transactional
    fun createDia(fechaInicio: LocalDate, fechaFin: LocalDate, congresoId: Long) {
        // Obtener el congreso asociado al día
        val congreso = congresoService.getCongresoById(congresoId)

        // Obtencion del numero de dias total
        val diasTotal = calcDiasTotal(fechaInicio, fechaFin)

        for (nDia in 0..<diasTotal) {
            val day = fechaInicio.dayOfMonth + nDia
            val month = fechaInicio.month
            val year = fechaInicio.year

            val fechaCompleta = LocalDate.of(year,month,day)

            diaRepository.save(Dia(
                fecha = fechaCompleta,
                congreso = congreso
            ))
        }
    }

    // Metodo para calcular la cantidad de dias del congreso
    fun calcDiasTotal(fechaInicio: LocalDate, fechaFin: LocalDate): Int {
        val diaInicial = fechaInicio.dayOfMonth
        val diaFinal = fechaFin.dayOfMonth
        if (diaInicial == diaFinal)
            return 1
        else if (diaFinal > diaInicial) {
            var numDias = 0
            for (i in diaInicial..diaFinal) {
                numDias += 1
            }
            return numDias
        } else throw IllegalArgumentException("La fecha de inicio no puede ser mayor a la fecha final del congreso")
    }

    // Método para obtener un día por su ID
    fun getDiaById(id: Long, includeInactive: Boolean = false): Dia {
        val dia = diaRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Día con id $id no encontrado")
        }

        if (!dia.estado && !includeInactive)
            throw ResourceNotFoundException("Dia con id $id está desactivado o no disponible")

        return dia
    }

    // Método para obtener todos los días
    fun getAllDias(): List<Dia> = diaRepository.findAllByEstadoTrueOrderByIdAsc()

    // Método para desactivar un día
    @Transactional
    fun deactivateDia(id: Long) {
        val dia = getDiaById(id)
        dia.estado = false
        diaRepository.save(dia)
    }

    // Método para activar un día
    @Transactional
    fun activateDia(id: Long) {
        val dia = getDiaById(id, includeInactive = true)
        if (dia.estado)
            throw IllegalStateException("El día ya está activo")
        dia.estado = true
        diaRepository.save(dia)
    }

    // Método para eliminar un día por su ID
    @Transactional
    fun deleteDiaById(id: Long) {
        val dia = getDiaById(id)

        if (!dia.estado)
            throw IllegalStateException("El día ya está activo")
        diaRepository.delete(dia)
    }
}