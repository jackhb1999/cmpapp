package util

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.UUID
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ResultTest {
    private fun getResult(): Result<String> {
        return if (true) {
            Result.Error(message = "error")
        } else {
            Result.Success(message = "aa", data = "aa")
        }
    }

    @Test
    fun jsonTest() {
        val error: Result<String> = getResult()
        val string = Json.encodeToString(error)
        println(string)

        // 反序列化回对象
        val obj = Json.decodeFromString<Result<String>>(string)
        if (obj.isSuccess) {
            println(obj)
        }else{
            print(obj)
            println()
        }
    }


    @Test
    fun jsonFooTest() {
        val barInt = Foo.BarInt(1, 2)
        var string = Json.encodeToString(barInt)
        println(string)


        // 反序列化回对象
        var obj = Json.decodeFromString<Foo.BarInt>(string)
        println(obj)

        val module = SerializersModule {
            polymorphic(Foo::class) {
                subclass(Foo.BarInt.serializer())
            }
        }


        val format = Json { serializersModule = module }

        string = format.encodeToString(barInt)
        println(string)


        // 反序列化回对象
        var obj2 = format.decodeFromString<Foo<Int>>(string)
        println(obj2)
    }


    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun jsonProjectTest() {
        println(Json.encodeToString(Project.OwnedProject("kotlinx.coroutines", "kotlin")))
        // prints: {"name":"kotlinx.coroutines","owner":"kotlin"}
        val project: Project = Project.OwnedProject("kotlinx.coroutines", "kotlin")
        // prints: {"type":"com.example.Project.OwnedProject","name":"kotlinx.coroutines","owner":"kotlin"}
        println(Json.encodeToString(project))

    }
}