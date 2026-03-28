package com.hb.route

import com.hb.util.Constants
import com.hb.util.getIntParameter
import com.hb.util.getParameter
import com.hb.util.saveFile
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import model.Post
import model.PostTextParams
import org.koin.ktor.ext.inject
import repository.PostRepository
import util.Result
import java.io.File

fun Routing.postRoutes() {
    val postRepository by inject<PostRepository>()

    authenticate {
        route(path = "/post") {
            post(path = "/create") {
                var fileName = ""
                var postTextParams: PostTextParams? = null
                val multiPartData = call.receiveMultipart()
                multiPartData.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            fileName = part.saveFile(folderPath = Constants.POST_IMAGES_FOLDER_PATH)
                        }

                        is PartData.FormItem -> {
                            if (part.name == "post_data") {
                                postTextParams = Json.decodeFromString(part.value)
                            }
                        }

                        else -> {}
                    }
                    part.dispose()
                }
                val imageUrl = "${Constants.BASE_URL}/${Constants.POST_IMAGES_FOLDER}/${fileName}"
                if (postTextParams == null) {
                    File("${Constants.POST_IMAGES_FOLDER_PATH}/$fileName").delete()
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Result.Error<Any>(message = "Bad Request")
                    )
                } else {
                    val result = postRepository.createPost(imageUrl = imageUrl, postTextParams = postTextParams!!)
                    call.respond<Result<Any>>(
                        status = result.code,
                        message = result
                    )
                }
            }

            get("/{postId}") {
                try {
                    val postId = call.getParameter(name = "postId")
                    val currentUserId = call.getParameter(name = "currentUserId", isQueryParameter = true)
                    val result = postRepository.getPost(postId, currentUserId)
                    call.respond<Result<Post>>(result)
                } catch (badRequestException: BadRequestException) {
                    return@get
                } catch (anyError: Throwable) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error<Any>(message = "An unexpected error occurred")
                    )
                }

            }

            delete("/{postId}") {
                try {
                    val postId = call.getParameter(name = "postId")
                    val result = postRepository.deletePost(postId)
                    call.respond<Result<Any>>(result)
                } catch (badRequestException: BadRequestException) {
                    return@delete
                } catch (anyError: Throwable) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error<Any>(message = "An unexpected error occurred")
                    )
                }
            }
        }

        route("/posts") {
            get(path = "/feed") {
                try {
                    val currentUserId = call.getParameter(name = "currentUserId", isQueryParameter = true)
                    val page = call.getIntParameter(name = "page", isQueryParameter = true, defaultVal = 0)
                    val limit = call.getIntParameter(name = "limit", isQueryParameter = true, defaultVal = 10)
                    val result =
                        postRepository.getFeedPosts(userId = currentUserId, pageNumber = page, pageSize = limit)
                    call.respond<Result<List<Post>>>(result)
                } catch (badRequestException: BadRequestException) {
                    return@get
                } catch (anyError: Throwable) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error<Any>(message = "An unexpected error occurred")
                    )
                }
            }

            get(path = "/{userId}") {
                try {
                    val userId = call.getParameter(name = "userId")
                    val currentUserId = call.getParameter(name = "currentUserId", isQueryParameter = true)
                    val page = call.getIntParameter(name = "page", isQueryParameter = true, defaultVal = 0)
                    val limit = call.getIntParameter(name = "limit", isQueryParameter = true, defaultVal = 10)
                    val result =
                        postRepository.getPostsByUser(
                            postsOwnerId = userId,
                            currentUserId = currentUserId,
                            pageNumber = page,
                            pageSize = limit
                        )
                    call.respond<Result<List<Post>>>(result)
                } catch (badRequestException: BadRequestException) {
                    return@get
                } catch (anyError: Throwable) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error<Any>(message = "An unexpected error occurred")
                    )
                }
            }
        }
    }

}