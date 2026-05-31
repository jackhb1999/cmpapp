package data

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.first
import model.User
import java.io.File

internal class UserPreferencesImpl(
    private val dataStore: DataStore<User>
) : UserPreferences {
    override suspend fun getUserData(): User {
        return dataStore.data.first()
    }

    override suspend fun setUserData(user: User) {
        dataStore.updateData { user }
    }
}


internal fun createDatastore(): DataStore<User> {
    return DataStoreFactory.create(
        serializer = UserSettingsSerializer,
        produceFile = {
            File(PREFERENCES_NAME)
        },
        corruptionHandler = null
    )
}