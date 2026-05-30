package com.hb.plugins


import com.hb.route.rpaRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/ping") {
            call.respondText("pong")
        }
        rpaRoutes()
        staticResources(
            remotePath = "/resources",
            basePackage = "static"
        )
        staticResources("/resources", "static")
    }
}
