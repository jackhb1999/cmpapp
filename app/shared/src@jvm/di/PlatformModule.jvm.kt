package di

import cafe.adriel.voyager.core.registry.screenModule
import data.UserPreferences
import data.UserPreferencesImpl
import data.createDatastore
import org.koin.dsl.module
import ui.screenmodel.HomeScreenModel

actual val platformModule= module {
    single<UserPreferences> { UserPreferencesImpl(get()) }
    factory { HomeScreenModel(get()) }
    single {
        createDatastore()
    }
}