package com.hb.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import model.FollowsParams
import org.koin.ktor.ext.inject
import repository.FollowsRepository
import util.Result

fun Routing.followsRoute() {
    val repository by inject<FollowsRepository>()

    authenticate {
        route("/follows") {
            post {
                val params = call.receiveNullable<FollowsParams>()
                if (params == null) {
//                    call.respond<Result<Any>>(
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Result.Error<Any>(message = "Bad Request")
                    )
                    return@post
                }
                val result = if (params.isFollowing) {
                    repository.followUser(follower = params.follower, following = params.following)
                } else {
                    repository.unfollowUser(follower = params.follower, following = params.following)
                }
                call.respond<Result<Any>>(result)
            }
        }
    }
}
