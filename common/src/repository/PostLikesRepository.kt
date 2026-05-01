package repository

import model.LikeParams

interface PostLikesRepository {

    suspend fun addLike(params: LikeParams): Result<Boolean>

    suspend fun removeLike(params:LikeParams): Result<Boolean>
}