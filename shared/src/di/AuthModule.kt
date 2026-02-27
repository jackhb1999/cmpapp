package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import repository.AuthRepositoryImpl
import repository.UserRepository
import service.AuthService
import usecase.SignInUseCase
import usecase.SignUpUseCase
import viewmodel.LoginViewModel
import viewmodel.SignUpViewModel

val authModule = module {
    single<UserRepository> {
        AuthRepositoryImpl(
            get(), get()
        )
    }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

