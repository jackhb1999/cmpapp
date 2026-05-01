package com.hb.repository

import com.hb.dao.follows.FollowsDao
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserRow
import model.Profile
import model.UpdateUserParams
import repository.ProfileRepository


class ProfileRepositoryImpl(
    private val userDao: UserDao,
    private val followsDao: FollowsDao,
) : ProfileRepository {
    override suspend fun getUserById(userId: String, currentUserId: String): Result<Profile> {
        val userRow = userDao.findById(userId)
        return if (userRow != null) {
            val isFollowing = followsDao.isAlreadyFollowing(userId, currentUserId)
            val isOwnProfile = userId == currentUserId
            Result.success(toProfile(userRow, isFollowing, isOwnProfile))
        } else {
            throw Throwable("找不到用户")
        }
    }

    override suspend fun updateUser(updateUserParams: UpdateUserParams): Result<Boolean> {
        val userExists = userDao.findById(updateUserParams.userId) != null
        if (userExists) {
            val userUpdated = userDao.updateUser(
                userId = updateUserParams.userId,
                name = updateUserParams.name,
                bio = updateUserParams.bio,
                imageUrl = updateUserParams.imageUrl
            )
            if (userUpdated) {
                return Result.success(true)
            }
        }
        return Result.success(false)
    }

    private fun toProfile(userRow: UserRow, isFollowing: Boolean, isOwnProfile: Boolean): Profile {
        return Profile(
            id = userRow.id,
            name = userRow.name,
            bio = userRow.bio,
            imageUrl = userRow.imageUrl,
            followingCount = userRow.followingCount,
            followersCount = userRow.followersCount,
            isFollowing = isFollowing,
            isOwnProfile = isOwnProfile,
        )
    }
}