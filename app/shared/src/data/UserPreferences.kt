package data

import model.User

internal const val PREFERENCES_NAME = "user_settings.preferences"

internal interface UserPreferences {
    suspend fun getUserData(): User
    suspend fun setUserData(user: User)
}