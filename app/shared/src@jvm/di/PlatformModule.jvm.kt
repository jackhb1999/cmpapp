package di

import androidx.datastore.core.DataStoreFactory
import data.UserPreferences
import data.UserPreferencesImpl
import data.UserSettingsSerializer
import data.createDatastore
import org.koin.dsl.module

actual val platformModule= module {
    single<UserPreferences> { UserPreferencesImpl(get()) }
    single {
        createDatastore()
    }
}