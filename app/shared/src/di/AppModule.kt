package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import service.UserService
import service.impl.UserServiceImpl
import ui.viewmodel.LoginViewModel
import ui.viewmodel.SignUpViewModel
import usercase.UserSettingUseCase

fun appModule() = listOf(userModule)


val userModule = module {
    single<UserService> {
        UserServiceImpl()
    }

    factory { UserSettingUseCase() }

    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }

}
