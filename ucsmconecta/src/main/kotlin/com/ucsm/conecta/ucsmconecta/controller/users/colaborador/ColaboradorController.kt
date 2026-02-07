package com.ucsm.conecta.ucsmconecta.controller.users.colaborador

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.bloques.Bloque
import com.ucsm.conecta.ucsmconecta.domain.university.congresos.ubicacion.Ubicacion
import com.ucsm.conecta.ucsmconecta.domain.users.colaborador.Colaborador
import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResultCongreso
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.asistencia.DataRequestAsistencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.DataResponseBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.dia.DataResultDia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.DataResultPonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.refrigerio.DataRequestRefrigerio
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.refrigerio.DataResponseRefrigerio
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataResponseUbicacion
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataResultUbicacion
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResponseColaborador
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResponseColaboradorWithCongreso
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResultColaborador
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantResult
import com.ucsm.conecta.ucsmconecta.dto.register.participante.RegisterParticipanteDataforColab
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.asistencia.AsistenciaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.bloques.BloqueService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.refrigerio.RefrigerioService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ubicacion.UbicacionService
import com.ucsm.conecta.ucsmconecta.services.users.ColaboradorService
import com.ucsm.conecta.ucsmconecta.services.users.CongresoColaboradorService
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/colaborador")
class ColaboradorController @Autowired constructor(
    private val congresoColaboradorService: CongresoColaboradorService,
    private val colaboradorService: ColaboradorService,
    private val ubicacionService: UbicacionService,
    private val asistenciaService: AsistenciaService,
    private val refrigerioService: RefrigerioService,
    private val bloqueService: BloqueService,
    private val participanteService: ParticipanteService
){
    /******** ENDPOINTS PARA LA ENTIDAD PARTICIPANTE ********/
    @PostMapping("/registrar-participante")
    fun registrarParticipante(
        @RequestBody @Valid registerParticipanteData: RegisterParticipanteDataforColab,
        @RequestParam("escuelaCod") escuelaCod: String,
        @RequestParam("congresoCod") congresoCod: String
    ): ResponseEntity<Map<String, Any>> {
        val resultado = participanteService.registrarParticipantesManual(registerParticipanteData, escuelaCod, congresoCod)
        return ResponseEntity.ok(resultado)
    }
    /******** ENDPOINTS PARA LA ENTIDAD COLABORADOR ********/
    // Endpoint para obtener un colaborador por su id
    @GetMapping("/profile/{id}")
    fun getColaboradorById(@PathVariable id: Long): ResponseEntity<DataResponseColaborador> {
        val colaborador: Colaborador = colaboradorService.getColaboradorById(id)

        if (!colaborador.estado) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseColaborador = DataResponseColaborador(
            id = colaborador.id!!,
            nombres = colaborador.nombres,
            aPaterno = colaborador.aPaterno,
            aMaterno = colaborador.aMaterno,
            email = colaborador.email,
            estado = colaborador.estado,
            escuelaProfesional = DataResponseEscuelaProfesional(
                id = colaborador.escuelaProfesional.id!!,
                nombre = colaborador.escuelaProfesional.nombre,
                codigo = colaborador.escuelaProfesional.codigo
            )
        )

        return ResponseEntity.ok(dataResponseColaborador)
    }

    /******** ENDPOINTS PARA LA ENTIDAD COLABORADOR_CONGRESO ********/
    @GetMapping("/{id}")
    fun getColaboradorWithCongresoById(@PathVariable id: Long): ResponseEntity<DataResponseColaboradorWithCongreso> {
        val colaboradorCongreso = congresoColaboradorService.getColaboradorWithCongresoById(id)

        // Mapear la entidad colaboradorCongreso
        val dataResponseColaboradorWithCongreso = DataResponseColaboradorWithCongreso(
            colaborador = DataResultColaborador(
                id = colaboradorCongreso.colaborador.id!!,
                nombres = colaboradorCongreso.colaborador.nombres,
                escuelaProfesional = DataResponseEscuelaProfesional(
                    id = colaboradorCongreso.colaborador.escuelaProfesional.id!!,
                    nombre = colaboradorCongreso.colaborador.escuelaProfesional.nombre,
                    codigo = colaboradorCongreso.colaborador.escuelaProfesional.codigo
                )
            ),
            congreso = DataResultCongreso(
                id = colaboradorCongreso.congreso.id!!,
                nombre = colaboradorCongreso.congreso.nombre,
                codigo = colaboradorCongreso.congreso.codigo
            )
        )

        return ResponseEntity.ok(dataResponseColaboradorWithCongreso)
    }

    /******** ENDPOINTS PARA LA ENTIDAD ASISTENCIA ********/
    // Endpoint para registrar la asistencia de los participantes por QR
    @PostMapping("/registrar-asistencia")
    fun createAsistencia(@RequestBody @Valid dataRequestAsistencia: DataRequestAsistencia): ResponseEntity<Map<String, String>> {
        // creacion de la asistencia con el servicio
        val asistencia = asistenciaService.createAsistencia(dataRequestAsistencia)
        val response: Map<String, String> = mapOf("success" to "Registro exitoso")

        return ResponseEntity.ok(response)
    }

    /******** ENDPOINTS PARA LA ENTIDAD UBICACION ********/
    // Endpoint para obtener todas las Ubicaciones
    @GetMapping("/ubicaciones")
    fun getAllUbicaciones(): ResponseEntity<List<DataResponseUbicacion>> {
        // Obtener todas las Ubicaciones
        val ubicaciones: List<Ubicacion> = ubicacionService.getAllUbicaciones()

        if (ubicaciones.isEmpty())
            return ResponseEntity.noContent().build()

        // Mapear las ubicaciones a una lista de DataResponseUbicacion
        val dataResponseUbicaciones = ubicaciones.map { ubicacion ->
            DataResponseUbicacion(
                id = ubicacion.id!!,
                nombre = ubicacion.nombre,
                estado = null
            )
        }

        // Retornar la respuesta con la lista de recursos encontrados
        return ResponseEntity.ok(dataResponseUbicaciones)
    }

    /******** ENDPOINTS PARA LA ENTIDAD BLOQUE ********/
    @GetMapping("/bloques")
    fun getAllBloquesByDia(
        @RequestParam("ubicacionId") ubicacionId: Long
    ): ResponseEntity<List<DataResponseBloque>> {
        val bloques: List<Bloque> = bloqueService.getALlBloquesByDiaAndHourAndUbicacion(ubicacionId)

        if (bloques.isEmpty())
            return ResponseEntity.noContent().build()

        val dataResponseBloques: List<DataResponseBloque> = bloques.map { bloque ->
            DataResponseBloque(
                id = bloque.id!!,
                horaInicial = bloque.horaInicio,
                horaFinal = bloque.horaFinal,
                dia = DataResultDia(
                    id = bloque.dia.id!!,
                    fecha = bloque.dia.fecha,
                    congreso = DataResultCongreso(
                        id = bloque.dia.congreso.id!!,
                        nombre = bloque.dia.congreso.nombre,
                        codigo = bloque.dia.congreso.codigo
                    )
                ),
                ubicacion = DataResultUbicacion(
                    id = bloque.ubicacion.id!!,
                    nombre = bloque.ubicacion.nombre,
                ),
                ponencia = DataResultPonencia(
                    id = bloque.ponencia.id!!,
                    nombre = bloque.ponencia.nombre,
                    ponente = DataResultPonente(
                        id = bloque.ponencia.ponente.id!!,
                        nombres = bloque.ponencia.ponente.nombres,
                        apellidos = bloque.ponencia.ponente.apellidos
                    )
                )
            )
        }

        return ResponseEntity.ok(dataResponseBloques)
    }

    /******** ENDPOINTS PARA LA ENTIDAD REFRIGERIO ********/
    // Endpoint para crear refrigerio del participante por QR
    @PostMapping("/registrar-refrigerio")
    fun createRefrigerio(@RequestBody @Valid dataRequestRefrigerio: DataRequestRefrigerio, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<Map<String, String>> {
        // Crear refrigerio
        val refrigerio = refrigerioService.createRefrigerio(dataRequestRefrigerio)

        // Mapear a DataResponseRefrigerio
        val response: Map<String, String> = mapOf("success" to "Refrigerio exitoso")

        // Retornar respuesta con el nuevo recurso creado
        return ResponseEntity.ok(response)
    }

    // Endpoint para obtener el refrigerio por numDocumento de Participante
    @GetMapping("/refrigerio/search")
    fun searchRefrigerioByNumDocumento(@RequestParam numDocumento: String): ResponseEntity<DataResponseRefrigerio> {
        // Obtener refrigerio
        val refrigerio = refrigerioService.getRefrigerioParticipanteByNumDocumento(numDocumento)

        // Mapear a DataResponseRefrigerio
        val dataResponseRefrigerio = DataResponseRefrigerio(
            id = refrigerio.id!!,
            fecha = refrigerio.fecha,
            hora = refrigerio.hora,
            participante = ParticipantResult(
                nombres = refrigerio.participante.nombres,
                apPaterno = refrigerio.participante.apPaterno,
                apMaterno = refrigerio.participante.apMaterno,
                numDocumento = refrigerio.participante.numDocumento
            ),
            congreso = DataResultCongreso(
                id = refrigerio.congreso.id!!,
                nombre = refrigerio.congreso.nombre,
                codigo = refrigerio.congreso.codigo
            )
        )
        // Retornar respuesta con el refrigerio
        return ResponseEntity.ok(dataResponseRefrigerio)
    }
}