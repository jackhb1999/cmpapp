package di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import data.UserPreferences
import data.UserPreferencesImpl
import data.UserSettingsSerializer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ui.screenmodel.HomeScreenModel

actual val platformModule = module {
    single<UserPreferences> { UserPreferencesImpl(get()) }
    factory { HomeScreenModel(get()) }
}