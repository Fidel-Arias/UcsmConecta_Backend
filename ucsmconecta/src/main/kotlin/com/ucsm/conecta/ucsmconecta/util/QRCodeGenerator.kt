package com.ucsm.conecta.ucsmconecta.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.util.UUID

object QRCodeGenerator {
    fun generarQR(
        nombres: String,
        numDocumento: String,
    ): String {
        val qrWriter = QRCodeWriter()
        val contenidoQR = "\"{\"names\":\"$nombres\", \"numDocumento\": \"$numDocumento\"}\""

        val hints = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")

        val bitMatrix = qrWriter.encode(contenidoQR, BarcodeFormat.QR_CODE, 300, 300, hints)

        // Carpeta de salida (asegúrate de que exista)
        val carpetaQR = "/var/ucsmconecta/qrcodes"
        File(carpetaQR).mkdirs()

        // Nombre único para el QR
        val nombreArchivo = "QR_${numDocumento}_${UUID.randomUUID()}.png"
        val rutaArchivo: Path = FileSystems.getDefault().getPath(carpetaQR, nombreArchivo)

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", rutaArchivo)

        return "/qrcodes/$nombreArchivo"
    }
}