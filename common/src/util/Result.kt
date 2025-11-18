package util

import io.ktor.http.HttpStatusCode

sealed class Result<T>(
    val code: HttpStatusCode = HttpStatusCode.Companion.OK,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(HttpStatusCode.Companion.OK, data)
    class Error<T>(
        code: HttpStatusCode = HttpStatusCode.Companion.ServiceUnavailable,
        data: T? = null,
        message: String? = null
    ) : Result<T>(code, data, message)
}