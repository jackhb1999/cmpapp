package data

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import kotlinx.coroutines.flow.first
import model.UserSettingsData
import java.io.File

internal class UserPreferencesImpl(
    private val dataStore: DataStore<UserSettingsData>,
) : UserPreferences {
    override suspend fun getUserData(): UserSettingsData {
        return dataStore.data.first()
    }

    override suspend fun setUserData(userSettingsData: UserSettingsData) {
        dataStore.updateData { userSettingsData }
    }
}

internal fun createDatastore(): DataStore<UserSettingsData> {
    return DataStoreFactory.create(
        serializer = UserSettingsSerializer,
        produceFile = {
            File("myapp.preferences_pb")
        },
        corruptionHandler = null
    )
}