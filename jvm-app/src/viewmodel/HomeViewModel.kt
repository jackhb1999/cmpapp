package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.FollowsUser
import fake_data.Post
import fake_data.samplePosts
import fake_data.sampleUsers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var postsUiState by mutableStateOf(PostsUiState())
        private set

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    init {
        println("home的数据创建")
        fetchData()
    }

    fun fetchData() {
        onBoardingUiState = onBoardingUiState.copy(isLoading = true)
        postsUiState = postsUiState.copy(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            onBoardingUiState = onBoardingUiState.copy(
                isLoading = false,
                users = sampleUsers,
                shouldShowOnBoarding = true
            )

            postsUiState = postsUiState.copy(
                isLoading = false,
                posts = samplePosts
            )
        }
    }

    fun onProfileClick(id: Int) {}
    fun onBoardingFinish() {
        TODO("Not yet implemented")
    }



}


data class PostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val errorMessage: String? = null
)

data class OnBoardingUiState(
    val isLoading: Boolean = false,
    val users: List<FollowsUser> = listOf(),
    val errorMessage: String? = null,
    val shouldShowOnBoarding: Boolean = false
)