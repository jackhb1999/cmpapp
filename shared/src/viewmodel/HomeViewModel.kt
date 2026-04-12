package viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.samplePosts
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch
import model.FollowUserData
import model.LikeParams
import model.Post
import usecase.FollowOrUnfollowUseCase
import usecase.GetFollowableUsersUseCase
import util.Result

private val logger = KotlinLogging.logger {}

class HomeViewModel(
    private val getFollowableUsersUseCase: GetFollowableUsersUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase
) : ViewModel() {

    var postsFeedUiState by mutableStateOf(PostsFeedUiState())
        private set

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    init {
        println("home的数据创建")
        fetchData()
    }

    fun fetchData() {
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)
        viewModelScope.launch {
            logger.info { "fetchData1" }
            println("fetchData2")
            val users = getFollowableUsersUseCase()
            handleOnBoardingResult(users)
            postsFeedUiState = postsFeedUiState.copy(
                isLoading = false,
                posts = samplePosts
            )
            homeRefreshState = homeRefreshState.copy(
                isRefreshing = true,
            )
        }
    }

    private fun handleOnBoardingResult(result: Result<List<FollowUserData>>) {
        when (result) {
            is Result.Success -> {
                result.data?.let { followsUsers ->
                    onBoardingUiState = onBoardingUiState.copy(
                        shouldShowOnBoarding = followsUsers.isNotEmpty(),
                        followableUsers = followsUsers
                    )
                }
            }

            is Result.Error -> Unit
        }
    }

    private fun followUser(followUserData: FollowUserData) {
        viewModelScope.launch {
            val result = followOrUnfollowUseCase(
                followedUserId = followUserData.id,
                shouldFollow = !followUserData.isFollowing,
            )
            logger.info { "78followUser: $result" }
            onBoardingUiState = onBoardingUiState.copy(
                followableUsers = onBoardingUiState.followableUsers.map {
                    if (it.id == followUserData.id && result is Result.Success && result.data == true) {
                        it.copy(isFollowing = !followUserData.isFollowing) // 满足条件时更新
                    } else {
                        it // 其他所有情况返回原对象
                    }
                }
            )
        }
    }

    private fun dismissOnboarding(){
        val hasFollowing = onBoardingUiState.followableUsers.any{it.isFollowing}
        if(!hasFollowing){

        }else{
            onBoardingUiState.copy(followableUsers = emptyList(),shouldShowOnBoarding = false)
            fetchData()
        }
    }

    fun onUiAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.FollowUserAction -> followUser(uiAction.user)
            is HomeUiAction.LoadMorePostsAction -> Unit
            is HomeUiAction.PostLikeAction -> Unit
            is HomeUiAction.RefreshAction -> fetchData()
            is HomeUiAction.RemoveOnboardingAction -> dismissOnboarding()
        }
    }

    fun onProfileClick(id: String) {}


}


data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null,
)

data class PostsFeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val errorMessage: String? = null
)

data class OnBoardingUiState(
    val followableUsers: List<FollowUserData> = listOf(),
    val shouldShowOnBoarding: Boolean = false
)


sealed interface HomeUiAction {
    data class FollowUserAction(val user: FollowUserData) : HomeUiAction

    data class PostLikeAction(val likePostId: String) : HomeUiAction

    data object RemoveOnboardingAction : HomeUiAction

    data object RefreshAction : HomeUiAction

    data object LoadMorePostsAction : HomeUiAction
}