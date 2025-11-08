package com.hb

import io.ktor.server.application.*
import com.hb.dao.DatabaseFactory
import com.hb.di.configureDI
import com.hb.plugins.configureRouting
import com.hb.plugins.configureSecurity
import com.hb.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureDI()
    configureSecurity()
    configureRouting()

}
