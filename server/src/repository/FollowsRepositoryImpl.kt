package com.hb.repository

import com.hb.dao.follows.FollowsDao
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserRow
import io.ktor.http.HttpStatusCode
import model.FollowUserData
import repository.FollowsRepository
import util.Result

class FollowsRepositoryImpl(
    private val userDao: UserDao,
    private val followsDao: FollowsDao
) : FollowsRepository {
    override suspend fun followUser(follower: String, following: String): Result<Boolean> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            Result.Error<Boolean>(code = HttpStatusCode.Forbidden.value, message = "已经关注过了", data = false)
        } else {
            val success = followsDao.followUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = true)
                Result.Success(message = "添加关注成功！", data = true)
            } else {
                Result.Error<Boolean>(
                    code = HttpStatusCode.ServiceUnavailable.value,
                    message = "添加关注失败！",
                    data = false
                )
            }
        }
    }

    override suspend fun unfollowUser(follower: String, following: String): Result<Boolean> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            val success = followsDao.unfollowUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = false)
                Result.Success(message = "取关成功！", data = true)
            } else {
                Result.Error(code = HttpStatusCode.ServiceUnavailable.value, message = "取关失败！", data = false)
            }
        } else {
            Result.Error(code = HttpStatusCode.Forbidden.value, message = "未曾关注！", data = false)
        }
    }


    override suspend fun getFollowers(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<FollowUserData>> {
        val followersIds = followsDao.getFollowers(userId, pageNumber, pageSize)
        val followersRows = userDao.getUsers(followersIds)
        val followers = followersRows.map {
            val isFollowing = followsDao.isAlreadyFollowing(follower = userId, following = it.id)
            toFollowUserData(it, isFollowing)
        }
        return Result.Success(followers)
    }

    override suspend fun getFollowing(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<FollowUserData>> {
        val followingIds = followsDao.getFollowing(userId, pageNumber, pageSize)
        val followingRows = userDao.getUsers(followingIds)
        val following = followingRows.map {
            toFollowUserData(it, true)
        }
        return Result.Success(following)
    }

    override suspend fun getFollowingSuggestions(userId: String): Result<List<FollowUserData>> {
        val hasFollowing = followsDao.getFollowing(userId, pageNumber = 0, pageSize = 1)
        return if (hasFollowing.isNotEmpty()) {
            Result.Error(
                code = HttpStatusCode.Forbidden.value,
                data = listOf()
            )
        } else {
            val popularUsersRows = userDao.getPopularUsers(10)
            val popularUsers = popularUsersRows.filter { it.id != userId }
                .map { toFollowUserData(it, false) }
            return Result.Success(popularUsers)
        }
    }

    private fun toFollowUserData(userRow: UserRow, isFollowing: Boolean): FollowUserData {
        return FollowUserData(
            id = userRow.id,
            name = userRow.name,
            bio = userRow.bio,
            imageUrl = userRow.imageUrl,
            isFollowing = isFollowing
        )
    }
}