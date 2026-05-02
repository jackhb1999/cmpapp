package com.hb.repository

import com.hb.dao.follows.FollowsDao
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserRow
import model.FollowUserData
import repository.FollowsRepository
import util.error

class FollowsRepositoryImpl(
    private val userDao: UserDao,
    private val followsDao: FollowsDao
) : FollowsRepository {
    override suspend fun followUser(follower: String, following: String): Result<Boolean> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            Result.failure(Throwable("Following $following is already followed"))
        } else {
            val success = followsDao.followUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = true)
                Result.success(true)
            } else {
                Result.failure(Throwable("Following $following is follow failed"))
            }
        }
    }

    override suspend fun unfollowUser(follower: String, following: String): Result<Boolean> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            val success = followsDao.unfollowUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = false)
                Result.success(true)
            } else {
                Result.failure(Throwable("Following $following is follow failed"))
            }
        } else {
            Result.failure(Throwable("Following $following is unfollow failed"))
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
        return Result.success(followers)
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
        return Result.success(following)
    }

    override suspend fun getFollowingSuggestions(userId: String): Result<List<FollowUserData>> {
        val hasFollowing = followsDao.getFollowing(userId, pageNumber = 0, pageSize = 1)
        return if (hasFollowing.isNotEmpty()) {
            val list: List<FollowUserData> = listOf()
            Result.success(list)
        } else {
            val popularUsersRows = userDao.getPopularUsers(10)
            val popularUsers = popularUsersRows.filter { it.id != userId }
                .map { toFollowUserData(it, false) }
            Result.success(popularUsers)
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