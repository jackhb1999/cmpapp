package service

import model.AuthResponse
import model.SignInParams
import model.SignUpParams
import common.KtorApi
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import util.ActionResult

internal class AuthService : KtorApi() {
    suspend fun signUp(request: SignUpParams): ActionResult<AuthResponse> = client.post {
        endPoint(path = "signup")
        setBody(request)
        contentType(ContentType.Application.Json) // 告诉服务器：我发送的是JSON
        accept(ContentType.Application.Json)
    }.body()

    suspend fun signIn(request: SignInParams): ActionResult<AuthResponse> = client.post {
        endPoint(path = "login")
        setBody(request)
    }.body()

}
