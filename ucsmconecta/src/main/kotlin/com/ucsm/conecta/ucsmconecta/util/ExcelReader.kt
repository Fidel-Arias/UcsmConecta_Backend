package com.ucsm.conecta.ucsmconecta.util

import com.ucsm.conecta.ucsmconecta.dto.register.participante.RegisterParticipanteData
import jakarta.validation.Validation
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

object ExcelReader {
    private val validator = Validation.buildDefaultValidatorFactory().validator

    fun leerParticipantesDesdeExcel(file: MultipartFile): List<RegisterParticipanteData> {
        val participantes = mutableListOf<RegisterParticipanteData>()

        val inputStream: InputStream = file.inputStream
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0) // Primera hoja del Excel

        // Validar encabezados mínimos (opcional)
        if (sheet.physicalNumberOfRows <= 1) {
            throw IllegalArgumentException("El archivo Excel está vacío o no contiene datos válidos.")
        }

        // Iterar filas (saltando encabezado)
        for ((index, row) in sheet.drop(1).withIndex()) {
            if (row.getCell(0) == null) continue // Evita filas vacías
            val filaExcel = index + 2 // (+2 porque el índice empieza en 0 y hay encabezado)

            val apPaterno = getCellValue(row.getCell(0))
            val apMaterno = getCellValue(row.getCell(1))
            val nombres = getCellValue(row.getCell(2))
            val numDocumento = getCellValue(row.getCell(3))
            val email = getCellValue(row.getCell(4))
            val tipoParticipante = getCellValue(row.getCell(5))
            val estado = getCellValue(row.getCell(6))

            val participante = RegisterParticipanteData(
                nombres, apPaterno, apMaterno, numDocumento, email, tipoParticipante, estado
            )

            val violations = validator.validate(participante)
            if (violations.isNotEmpty()) {
                val primerError = violations.first().message
                throw IllegalArgumentException("Error en fila $filaExcel: $primerError")
            }

            participantes.add(participante)
        }

        workbook.close()
        inputStream.close()
        return participantes
    }

    fun getCellValue(cell: Cell?): String {
        if (cell == null) return ""
        return when (cell.cellType) {
            CellType.STRING -> cell.stringCellValue.trim()
            CellType.NUMERIC -> cell.numericCellValue.toLong().toString()
            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            else -> cell.toString().trim()
        }
    }
}