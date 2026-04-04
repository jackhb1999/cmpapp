package repository

import model.LikeParams
import util.Result

interface PostLikesRepository {

    suspend fun addLike(params: LikeParams): Result<Any>

    suspend fun removeLike(params:LikeParams): Result<Boolean>
}