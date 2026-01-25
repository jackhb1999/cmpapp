package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viewmodel.EditProfileViewModel
import viewmodel.HomeViewModel
import viewmodel.PostDetailViewModel
import viewmodel.ProfileViewModel

val otherModule= module {
//    single { SettingsWrapper().createSettings() }
    viewModel { HomeViewModel() }
    viewModel { PostDetailViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { EditProfileViewModel() }
}