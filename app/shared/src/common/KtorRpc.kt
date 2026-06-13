package common

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json

internal expect val BASE_IP: String

internal abstract class KtorRpc : AutoCloseable {

    val rpcClient = HttpClient { installKrpc() }.rpc {
        url("ws://${BASE_IP}/rpc")

        rpcConfig {
            serialization {
                json()
            }
        }
    }

    override fun close() {
        rpcClient.close()
    }
}