package util

import io.ktor.http.HttpStatusCode

sealed class Result<T>(
    val code: HttpStatusCode = HttpStatusCode.OK,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(HttpStatusCode.OK, data)
    class Error<T>(
        code: HttpStatusCode = HttpStatusCode.ServiceUnavailable,
        data: T? = null,
        message: String? = null
    ) : Result<T>(code, data, message)
}