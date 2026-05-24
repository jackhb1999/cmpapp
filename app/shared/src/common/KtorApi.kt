package common

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import util.ActionResult

//private const val BASE_URL = "http://192.168.1.5:8088"
//private const val BASE_URL = "http://192.168.32.28:8088"
//private const val BASE_URL = "http://192.168.1.190:8088"
private const val BASE_URL = "http://127.0.0.1:8088"

internal abstract class KtorApi : AutoCloseable {
    val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
        }
    }

    fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    fun HttpRequestBuilder.setToken(token: String) {
        headers {
//            append(HttpHeaders.Authorization,token)
            append("Authorization", "Bearer $token")
        }
    }

    // 可能会类型擦除
    suspend inline fun <reified T : Any> HttpResponse.getBody(): ActionResult<T> {
        return if (this.status == HttpStatusCode.OK) {
            this.body()
        } else {
            ActionResult(isSuccess = false, message = this.body())
        }
    }

    override fun close() {
        client.close()
    }


}
