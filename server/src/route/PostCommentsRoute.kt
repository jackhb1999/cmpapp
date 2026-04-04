package com.hb.route

import com.hb.util.getIntParameter
import com.hb.util.getParameter
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.NewCommentParams
import org.koin.ktor.ext.inject
import repository.PostCommentsRepository
import util.Result

fun Routing.postCommentsRoute() {

    val repository: PostCommentsRepository by inject<PostCommentsRepository>()

    authenticate {
        route("/post/comments") {
            post(path = "/create") {
                val params = call.receiveNullable<NewCommentParams>()
                if (params == null) {
                    call.respond<Result<Any>>(
                        status = HttpStatusCode.BadRequest,
                        message = Result.Error(message = "Parameter can't be null")
                    )
                    return@post
                }
                val result = repository.addComment(params)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result)
            }

            delete(path = "/delete") {
                val userId = call.getParameter(name = "userId", isQueryParameter = true)
                val postId = call.getParameter(name = "postId", isQueryParameter = true)
                val commentId = call.getParameter(name = "commentId", isQueryParameter = true)
                val result = repository.removeComment(commentId, postId, userId)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result)
            }

            get(path = "/{postId}") {
                val postId = call.getParameter(name = "postId")
                val page = call.getIntParameter(name = "page", isQueryParameter = true, defaultVal = 0)
                val limit = call.getIntParameter(name = "limit", isQueryParameter = true, defaultVal = 10)

                val result =  repository.getPostComments(postId,page,limit)
                call.respond(status = HttpStatusCode.fromValue(result.code), message = result)
            }
        }
    }
}