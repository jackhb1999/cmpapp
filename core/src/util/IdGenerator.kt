package util

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


object IdGenerator {
    @OptIn(ExperimentalUuidApi::class)
    fun generateId(): String {

        val generate =   Uuid.generateV7()
        return generate.toString()
    }
}