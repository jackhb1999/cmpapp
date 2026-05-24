package util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlin.Result

internal suspend fun <T> safeApiCall(
    dispatcher: DispatcherProvider,
    errorHandler: (Throwable) -> Result<T> = ::defaultErrorHandler,
    apiCall: suspend () -> Result<T>
): Result<T> = withContext(dispatcher.io) {
    try {
        apiCall()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        errorHandler(e)
    }
}

private fun <T> defaultErrorHandler(throwable: Throwable): Result<T> {
    return if (throwable is IOException) {
        Result.failure(throwable)
    } else {
        Result.failure(throwable)
    }

}