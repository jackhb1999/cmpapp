package repository

import model.Profile
import model.UpdateUserParams

interface ProfileRepository {
    suspend fun getUserById(userId:String,currentUserId:String): Result<Profile>

    suspend fun updateUser(updateUserParams: UpdateUserParams): Result<Boolean>
}