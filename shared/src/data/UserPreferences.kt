package data

import model.UserSettingsData

internal const val PREFERENCES_NAME = "user_settings.preferences"

internal interface UserPreferences {
    suspend fun getUserData(): UserSettingsData
    suspend fun setUserData(userSettingsData: UserSettingsData)
}