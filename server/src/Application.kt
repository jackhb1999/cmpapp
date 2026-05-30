package com.hb

import com.hb.dao.DatabaseFactory
import com.hb.di.configureDI
import com.hb.plugins.configureKrpc
import com.hb.plugins.configureRouting
import com.hb.plugins.configureSerialization
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureDI()
    configureKrpc()
    configureRouting()
}
