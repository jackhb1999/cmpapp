package service

import common.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import model.NewCommentParams
import model.PostComment
import util.ActionResult
import util.Constants

internal class PostCommentsApiService : KtorApi() {

    suspend fun getPostComments(
        userToken: String,
        postId: String,
        page: Int, pageSize: Int
    ): ActionResult<List<PostComment>> {
        val httpResponse = client.get {
            endPoint(path = "post/comments/$postId")
            parameter(key = Constants.PAGE_QUERY_PARAMETER, page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, pageSize)
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }

    suspend fun removePostComments(
        userToken: String,
        postId: String,
        commentId: String,
        userId: String
    ): ActionResult<Boolean> {
        val httpResponse = client.delete {
            endPoint(path = "post/delete")
            parameter(key = "userId", userId)
            parameter(key = "postId", postId)
            parameter(key = "commentId", commentId)
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }

    suspend fun addComment(
        comment: NewCommentParams,
        userToken: String,
    ): ActionResult<PostComment> {
        val httpResponse = client.post {
            endPoint(path = "post/comments/create")
            setBody(body = comment)
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }
}