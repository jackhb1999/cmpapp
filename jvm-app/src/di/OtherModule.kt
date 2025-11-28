package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viewmodel.HomeViewModel

val otherModule= module {
//    single { SettingsWrapper().createSettings() }
    viewModel { HomeViewModel() }
}