package com.hb.route

import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.koin.ktor.ext.inject
import service.AwesomeService
import service.UserService

fun Routing.rpaRoutes() {

    val awesomeService by inject<AwesomeService>()
    val userService by inject<UserService>()

    rpc("/rpc") {
        rpcConfig {
            serialization { json() }
        }
        registerService<AwesomeService>({ awesomeService })
        registerService<UserService>({ userService })
    }

}