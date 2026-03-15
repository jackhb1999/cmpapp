package repository

import util.Result

interface FollowsRepository {
    suspend fun followUser(follower:String,following:String): Result<Any>
    suspend fun unfollowUser(follower:String,following:String): Result<Any>

}