package com.hb.repository

import com.hb.dao.follows.FollowsDao
import com.hb.dao.user.UserDao
import io.ktor.http.HttpStatusCode
import repository.FollowsRepository
import util.Result

class FollowsRepositoryImpl(
    private val userDao: UserDao,
    private val followsDao: FollowsDao
) : FollowsRepository {
    override suspend fun followUser(follower: String, following: String): Result<Any> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            Result.Error<Any>(code = HttpStatusCode.Forbidden, message = "已经关注过了")
        } else {
            val success = followsDao.followUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = true)
                Result.Success(msg = "添加关注成功！")
            } else {
                Result.Error<Any>(code = HttpStatusCode.ServiceUnavailable, message = "添加关注失败！")
            }
        }
    }

    override suspend fun unfollowUser(follower: String, following: String): Result<Any> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            val success = followsDao.unfollowUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = false)
                Result.Success(msg = "取关成功！")
            } else {
                Result.Error<Any>(code = HttpStatusCode.ServiceUnavailable, message = "取关失败！")
            }
        } else {
            Result.Error<Any>(code = HttpStatusCode.Forbidden, message = "未曾关注！")
        }
    }
}