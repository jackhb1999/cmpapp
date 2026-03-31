package com.hb.plugins

import com.hb.route.authRouting
import com.hb.route.followsRoute
import com.hb.route.postRoutes
import com.hb.route.profileRoutes
import com.hb.route.rpaRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        rpaRoutes()
        authRouting()
        followsRoute()
        postRoutes()
        profileRoutes()
        staticResources(
            remotePath = "static",
            basePackage = "build/resource/main/static"
        )
    }
}
