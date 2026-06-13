package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import repository.PostPagingSource
import service.PostService
import service.UserService
import service.impl.PostServiceImpl
import service.impl.UserServiceImpl
import ui.viewmodel.HomeViewModel
import ui.viewmodel.LoginViewModel
import ui.viewmodel.SignUpViewModel
import usercase.UserSettingUseCase

fun getDI(): KoinApplication = startKoin {
    modules(platformModule + appModule())
}

fun appModule() = listOf(userModule, postModule,homeModule)


val userModule = module {
    single<UserService> {
        UserServiceImpl()
    }
    factory { UserSettingUseCase() }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}


val postModule = module {
    single<PostService> {
        PostServiceImpl()
    }
    factory { PostPagingSource(get()) }
}

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}


