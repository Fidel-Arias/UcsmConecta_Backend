package com.ucsm.conecta.ucsmconecta.controller.users.administrador

import com.ucsm.conecta.ucsmconecta.dto.university.carrera.DataResponseEscuelaProfesional
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResponseCongreso
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResultCongreso
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.asistencia.DataResponseAsistencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.DataRequestBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.DataResponseBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.DataResultBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.bloques.UpdateDataBloque
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.dia.DataResponseDia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.dia.DataResultDia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.dia.DataResultDiaAsistencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.DataRequestPonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.DataResponsePonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.DataResultPonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ponencias.UpdateDataPonencia
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataRequestUbicacion
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataResponseUbicacion
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.ubicacion.DataResultUbicacion
import com.ucsm.conecta.ucsmconecta.dto.university.gradoacademico.DataResponseGradoAcademico
import com.ucsm.conecta.ucsmconecta.dto.register.admin.UpdateDataAdministrador
import com.ucsm.conecta.ucsmconecta.dto.register.colaborador.RegisterColaboradorData
import com.ucsm.conecta.ucsmconecta.dto.register.colaborador.UpdateDataColaborador
import com.ucsm.conecta.ucsmconecta.dto.register.participante.UpdateDataParticipante
import com.ucsm.conecta.ucsmconecta.dto.users.profile.admin.DataResponseAdmin
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.ColaboradorBusquedaDTO
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResponseColaborador
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResponseColaboradorWithCongreso
import com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador.DataResultColaborador
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantResponse
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantTypeResponse
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipantResult
import com.ucsm.conecta.ucsmconecta.dto.participant.ParticipanteBusquedaDTO
import com.ucsm.conecta.ucsmconecta.dto.congress.speaker.SpeakerRequest
import com.ucsm.conecta.ucsmconecta.dto.congress.speaker.SpeakerResponse
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.CongresoService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.asistencia.AsistenciaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.bloques.BloqueService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.dia.DiaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ponencias.PonenciaService
import com.ucsm.conecta.ucsmconecta.services.universidad.congresos.ubicacion.UbicacionService
import com.ucsm.conecta.ucsmconecta.services.users.AdminService
import com.ucsm.conecta.ucsmconecta.services.users.ColaboradorService
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import com.ucsm.conecta.ucsmconecta.services.users.PonenteService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/administrador")
class AdministradorController @Autowired constructor(
    private val adminService: AdminService,
    private val congresoService: CongresoService,
    private val diaService: DiaService,
    private val ubicacionService: UbicacionService,
    private val bloqueService: BloqueService,
    private val ponenciaService: PonenciaService,
    private val ponenteService: PonenteService,
    private val colaboradorService: ColaboradorService,
    private val participanteService: ParticipanteService,
    private val asistenciaService: AsistenciaService
) {
    /********* ENDPOINTS PARA LA ENTIDAD ADMIN *********/
    // Endpoint para obtener un administrador por su ID
    @GetMapping("/profile/{id}")
    fun getAdministradorById(@PathVariable id: Long): ResponseEntity<DataResponseAdmin> {
        val admin: Administrador = adminService.getAdminById(id)

        if (!admin.estado) {
            return ResponseEntity.notFound().build()
        }

        val dataResponseAdmin = DataResponseAdmin(
            id = admin.id!!,
            nombres =  admin.nombres,
            aPaterno = admin.aPaterno,
            aMaterno = admin.aMaterno,
            estado = admin.estado,
            escuelaProfesional = DataResponseEscuelaProfesional(
                id = admin.escuelaProfesional.id!!,
                nombre = admin.escuelaProfesional.nombre,
                admin.escuelaProfesional.codigo
            )
        )

        return ResponseEntity.ok(dataResponseAdmin)
    }

    // Endpoint para desactivar un administrador por su ID
    @DeleteMapping("/profile/deactivate/{id}")
    fun deleteAdministrador(@PathVariable id: Long): ResponseEntity<Void> {
        adminService.deactivateAdminById(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para activar un administrador por ID
    @PutMapping("/profile/activate/{id}")
    fun activateAdministrador(@PathVariable id: Long): ResponseEntity<Void> {
        adminService.activateAdminById(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para actualizar el admin
    @PutMapping("/profile/{id}")
    fun updateAdministrador(@PathVariable id: Long, @RequestBody @Valid updateDataAdministrador: UpdateDataAdministrador): ResponseEntity<Void> {
        adminService.updateAdministrador(id, updateDataAdministrador)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD CONGRESO ********/
    // Endpoint para obtener el congreso por su ID
    @GetMapping("/congreso/search")
    fun getCongresoByCodigo(@RequestParam codigo: String): ResponseEntity<DataResponseCongreso> {
        val congreso: Congreso = congresoService.searchByCodigo(codigo)

        // Se pasan los datos a DataResponseCongreso para visualizarlos
        val dataResponseCongreso = DataResponseCongreso(
            id = congreso.id!!,
            codigo = congreso.codigo,
            nombre = congreso.nombre,
            fechaInicio = congreso.fechaInicio,
            fechaFin = congreso.fechaFin,
            numAsistencias = congreso.numAsistencias,
            numRefrigerios = congreso.numRefrigerios,
            escuelaProfesional = DataResponseEscuelaProfesional(
                id = congreso.escuelaProfesional.id!!,
                nombre = congreso.escuelaProfesional.nombre,
                codigo = congreso.escuelaProfesional.codigo
            )
        )

        return ResponseEntity.ok(dataResponseCongreso)
    }

    /******** ENDPOINTS PARA LA ENTIDAD DIA ********/
    // Endpoint para buscar un día por su ID
    @GetMapping("/dia/{id}")
    fun searchDiaById(@PathVariable id: Long): ResponseEntity<DataResponseDia> {
        val dia = diaService.getDiaById(id)

        if (!dia.estado)
            return ResponseEntity.noContent().build()

        val dataResponseDia = DataResponseDia(
            id = dia.id!!,
            fecha = dia.fecha,
            estado = dia.estado,
            congreso = DataResultCongreso(
                id = dia.congreso.id!!,
                nombre = dia.congreso.nombre,
                codigo = dia.congreso.codigo
            )
        )

        return ResponseEntity.ok(dataResponseDia)
    }

    // Endpoint para listar todos los días
    @GetMapping("/dias")
    fun listAllDias(): ResponseEntity<List<DataResponseDia>> {
        val dias = diaService.getAllDias()

        if (dias.isEmpty())
            return ResponseEntity.noContent().build()

        val dataResultDias = dias.map { dia ->
            DataResponseDia(
                id = dia.id!!,
                fecha = dia.fecha,
                estado = dia.estado,
                congreso = DataResultCongreso(
                    id = dia.congreso.id!!,
                    nombre = dia.congreso.nombre,
                    codigo = dia.congreso.codigo
                )
            )
        }
        return ResponseEntity.ok(dataResultDias)
    }

    /******** ENDPOINTS PARA LA ENTIDAD UBICACION ********/
    // Endpoint para crear una nueva Ubicacion
    @PostMapping("/create-ubicacion")
    fun createUbicacion(@RequestBody @Valid dataRequestUbicacion: DataRequestUbicacion, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<DataResponseUbicacion> {
        // Crear una nueva Ubicacion
        val ubicacion: Ubicacion = ubicacionService.createUbicacion(dataRequestUbicacion)

        // Mapear la ubicacion creada a un DataResponseUbicacion
        val dataResponseUbicacion = DataResponseUbicacion(
            id = ubicacion.id!!,
            nombre = ubicacion.nombre,
            estado = ubicacion.estado
        )

        // Construir la URI del recurso creado
        val uri = uriComponentsBuilder.path("/api/ubicaciones/{id}")
            .buildAndExpand(dataResponseUbicacion.id)
            .toUri()

        // Retornar la respuesta con el recurso creado
        return ResponseEntity.created(uri).body(dataResponseUbicacion)
    }

    // Endpoint para buscar una Ubicacion por su ID
    @GetMapping("/ubicacion/{id}")
    fun searchUbicacionById(@PathVariable id: Long): ResponseEntity<DataResponseUbicacion> {
        // Buscar la Ubicacion por su ID
        val ubicacion: Ubicacion = ubicacionService.getUbicacionById(id)

        if (!ubicacion.estado) {
            return ResponseEntity.notFound().build()
        }

        // Mapear la ubicacion encontrada a un DataResponseUbicacion
        val dataResponseUbicacion = DataResponseUbicacion(
            id = ubicacion.id!!,
            nombre = ubicacion.nombre,
            estado = ubicacion.estado
        )

        // Retornar la respuesta con el recurso encontrado
        return ResponseEntity.ok(dataResponseUbicacion)
    }

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
                estado = ubicacion.estado
            )
        }

        // Retornar la respuesta con la lista de recursos encontrados
        return ResponseEntity.ok(dataResponseUbicaciones)
    }

    // Endpoint para desactivar una Ubicacion por su ID
    @DeleteMapping("/ubicacion/deactivate/{id}")
    fun deactivateUbicacion(@PathVariable id: Long): ResponseEntity<Void> {
        // Desactivar la Ubicacion por su ID
        ubicacionService.deactivateUbicacion(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para activar una Ubicacion por su ID
    @PutMapping("/ubicacion/activate/{id}")
    fun activateUbicacion(@PathVariable id: Long): ResponseEntity<Void> {
        // Activar la Ubicacion por su ID
        ubicacionService.activateUbicacion(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para actualizar una Ubicacion por su ID
    @PutMapping("/ubicacion/{id}")
    fun updateUbicacion(@PathVariable id: Long, @RequestBody @Valid dataRequestUbicacion: DataRequestUbicacion): ResponseEntity<Void> {
        // Actualizar la Ubicacion por su ID
        ubicacionService.updateUbicacion(id, dataRequestUbicacion)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD PONENTE ********/
    // Endpoint para crear una nuevo ponente
    @PostMapping("/create-ponente")
    fun createPonente(@RequestBody @Valid speakerRequest: SpeakerRequest, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<SpeakerResponse> {
        // Crear la ponencia utilizando el servicio
        val ponente: Ponente = ponenteService.createPonente(speakerRequest)

        // Mapear la entidad Ponencia a DataResponsePonencia
        val speakerResponse = SpeakerResponse(
            id = ponente.id!!,
            nombres = ponente.nombres,
            apellidos = ponente.apellidos,
            gradoAcademico = DataResponseGradoAcademico(
                id = ponente.gradoAcademico.id!!,
                descripcion = ponente.gradoAcademico.descripcion
            ),
            congreso = DataResultCongreso(
                id = ponente.congreso.id!!,
                nombre = ponente.congreso.nombre,
                codigo = ponente.congreso.codigo
            )
        )

        // Construir la URI del recurso creado
        val uri = uriComponentsBuilder.path("/api/ponentes/{id}")
            .buildAndExpand(ponente.id).toUri()

        // Retornar la respuesta con el código 201 Created y el cuerpo de la ponencia creada
        return ResponseEntity.created(uri).body(speakerResponse)
    }

    // Endpoint para buscar un ponente por su id
    @GetMapping("/ponente/{id}")
    fun searchPonenteById(@PathVariable id: Long): ResponseEntity<SpeakerResponse> {
        val ponente: Ponente = ponenteService.getPonenteById(id)

        val speakerResponse = SpeakerResponse(
            id = ponente.id!!,
            nombres = ponente.nombres,
            apellidos = ponente.apellidos,
            gradoAcademico = DataResponseGradoAcademico(
                id = ponente.gradoAcademico.id!!,
                descripcion = ponente.gradoAcademico.descripcion
            ),
            congreso = DataResultCongreso(
                id = ponente.congreso.id!!,
                nombre = ponente.congreso.nombre,
                codigo = ponente.congreso.codigo
            )
        )

        return ResponseEntity.ok(speakerResponse)
    }

    // Endpoint para listar todos los ponentes
    @GetMapping("/ponentes")
    fun listAllPonentes(): ResponseEntity<List<SpeakerResponse>> {
        val ponentes: List<Ponente> = ponenteService.getAllPonentes()

        if (ponentes.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponsePonentes = ponentes.map { ponente ->
            SpeakerResponse(
                id = ponente.id!!,
                nombres = ponente.nombres,
                apellidos = ponente.apellidos,
                gradoAcademico = DataResponseGradoAcademico(
                    id = ponente.gradoAcademico.id!!,
                    descripcion = ponente.gradoAcademico.descripcion
                ),
                congreso = DataResultCongreso(
                    id = ponente.congreso.id!!,
                    nombre = ponente.congreso.nombre,
                    codigo = ponente.congreso.codigo
                )
            )
        }

        return ResponseEntity.ok(dataResponsePonentes)
    }

    // Endpoint para eliminar un ponente por su id
    @DeleteMapping("/ponente/{id}")
    fun deletePonenteById(@PathVariable id: Long): ResponseEntity<Void> {
        ponenteService.deletePonente(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para actualizar un ponente por su id
    @PutMapping("/ponente/{id}")
    fun updatePonenteById(@PathVariable id: Long, @RequestBody @Valid updateDataPonente: UpdateDataPonente): ResponseEntity<Void> {
        ponenteService.updatePonente(id, updateDataPonente)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD PONENCIAS ********/
    // Endpoint para crear una nueva ponencia
    @PostMapping("/create-ponencia")
    fun createPonencia(@RequestBody @Valid dataRequestPonencia: DataRequestPonencia, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<DataResponsePonencia> {
        // Crear la ponencia utilizando el servicio
        val ponencia: Ponencia = ponenciaService.createPonencia(dataRequestPonencia)

        // Mapear la entidad Ponencia a DataResponsePonencia
        val dataResponsePonencia = DataResponsePonencia(
            id = ponencia.id!!,
            nombre = ponencia.nombre,
            estado = ponencia.estado,
            ponente = DataResultPonente(
                id = ponencia.ponente.id!!,
                nombres = ponencia.ponente.nombres,
                apellidos = ponencia.ponente.apellidos,
            ),
            congreso = DataResultCongreso(
                id = ponencia.congreso.id!!,
                nombre = ponencia.congreso.nombre,
                codigo = ponencia.congreso.codigo
            )
        )

        // Construir la URI del recurso creado
        val uri = uriComponentsBuilder.path("/api/ponencias/{id}")
            .buildAndExpand(ponencia.id).toUri()

        // Retornar la respuesta con el código 201 Created y el cuerpo de la ponencia creada
        return ResponseEntity.created(uri).body(dataResponsePonencia)
    }

    // Endpoint para buscar una ponencia por su id
    @GetMapping("/ponencia/{id}")
    fun searchPonenciasById(@PathVariable id: Long): ResponseEntity<DataResponsePonencia> {
        val ponencia: Ponencia = ponenciaService.getPonenciaById(id)

        if (!ponencia.estado)
            return ResponseEntity.noContent().build()

        val dataResponsePonencia = DataResponsePonencia(
            id = ponencia.id!!,
            nombre = ponencia.nombre,
            estado = ponencia.estado,
            ponente = DataResultPonente(
                id = ponencia.ponente.id!!,
                nombres = ponencia.ponente.nombres,
                apellidos = ponencia.ponente.apellidos,
            ),
            congreso = DataResultCongreso(
                id = ponencia.congreso.id!!,
                nombre = ponencia.congreso.nombre,
                codigo = ponencia.congreso.codigo
            )
        )

        return ResponseEntity.ok(dataResponsePonencia)
    }

    // Endpoint para obtener todas las ponencias activas
    @GetMapping("/ponencias")
    fun getAllPonencias(): ResponseEntity<List<DataResponsePonencia>> {
        val ponencias: List<Ponencia> = ponenciaService.getAllPonencias()

        if (ponencias.isEmpty())
            return ResponseEntity.noContent().build()

        val dataResponsePonencias = ponencias.map { ponencia ->
            DataResponsePonencia(
                id = ponencia.id!!,
                nombre = ponencia.nombre,
                estado = ponencia.estado,
                ponente = DataResultPonente(
                    id = ponencia.ponente.id!!,
                    nombres = ponencia.ponente.nombres,
                    apellidos = ponencia.ponente.apellidos,
                ),
                congreso = DataResultCongreso(
                    id = ponencia.congreso.id!!,
                    nombre = ponencia.congreso.nombre,
                    codigo = ponencia.congreso.codigo
                )
            )
        }

        return ResponseEntity.ok(dataResponsePonencias)
    }

    // Endpoint para buscar una ponencia por su nombre
    @GetMapping("/ponencia/search/nombre")
    fun getPonenciaByNombre(@RequestParam nombre: String): ResponseEntity<DataResponsePonencia> {
        val ponencia: Ponencia = ponenciaService.getPonenciaByNombre(nombre)

        val dataResponsePonencia = DataResponsePonencia(
            id = ponencia.id!!,
            nombre = ponencia.nombre,
            estado = ponencia.estado,
            ponente = DataResultPonente(
                id = ponencia.ponente.id!!,
                nombres = ponencia.ponente.nombres,
                apellidos = ponencia.ponente.apellidos,
            ),
            congreso = DataResultCongreso(
                id = ponencia.congreso.id!!,
                nombre = ponencia.congreso.nombre,
                codigo = ponencia.congreso.codigo
            )
        )

        return ResponseEntity.ok(dataResponsePonencia)
    }

    // Endpoint para desactivar una ponencia por su ID
    @DeleteMapping("/ponencia/deactivate/{id}")
    fun deactivatePonencia(@PathVariable id: Long): ResponseEntity<Void> {
        val ponencia: Ponencia = ponenciaService.deactivatePonencia(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para activar una ponencia por su ID
    @PutMapping("/ponencia/activate/{id}")
    fun activatePonencia(@PathVariable id: Long): ResponseEntity<DataResponsePonencia> {
        ponenciaService.activatePonencia(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para actualizar una ponencia por su ID
    @PutMapping("/ponencia/{id}")
    fun updatePonencia(@PathVariable id: Long, @RequestBody @Valid updateDataPonencia: UpdateDataPonencia): ResponseEntity<Void> {
        // Actualizar la ponencia utilizando el servicio
        ponenciaService.updatePonencia(id, updateDataPonencia)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD BLOQUE ********/
    // Endpoint para crear un nuevo bloque
    @PostMapping("/create-bloque")
    fun createBloque(@RequestBody @Valid dataRequestBloque: DataRequestBloque, uriComponentsBuilder: ServletUriComponentsBuilder): ResponseEntity<DataResponseBloque> {
        // Llamar al servicio para crear el bloque
        val nuevoBloque: Bloque = bloqueService.createBloque(dataRequestBloque)

        // Construir la respuesta con el URI del nuevo recurso
        val location = uriComponentsBuilder.path("/api/congresos/bloques/{id}")
            .buildAndExpand(nuevoBloque.id).toUri()

        // Crear el cuerpo de la respuesta
        val dataResponseBloque = DataResponseBloque(
            id = nuevoBloque.id!!,
            horaInicial = nuevoBloque.horaInicio,
            horaFinal = nuevoBloque.horaFinal,
            dia = DataResultDia(
                id = nuevoBloque.dia.id!!,
                fecha = nuevoBloque.dia.fecha,
                congreso = DataResultCongreso(
                    id = nuevoBloque.dia.congreso.id!!,
                    nombre = nuevoBloque.dia.congreso.nombre,
                    codigo = nuevoBloque.dia.congreso.codigo
                )
            ),
            ubicacion = DataResultUbicacion(
                id = nuevoBloque.ubicacion.id!!,
                nombre = nuevoBloque.ubicacion.nombre,
            ),
            ponencia = DataResultPonencia(
                id = nuevoBloque.ponencia.id!!,
                nombre = nuevoBloque.ponencia.nombre,
                ponente = DataResultPonente(
                    id = nuevoBloque.ponencia.ponente.id!!,
                    nombres = nuevoBloque.ponencia.ponente.nombres,
                    apellidos = nuevoBloque.ponencia.ponente.apellidos
                )
            )
        )
        return ResponseEntity.created(location).body(dataResponseBloque)
    }

    // Endpoint para obtener todos los bloques
    @GetMapping("/bloques")
    fun getAllBloques(): ResponseEntity<List<DataResponseBloque>> {
        val bloques: List<Bloque> = bloqueService.getAllBloques()

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

    // Endpoint para obtener un bloque por su id
    @GetMapping("/bloque/{id}")
    fun searchBloqueById(@PathVariable id: Long): ResponseEntity<DataResponseBloque> {
        val bloque: Bloque = bloqueService.getBloqueById(id)
        val dataResponseBloque = DataResponseBloque(
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
        return ResponseEntity.ok(dataResponseBloque)
    }

    // Endpoint para eliminar un bloque por su id
    @DeleteMapping("/bloque/{id}")
    fun deleteBloqueById(@PathVariable id: Long): ResponseEntity<Void> {
        bloqueService.deleteBloqueById(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para actualizar un bloque por su id
    @PutMapping("/bloque/{id}")
    fun updateBloque(
        @PathVariable id: Long,
        @RequestBody @Valid updateDataBloque: UpdateDataBloque
    ): ResponseEntity<Void> {
        val bloqueActualizado: Bloque = bloqueService.updateBloque(id, updateDataBloque)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD COLABORADOR ********/
    // Endpoint para crear un nuevo colaborador
    @PostMapping("/create-colaborador")
    fun createColaboradorWithCongreso(@RequestBody @Valid registerColaboradorData: RegisterColaboradorData, uriComponentsBuilder: ServletUriComponentsBuilder, @RequestParam codCongreso: String): ResponseEntity<DataResponseColaboradorWithCongreso> {
        // Crear el colaborador
        val colaboradorCongreso: CongresoColaborador = colaboradorService.createColaboradorWithCongreso(registerColaboradorData, codCongreso)

        // Se pasan los datos creados a DataResponseColaborador para visualizarlos
        val dataResponseColaborador = DataResponseColaboradorWithCongreso(
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

        // Crear la URI para el nuevo recurso creado
        val uri = uriComponentsBuilder.path("/api/administrador/colaborador/{id}")
            .buildAndExpand(colaboradorCongreso.id).toUri()

        return ResponseEntity.created(uri).body(dataResponseColaborador)
    }

    // Endpoint para obtener un colaborador por su id
    @GetMapping("/colaborador/{id}")
    fun getColaboradorById(@PathVariable id: Long): ResponseEntity<DataResponseColaborador> {
        val colaborador: Colaborador = colaboradorService.getColaboradorById(id)

        if (!colaborador.estado) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseColaborador: DataResponseColaborador = DataResponseColaborador(
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

    // Endpoint para listar todos los colaboradores
    @GetMapping("/colaboradores")
    fun getAllColaboradores(): ResponseEntity<List<DataResponseColaborador>> {
        val colaboradores: List<Colaborador> = colaboradorService.getAllColaboradores()

        if (colaboradores.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseColaborador: List<DataResponseColaborador> = colaboradores.map { colaborador ->
            DataResponseColaborador(
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
        }

        return ResponseEntity.ok(dataResponseColaborador)
    }

    // Endpoint para buscar un colaborador por sus names
    @GetMapping("/colaborador/search/nombres")
    fun searchColaboradoresByNombres(@RequestParam nombres: String): ResponseEntity<List<ColaboradorBusquedaDTO>> {
        // Buscar colaboradores por names
        val colaboradores: List<ColaboradorBusquedaDTO> = colaboradorService.searchByNombres(nombres)

        return if (colaboradores.isEmpty()) {
            ResponseEntity.noContent().build() // 204 No Content
        } else {
            ResponseEntity.ok(colaboradores) // 200 OK con la lista de colaboradores
        }
    }

    // Endpoint para buscar un colaborador por sus apellidos
    @GetMapping("/colaborador/search/apellidos")
    fun searchColaboradoresByApellidos(
        @RequestParam(required = false) aPaterno: String?,
        @RequestParam(required = false) aMaterno: String?
    ): ResponseEntity<List<ColaboradorBusquedaDTO>> {
        // Buscar colaboradores por apellidos
        val colaboradores: List<ColaboradorBusquedaDTO> = colaboradorService.searchByApellidos(aPaterno, aMaterno)

        return if (colaboradores.isEmpty()) {
            ResponseEntity.noContent().build() // 204 No Content
        } else {
            ResponseEntity.ok(colaboradores) // 200 OK con la lista de colaboradores
        }
    }

    // Endpoint para desactivar un colaborador por su ID
    @DeleteMapping("/colaborador/deactivate/{id}")
    fun deleteColaboradorById(@PathVariable id: Long): ResponseEntity<Void> {
        colaboradorService.deactivateColaboradorById(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para activar un colaborador por su ID
    @PutMapping("/colaborador/activate/{id}")
    fun activateColaboradorById(@PathVariable id: Long): ResponseEntity<Void> {
        colaboradorService.activateColaboradorById(id)
        return ResponseEntity.noContent().build()
    }

    // Endpoint para editar un colaborador
    @PutMapping("/colaborador/{id}")
    fun editColaborador(@PathVariable id: Long, @RequestBody @Valid updatedColaboradorData: UpdateDataColaborador): ResponseEntity<Void> {
        colaboradorService.editColaborador(id, updatedColaboradorData)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD PARTICIPANTE ********/
    // Endpoint para importar estudiantes desde excel
    @PostMapping("/importar-participantes")
    fun importarParticipantesDesdeExcel(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("adminId") adminId: Long
    ): ResponseEntity<Map<String, Any>> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Debe subir un archivo Excel válido."))
        }
        val resultado = participanteService.registrarParticipantesDesdeExcel(file, adminId)
        return ResponseEntity.ok(resultado)
    }
    // Endpoint para obtener todos los participantes
    @GetMapping("/participantes")
    fun getAllParticipantes(): ResponseEntity<List<ParticipantResponse>> {
        val participantes = participanteService.getAllParticipantes()

        if (participantes.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val participantResponseParticipantes = participantes.map { participante ->
            ParticipantResponse(
                id = participante.id!!,
                nombres = participante.nombres,
                apPaterno = participante.apPaterno,
                apMaterno = participante.apMaterno,
                estado = participante.estado,
                numDocumento = participante.numDocumento,
                escuelaProfesional = participante.escuelaProfesional.let {
                    DataResponseEscuelaProfesional(
                        id = it.id!!,
                        nombre = it.nombre,
                        codigo = it.codigo
                    )
                },
                tipoParticipante = participante.tipoParticipante.let {
                    ParticipantTypeResponse(
                        id = it.id!!,
                        descripcion = it.descripcion
                    )
                },
                congreso = participante.congreso.let {
                    DataResultCongreso(
                        id = it.id!!,
                        nombre = it.nombre,
                        codigo = it.codigo
                    )
                },
                qrCode = participante.qr_code
            )
        }

        return ResponseEntity.ok(participantResponseParticipantes)
    }

    // Endpoint para buscar participantes por apellidos
    @GetMapping("/participante/search/apellidos")
    fun searchParticipanteByApellidos(
        @RequestParam(required = false) apPaterno: String?,
        @RequestParam(required = false) apMaterno: String?
    ): ResponseEntity<List<ParticipanteBusquedaDTO>> {
        val participantes = participanteService.searchByApellidos(apPaterno, apMaterno)

        if (participantes.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val dataResponseParticipantes: List<ParticipanteBusquedaDTO> = participantes.map { participante ->
            ParticipanteBusquedaDTO(
                nombres = participante.nombres,
                apPaterno = participante.apPaterno,
                apMaterno = participante.apMaterno,
                estado = participante.estado,
                numDocumento = participante.numDocumento,
            )
        }
        return ResponseEntity.ok(dataResponseParticipantes)
    }

    // Endpoint para buscar participante por numero de documento
    @GetMapping("/participante/search/documento")
    fun searchParticipanteByNumDocumento(@RequestParam numDocumento: String): ResponseEntity<ParticipanteBusquedaDTO> {
        // Buscar el participante por número de documento
        val participante: Participante = participanteService.searchByNumDocumento(numDocumento)

        // Mapear a ParticipanteBusquedaDTO
        val dataResponseParticipante: ParticipanteBusquedaDTO = ParticipanteBusquedaDTO(
            nombres = participante.nombres,
            apPaterno = participante.apPaterno,
            apMaterno = participante.apMaterno,
            estado = participante.estado,
            numDocumento = participante.numDocumento,
        )

        return ResponseEntity.ok(dataResponseParticipante)
    }

    // Endpoint para buscar participantes por names
    @GetMapping("/participante/search/nombres")
    fun searchParticipanteByNombres(@RequestParam nombres: String): ResponseEntity<List<ParticipanteBusquedaDTO>> {
        // Buscar participantes por names
        val participantes: List<ParticipanteBusquedaDTO> = participanteService.searchByNombres(nombres)

        if (participantes.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        // Mapear a lista de ParticipanteBusquedaDTO
        val dataResponseParticipantes: List<ParticipanteBusquedaDTO> = participantes.map { participante ->
            ParticipanteBusquedaDTO(
                nombres = participante.nombres,
                apPaterno = participante.apPaterno,
                apMaterno = participante.apMaterno,
                estado = participante.estado,
                numDocumento = participante.numDocumento,
            )
        }
        return ResponseEntity.ok(dataResponseParticipantes)
    }

    // Endpoint para cambiar el estado de un participante
    @PutMapping("/participante/change-estado/{id}")
    fun changeEstadoParticiapante(@PathVariable id: Long, @RequestBody @Valid updateDataParticipante: UpdateDataParticipante): ResponseEntity<Void> {
        participanteService.changeStateParticipante(id, updateDataParticipante)
        return ResponseEntity.noContent().build()
    }

    /******** ENDPOINTS PARA LA ENTIDAD ASISTENCIA ********/
    // Endpoint para buscar la asistencia por ID
    @GetMapping("/asistencia/{id}")
    fun searchAsistenciaById(@PathVariable id: Long): ResponseEntity<DataResponseAsistencia> {
        val asistencia = asistenciaService.getAsistenciaById(id)

        // Mapear el resultado a DataResponseAsistencia
        val dataResponseAsistencia = DataResponseAsistencia(
            id = asistencia.id!!,
            fecha = asistencia.fecha,
            hora = asistencia.hora,
            participante = ParticipantResult(
                nombres = asistencia.participante.nombres,
                apPaterno = asistencia.participante.apPaterno,
                apMaterno = asistencia.participante.apMaterno,
                numDocumento = asistencia.participante.numDocumento
            ),
            bloque = DataResultBloque(
                id = asistencia.bloque.id!!,
                horaInicial = asistencia.bloque.horaInicio,
                horaFinal = asistencia.bloque.horaFinal,
                dia = DataResultDiaAsistencia(
                    id = asistencia.bloque.dia.id!!,
                    fecha = asistencia.bloque.dia.fecha
                ),
                ubicacion = DataResultUbicacion(
                    id = asistencia.bloque.ubicacion.id!!,
                    nombre = asistencia.bloque.ubicacion.nombre
                ),
                ponencia = DataResultPonencia(
                    id = asistencia.bloque.ponencia.id!!,
                    nombre = asistencia.bloque.ponencia.nombre,
                    ponente = DataResultPonente(
                        id = asistencia.bloque.ponencia.ponente.id!!,
                        nombres = asistencia.bloque.ponencia.ponente.nombres,
                        apellidos = asistencia.bloque.ponencia.ponente.apellidos
                    )
                )
            ),
            congreso = DataResultCongreso(
                id = asistencia.congreso.id!!,
                nombre = asistencia.congreso.nombre,
                codigo = asistencia.congreso.codigo
            )
        )

        return ResponseEntity.ok(dataResponseAsistencia)
    }
}