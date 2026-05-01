package service

import common.KtorApi
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import model.LikeParams
import model.Post
import util.ActionResult
import util.Constants

private val logger = KotlinLogging.logger {}

internal class PostApiService : KtorApi() {


    suspend fun getFeedPosts(
        userToken: String,
        currentUserId: String,
        page: Int,
        pageSize: Int,
    ): ActionResult<List<Post>> {
        val httpResponse = client.get {
            endPoint(path = "/posts/feed")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }

    suspend fun likePost(
        userToken: String,
        likeParams: LikeParams
    ): ActionResult<Boolean> {
        val httpResponse = client.post {
            endPoint(path = "/post/likes/add")
            setBody(
                likeParams
            )
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }

    suspend fun unlikePost(
        userToken: String,
        likeParams: LikeParams
    ): ActionResult<Boolean> {
        val httpResponse = client.delete {
            endPoint(path = "/post/likes/remove")
            setBody(
                likeParams
            )
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }
}