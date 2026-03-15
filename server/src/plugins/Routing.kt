package com.hb.plugins

import com.hb.route.authRouting
import com.hb.route.followsRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRouting()
        followsRoute()
    }
}
