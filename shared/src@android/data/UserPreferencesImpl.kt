package data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import model.UserSettingsData

internal class UserPreferencesImpl(
    private val dataStore: DataStore<UserSettingsData>
): UserPreferences {
    override suspend fun getUserData(): UserSettingsData {
     return  dataStore.data.first()
    }

    override suspend fun setUserData(userSettingsData: UserSettingsData) {
        dataStore.updateData { userSettingsData }
    }
}