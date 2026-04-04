package common

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json

internal abstract class KtorRpc {
    val rpcClient = HttpClient { installKrpc() }.rpc {
        url("ws://localhost:8088/rpc")

        rpcConfig {
            serialization {
                json()
            }
        }
    }


}