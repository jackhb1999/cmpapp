package com.hb.util

import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import io.ktor.utils.io.InternalAPI
import io.ktor.utils.io.readBuffer
import kotlinx.io.readByteArray
import java.io.File
import java.util.UUID

@OptIn(InternalAPI::class)
suspend fun PartData.FileItem.saveFile(folderPath: String): String {
    val fileName = "${UUID.randomUUID()}.${File(originalFileName as String).extension}"

//    val fileBytes = streamProvider().readBytes()
    val fileBytes = provider().readBuffer().readByteArray()

    val folder = File(folderPath)
    folder.mkdirs()
    File("${folderPath}/$fileName").writeBytes(fileBytes)
    return fileName
}