package com.hb.route

import com.hb.service.AwesomeServiceImpl
import io.ktor.server.routing.Routing
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import service.AwesomeService

fun Routing.rpaRoutes() {
    rpc("/apc") {
        rpcConfig {
            serialization { json() }
        }
        registerService<AwesomeService> {
            AwesomeServiceImpl()
        }
    }

}