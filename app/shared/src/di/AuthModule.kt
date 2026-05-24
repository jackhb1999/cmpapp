package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module
import repository.AuthRepositoryImpl
import repository.FollowsRepository
import repository.FollowsRepositoryImpl
import repository.PostCommentsRepository
import repository.PostCommentsRepositoryImpl
import repository.PostLikesRepository
import repository.PostRepository
import repository.PostRepositoryImpl
import repository.UserRepository
import service.AuthService
import service.AwesomeServiceImpl
import service.FollowsApiService
import service.PostApiService
import service.PostCommentsApiService
import usecase.AddPostCommentUseCase
import usecase.AwesomeUseCase
import usecase.FollowOrUnfollowUseCase
import usecase.GetFollowableUsersUseCase
import usecase.GetFollowsUseCase
import usecase.GetPostCommentsUserCase
import usecase.GetPostUseCase
import usecase.GetPostsUseCase
import usecase.LikeOrUnLikePostUseCase
import usecase.RemovePostCommentUseCase
import usecase.SignInUseCase
import usecase.SignUpUseCase
import usecase.UserSettingUseCase
import viewmodel.LoginViewModel
import viewmodel.SignUpViewModel

val authModule = module {
    single<UserRepository> {
        AuthRepositoryImpl(
            get(), get(), get()
        )
    }

    // 单例
    single<FollowsRepository> {
        FollowsRepositoryImpl(
            get(), get(), get()
        )
    }

    single<PostCommentsRepository> {
        PostCommentsRepositoryImpl(
            get(), get(), get()
        )
    }

    single {
        PostRepositoryImpl(get(), get(), get())
    }.binds(arrayOf(PostRepository::class, PostLikesRepository::class))

    // 每次注入都创建新实例
    factory { AwesomeServiceImpl() }
    factory { AuthService() }
    factory { FollowsApiService() }
    factory { PostApiService() }
    factory { PostCommentsApiService() }


    factory { SignUpUseCase() }
    factory { SignInUseCase() }
    factory { GetFollowableUsersUseCase() }
    factory { FollowOrUnfollowUseCase() }
    factory { GetFollowsUseCase() }

    factory { GetPostsUseCase() }
    factory { LikeOrUnLikePostUseCase() }

    factory { GetPostCommentsUserCase() }
    factory { AddPostCommentUseCase() }
    factory { RemovePostCommentUseCase() }

    factory { GetPostUseCase() }

    factory { AwesomeUseCase() }
    factory { UserSettingUseCase() }
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(signInUseCase = get(), awesomeUseCase = get()) }
}

