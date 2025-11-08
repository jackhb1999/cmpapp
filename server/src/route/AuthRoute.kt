package com.hb.route

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val repository by inject<UserRepository>()

    route(path = "signup") {
        post {
            val params = call.receiveNullable<SignUpParams>()
            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Params is null"
                    )
                )
                return@post
            }
            val result = repository.signUp(params = params)
            println(result.toString())
            call.respond<AuthResponse>(
                status = result.code,
                message = result.data!!
            )
        }
    }

    route(path = "login") {
        post {
            val params = call.receiveNullable<SignInParams>()
            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Params is null"
                    )
                )
                return@post
            }
            val result = repository.signIn(params = params)
            call.respond(
                status = result.code,
                message = result.data!!
            )
        }
    }
}