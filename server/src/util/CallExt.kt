package com.hb.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import util.ActionResult
import util.send
import kotlin.Unit

suspend fun ApplicationCall.getParameter(name: String, isQueryParameter: Boolean = false): String {
    val parameter = (if (isQueryParameter) {
        request.queryParameters[name]
    } else {
        parameters[name]
    }).takeIf { !it.isNullOrEmpty() } ?: run {
        respond(
            status = HttpStatusCode.BadRequest,
//            message = Result.Error<Any>(message = "Parameter '${name}' must not be blank")
            message = ActionResult<Unit>(isSuccess = false, message = "Parameter '${name}' must not be blank")
        )
        ""
    }

    return parameter
}

suspend fun ApplicationCall.getIntParameter(
    name: String,
    isQueryParameter: Boolean = false,
    defaultVal: Int? = null
): Int {
    val parameter = (if (isQueryParameter) {
        request.queryParameters[name]?.toIntOrNull()
    } else {
        parameters[name]?.toIntOrNull()
    }) ?: run {
        if (defaultVal == null) {
            respond(
                status = HttpStatusCode.BadRequest,
//                message = Result.Error<Any>(message = "Parameter '${name}' must not be blank")
                message = ActionResult<Unit>(isSuccess = false, message = "Parameter '${name}' must not be blank")
            )
        }
        defaultVal!!
    }

    return parameter
}

// 避免类型擦除
suspend inline fun <reified T : @Serializable Any> ApplicationCall.sendResult(
    result: Result<T>,
    status: HttpStatusCode? = null,
    message: String? = null
) {
    val responseStatus =
        if (result.isSuccess) status ?: HttpStatusCode.OK else status ?: HttpStatusCode.InternalServerError
    val actionResult = if (message == null) result.send() else result.send(message)
    respond(responseStatus, actionResult)
}
