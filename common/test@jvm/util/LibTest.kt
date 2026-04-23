package util

import kotlinx.serialization.json.Json
import uniffi.rslib.add
import uniffi.rslib.add2
import kotlin.test.Test

class LibTest {


    @Test
    fun jsonTest() {
        val a = add2(lhs = 1, rhs = 2)
        println(a)
    }

}