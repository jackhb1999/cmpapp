package util

import kotlinx.serialization.Serializable
import kotlin.Result

@Serializable
class ActionResult<T : @Serializable Any>(
    val isSuccess: Boolean = true,
    val data: T? = null,
    val message: String? = null
) {
    fun toResult(): Result<T> {
        return when (this.isSuccess) {
            true -> kotlin.Result.success(data!!)
            false -> kotlin.Result.failure(Throwable(this.message))
        }
    }
}

