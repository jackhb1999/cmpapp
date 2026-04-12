package com.hb.route


import com.hb.util.getIntParameter
import com.hb.util.getParameter
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import model.FollowsParams
import org.koin.ktor.ext.inject
import repository.FollowsRepository
import util.Constants
import util.Result

fun Routing.followsRoute() {
    val repository by inject<FollowsRepository>()

    authenticate {
        route("/follows") {
            post("/follow") {
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
                call.respond<Boolean>(result.data!!)
            }

            get("/followers") {
                val userId = call.getParameter(name = Constants.USER_ID_PARAMETER)
                val page = call.getIntParameter(name = "page", isQueryParameter = true, defaultVal = 0)
                val limit = call.getIntParameter(name = "limit", isQueryParameter = true, defaultVal = 10)

                val result = repository.getFollowers(userId, page, limit)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result)

            }

            get("/following") {
                val userId = call.getParameter(name = Constants.USER_ID_PARAMETER)
                val page = call.getIntParameter(name = "page", isQueryParameter = true, defaultVal = 0)
                val limit = call.getIntParameter(name = "limit", isQueryParameter = true, defaultVal = 10)

                val result = repository.getFollowing(userId, page, limit)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result)
            }

            get("/suggestions") {
                val userId = call.getParameter(name = Constants.USER_ID_PARAMETER)
                val result = repository.getFollowingSuggestions(userId)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result.data!!)
            }
        }
    }
}
