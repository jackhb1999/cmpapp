package com.hb.repository

import com.hb.dao.post.PostDao
import com.hb.dao.post_likes.PostLikesDao
import io.ktor.http.HttpStatusCode
import model.LikeParams
import repository.PostLikesRepository
import util.Result

class PostLikesRepositoryImpl(
    private val likesDao: PostLikesDao,
    private val postDao: PostDao
) : PostLikesRepository {
    override suspend fun addLike(params: LikeParams): Result<Any> {
        val likeExists = likesDao.isPostLiked(postId = params.postId, userId = params.userId)
        return if (likeExists) {
            Result.Error(message = "已经喜欢")
        } else {
            val postLiked = likesDao.addLike(postId = params.postId, userId = params.userId)
            if (postLiked) {
                postDao.updateLikesCount(postId = params.postId)
                Result.Success(message = "成功！")
            } else {
                Result.Error(code = HttpStatusCode.Conflict.value, message = "失败")
            }
        }
    }

    override suspend fun removeLike(params: LikeParams): Result<Boolean> {
        val likeExists = likesDao.isPostLiked(postId = params.postId, userId = params.userId)
        return if (likeExists) {
            val postLikeRemoved = likesDao.removeLike(postId = params.postId, userId = params.userId)
            if (postLikeRemoved) {
                postDao.updateLikesCount(postId = params.postId, decrement = true)
                Result.Success(data = true, message = "成功！")
            } else {
                Result.Error(data = false, code = HttpStatusCode.Conflict.value, message = "失败")
            }
        } else {
            Result.Error(code = HttpStatusCode.NotFound.value, data = true, message = "已经喜欢")
        }
    }
}