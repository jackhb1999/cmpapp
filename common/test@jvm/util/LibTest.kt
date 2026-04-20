package util

import kotlinx.serialization.json.Json
import uniffi.rslib.add
import kotlin.test.Test

class LibTest {


    @Test
    fun jsonTest() {
        val a = add(lhs = 1u, rhs = 2u)
        println(a)
    }

}