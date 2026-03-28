package util

import diglol.id.Id
import diglol.id.Id.Companion.decodeToId
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64


object IdGenerator {
    fun generateId(): String {
        val generate = Id.generate()
        return generate.encodeToString()
    }
}