package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import viewmodel.EditProfileViewModel
import viewmodel.FollowsViewModel
import viewmodel.HomeViewModel
import viewmodel.MainActivityViewModel
import viewmodel.PostDetailViewModel
import viewmodel.ProfileViewModel

val otherModule = module {
//    single { SettingsWrapper().createSettings() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel {
        PostDetailViewModel(get(), get(), get(), get(), get())
    }
    viewModel { ProfileViewModel() }
    viewModel { EditProfileViewModel() }
    viewModel { FollowsViewModel(get()) }
    viewModel {
        MainActivityViewModel(
            get(),
            userSettingUseCase = get()
        )
    }
}