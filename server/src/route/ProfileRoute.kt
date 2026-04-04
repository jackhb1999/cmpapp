package com.hb.route

import com.hb.util.Constants
import com.hb.util.getParameter
import com.hb.util.saveFile
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import model.UpdateUserParams
import org.koin.ktor.ext.inject
import repository.ProfileRepository
import util.Result


fun Routing.profileRoutes() {
    val profileRepository by inject<ProfileRepository>()

    authenticate {
        route("/profile") {
            get(path = "/{userId}") {
                try {
                    val profileOwnerId = call.getParameter("userId")
                    val currentUserId = call.getParameter(name = "currentUserId", isQueryParameter = true)
                    val result = profileRepository.getUserById(profileOwnerId, currentUserId)
                    call.respond(result)
                } catch (badRequestError: BadRequestException) {
                    return@get
                } catch (anyException: Throwable) {
                    call.respond<Result<Any>>(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error(message = anyException.message ?: "Unknown error")
                    )
                }
            }

            post(path = "/update") {
                var fileName = ""
                var updateUserParams: UpdateUserParams? = null
                val multiPartData = call.receiveMultipart()
                try {
                    multiPartData.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                fileName = part.saveFile(Constants.PROFILE_IMAGES_FOLDER_PATH)
                            }

                            is PartData.FormItem -> {
                                if (part.name == "profile_data") {
                                    updateUserParams = Json.decodeFromString(part.value)
                                }
                            }

                            else -> {}
                        }
                        part.dispose()
                    }
                    val imageUrl = "${Constants.BASE_URL}/${fileName}"
                    val result = profileRepository.updateUser(
                        updateUserParams = updateUserParams!!.copy(
                            imageUrl = imageUrl
                        )
                    )
                    call.respond(result)
                }catch (anyError: Throwable) {
                    call.respond<Result<Any>>(
                        status = HttpStatusCode.InternalServerError,
                        message = Result.Error(message = anyError.message ?: "Unknown error")
                    )
                }
            }
        }
    }

}