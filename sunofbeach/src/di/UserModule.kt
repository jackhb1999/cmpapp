package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import repository.UserRepository
import repository.impl.UserRepositoryImpl
import service.ApiService
import usecase.LoginUseCase
import viewmodel.LoginViewModel

val userModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            get(), get()
        )
    }
    factory { ApiService() }
    factory { LoginUseCase() }
    viewModel { LoginViewModel(get()) }
}

