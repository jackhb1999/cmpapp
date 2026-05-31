package di

import data.UserPreferences
import data.UserPreferencesImpl
import org.koin.dsl.module
import ui.screenmodel.HomeScreenModel

actual val platformModule = module {
    single<UserPreferences> { UserPreferencesImpl(get()) }
    factory { HomeScreenModel(get()) }
}