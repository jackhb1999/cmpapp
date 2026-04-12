package service

import common.KtorApi
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.FollowOrUnfollowApiResponse
import model.FollowsApiResponse
import model.FollowsParams
import util.Constants

private val logger = KotlinLogging.logger {}


internal class FollowsApiService: KtorApi(){

    suspend fun followUser(userToken:String,followsParams: FollowsParams): FollowOrUnfollowApiResponse {
        val httpResponse = client.post {
            endPoint(path = "/follows/follow")
            setBody(followsParams)
            setToken(token = userToken)
        }
        logger.info { "23 $httpResponse" }
        return FollowOrUnfollowApiResponse(code = httpResponse.status,data = httpResponse.body())
    }


    suspend fun getFollowableUser(userToken:String,userId:String): FollowsApiResponse{
        val httpResponse = client.get {
            endPoint(path = "/follows/suggestions")
            parameter(key = Constants.USER_ID_PARAMETER,value = userId)
            setToken(token = userToken)
        }
        logger.info { "34 $httpResponse" }
        return FollowsApiResponse(code = httpResponse.status,data = httpResponse.body())
    }

}