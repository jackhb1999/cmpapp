package util

import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlin.reflect.KClass


@Serializable
sealed class Result<T : @Serializable Any>(
    open val code: Int = HttpStatusCode.OK.value,
    open val data: T? = null,
    open val message: String? = null
) {
    abstract val isSuccess: Boolean


    class Success<T : @Serializable Any>(
        data: T? = null,
        message: String? = null
    ) : Result<T>(data = data, message = message) {
        override val isSuccess: Boolean
            get() = true

        companion object {
            fun <T:@Serializable Any> serializer(type: KSerializer<T>)= Result.serializer(type)
        }
    }

    class Error<T : @Serializable Any>(
        code: Int = HttpStatusCode.ServiceUnavailable.value,
        data: T? = null,
        message: String? = null
    ) : Result<T>(code = code, data = data, message = message) {
        override val isSuccess: Boolean
            get() = false

        companion object {
            fun <T:@Serializable Any> serializer(type: KSerializer<T>)= Result.serializer(type)
        }
    }
}


@Serializable
sealed class Foo<T> {
    abstract val x: T
    abstract val y: T

    @Serializable
    data class BarInt(
        override val x: Int,
        override val y: Int
    ) : Foo<Int>()

    @Serializable
    data class BarFloat(
        override val x: Float,
        override val y: Float
    ) : Foo<Float>()
}


@Serializable
sealed class Project {
    abstract val name: String

    @Serializable
    class OwnedProject(override val name: String, val owner: String) : Project()
}