package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viewmodel.HomeViewModel
import viewmodel.PostDetailViewModel

val otherModule= module {
//    single { SettingsWrapper().createSettings() }
    viewModel { HomeViewModel() }
    viewModel { PostDetailViewModel() }
}