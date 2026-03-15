package util

import diglol.id.Id


object IdGenerator {
    fun generateId(): ByteArray {
        val generate = Id.generate()
        return generate.bytes
    }
}