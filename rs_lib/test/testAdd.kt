import uniffi.rslib.add
import kotlin.test.Test
import kotlin.test.assertEquals

class TestAdd {
    @Test
    fun doTest() {
        val add = add(1u, 2u)
        println(add)
        println("Hello World!")
    }
}