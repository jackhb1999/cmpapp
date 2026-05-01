package service

import common.KtorApi
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.*
import io.ktor.client.request.*
import model.FollowOrUnfollowApiResponse
import model.FollowUserData
import model.FollowsApiResponse
import model.FollowsParams
import util.ActionResult
import util.Constants

private val logger = KotlinLogging.logger {}


internal class FollowsApiService: KtorApi(){

    suspend fun followUser(userToken:String,followsParams: FollowsParams): ActionResult<Boolean> {
        val httpResponse = client.post {
            endPoint(path = "/follows/follow")
            setBody(followsParams)
            setToken(token = userToken)
        }
       return httpResponse.getBody()
    }


    suspend fun getFollowableUser(userToken:String,userId:String): ActionResult<List<FollowUserData>>{
        val httpResponse = client.get {
            endPoint(path = "/follows/suggestions")
            parameter(key = Constants.USER_ID_PARAMETER,value = userId)
            setToken(token = userToken)
        }
        return httpResponse.getBody()
    }

}