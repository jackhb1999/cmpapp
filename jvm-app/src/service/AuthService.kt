package service

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import common.data.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import util.Result

internal class AuthService : KtorApi() {
    suspend fun signUp(request: SignUpParams): AuthResponse = client.post {
        endPoint(path = "signup")
        setBody(request)
        contentType(ContentType.Application.Json) // 告诉服务器：我发送的是JSON
        accept(ContentType.Application.Json)
    }.body()

    suspend fun signIn(request: SignInParams): AuthResponse = client.post {
        endPoint(path = "signin")
        setBody(request)
    }.body()

}
