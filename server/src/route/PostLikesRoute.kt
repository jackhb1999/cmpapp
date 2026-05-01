package com.hb.route

import com.hb.util.sendResult
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import model.LikeParams
import org.koin.ktor.ext.inject
import repository.PostLikesRepository
private val logger = KotlinLogging.logger {}
fun Routing.postLikesRouting() {

    val repository by inject<PostLikesRepository>()

    authenticate {
        route("post/likes") {
            post("/add") {
                try {
                    val params = call.receiveNullable<LikeParams>()
                    if (params == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = ""
                        )
                        return@post
                    }
                    val result = repository.addLike(params)
                    call.sendResult(result)
                } catch (error: Throwable) {
                    error.printStackTrace()
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = "请求失败"
                    )
                }
            }

            delete("/remove") {
                try {
                    val params = call.receiveNullable<LikeParams>()
                    if (params == null) {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = ""
                        )
                        return@delete
                    }
                    val result = repository.removeLike(params)
                    call.sendResult(result)
                } catch (error: Throwable) {
                    error.printStackTrace()
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = "请求失败"
                    )
                }
            }



        }
    }

}