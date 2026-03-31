package com.hb.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.rpc.krpc.ktor.server.Krpc

fun Application.configureKrpc() {
    install(Krpc)
}