package com.ucsm.conecta.ucsmconecta.dto.users.profile.colaborador

import com.ucsm.conecta.ucsmconecta.dto.university.congresos.DataResultCongreso

data class DataResponseColaboradorWithCongreso(
    val colaborador: DataResultColaborador,
    val congreso: DataResultCongreso
)
