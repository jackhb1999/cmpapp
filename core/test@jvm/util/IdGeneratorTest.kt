package util

import kotlin.test.Test


class IdGeneratorTest {
    @Test
    fun generateIdTest() {
        val generateId = IdGenerator.generateId()
        println("10$generateId")
    }
}