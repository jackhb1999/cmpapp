package service

import com.hb.model.AuthResponse
import common.data.KtorApi
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import model.LoginParam

internal class ApiService : KtorApi() {

    private var sobToken: String? = null

    suspend fun login(captcha: String, request: LoginParam): String? {
        val response = client.post {
            endPoint(path = "uc/user/login/${captcha}")
            setBody(request)
            contentType(ContentType.Application.Json) // 告诉服务器：我发送的是JSON
            accept(ContentType.Application.Json)
        }
        sobToken = response.headers["sob_token"]
        return sobToken
    }

//    suspend fun signIn(request: SignInParams): AuthResponse = client.post {
//        endPoint(path = "login")
//        setBody(request)
//    }.body()


}
