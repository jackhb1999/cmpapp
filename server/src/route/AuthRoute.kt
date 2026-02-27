package com.hb.route

import com.hb.repository.DeptRepositoryImpl
import model.AuthResponse
import model.SignInParams
import model.SignUpParams
import repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject


fun Routing.authRouting() {
    val repository by inject<UserRepository>()
    val deptRepository by inject<DeptRepositoryImpl>()

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
    route(path = "list") {
        post {
            var a = deptRepository.selectDeptList()
            println(a)
            call.respondText("Hello World!")
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