package com.hb.route

import com.hb.service.AwesomeServiceImpl
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.koin.dsl.koinApplication
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import service.AwesomeService

fun Routing.rpaRoutes() {

    val awesomeService by inject<AwesomeService>()

    rpc("/rpc") {
        rpcConfig {
            serialization { json() }
        }
//        registerService<AwesomeService> {
//            AwesomeServiceImpl(
//                userDao = get()
//            )
//        }
        registerService<AwesomeService>({ awesomeService })
    }

}