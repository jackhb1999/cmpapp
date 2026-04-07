package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import repository.AuthRepositoryImpl
import repository.UserRepository
import service.AuthService
import service.AwesomeService
import service.AwesomeServiceImpl
import usecase.AwesomeUseCase
import usecase.SignInUseCase
import usecase.SignUpUseCase
import usecase.UserSettingUseCase
import viewmodel.LoginViewModel
import viewmodel.SignUpViewModel

val authModule = module {
    single<UserRepository> {
        AuthRepositoryImpl(
            get(), get(),get()
        )
    }

    factory { AwesomeServiceImpl() }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
    factory { AwesomeUseCase() }
    factory { UserSettingUseCase() }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(signInUseCase = get(), awesomeUseCase = get()) }
}

