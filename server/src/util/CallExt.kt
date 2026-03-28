package com.hb.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.jetbrains.exposed.v1.core.QueryParameter
import util.Result

suspend fun ApplicationCall.getParameter(name: String, isQueryParameter: Boolean = false): String {
    val parameter = (if (isQueryParameter) {
        request.queryParameters[name]
    } else {
        parameters[name]
    }).takeIf { !it.isNullOrEmpty() } ?: run {
        respond(
            status = HttpStatusCode.BadRequest,
            message = Result.Error<Any>(message = "Parameter '${name}' must not be blank")
        )
        ""
    }

    return parameter
}

suspend fun ApplicationCall.getIntParameter(name: String, isQueryParameter: Boolean = false, defaultVal: Int? = null): Int {
    val parameter = (if (isQueryParameter) {
        request.queryParameters[name]?.toIntOrNull()
    } else {
        parameters[name]?.toIntOrNull()
    }) ?: run {
        if (defaultVal == null) {
            respond(
                status = HttpStatusCode.BadRequest,
                message = Result.Error<Any>(message = "Parameter '${name}' must not be blank")
            )
        }
        defaultVal!!
    }

    return parameter
}