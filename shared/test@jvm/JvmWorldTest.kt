import uniffi.rslib.add
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmWorldTest {
    @Test
    fun doTest() {
        val add = add(1u, 2u)
        println(add)
        assertEquals("JVM World", getWorld())
    }
}