package com.ucsm.conecta.ucsmconecta.services.universidad.congresos.valoracion

import com.ucsm.conecta.ucsmconecta.domain.university.congresos.valoracion.Comentario
import com.ucsm.conecta.ucsmconecta.domain.users.participant.Participante
import com.ucsm.conecta.ucsmconecta.dto.university.congresos.valoracion.comentarios.DataRequestComentario
import com.ucsm.conecta.ucsmconecta.repository.universidad.congresos.valoracion.ComentarioRepository
import com.ucsm.conecta.ucsmconecta.services.users.ParticipanteService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.util.Locale.getDefault

@Service
class ComentarioService @Autowired constructor(
    private val comentarioRepository: ComentarioRepository,
    private val participanteService: ParticipanteService
) {
    // Mètodo para obtener todos los comentarios
    fun getALlComentarios(): List<Comentario> = comentarioRepository.findAllByEstadoTrueOrderByIdAsc()

    // Metodo para guardar un comentario
    @Transactional
    fun createComentario(@RequestBody @Valid dataRequestComentario: DataRequestComentario): Comentario {
        // Buscar participante por id
        val participante: Participante = participanteService.searchByNumDocumento(dataRequestComentario.documentoParticipante)

        // Verificar las groserias del comentario
        val comentarioPermitido: Boolean = verificationGroserias(dataRequestComentario.texto)

        /* Bloqueo permanente del comentario por groserias
        if (!comentarioPermitido) {
            throw IllegalArgumentException("El comentario contiene lenguaje inapropiado")
        }*/


        return comentarioRepository.save(Comentario(
            texto = dataRequestComentario.texto,
            estado = comentarioPermitido,
            participante = participante
        ))
    }

    // Metodo para verificar las groserias
    private fun verificationGroserias(texto: String): Boolean {
        val palabrasProhibidas = listOf(
            "puto", "putos", "puta", "putas", "gay",
            "perras", "perra", "imbecil", "imbeciles",
            "concha tu mare", "ctm"
        )

        val textoLimpio = texto.lowercase(getDefault()).trim()

        // Si contiene alguna palabra prohibida → retorna false
        return palabrasProhibidas.none { palabra ->
            textoLimpio.contains(palabra)
        }
    }
}