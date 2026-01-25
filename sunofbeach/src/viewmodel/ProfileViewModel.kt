package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.Post
import fake_data.Profile
import fake_data.samplePosts
import fake_data.sampleProfiles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var userInfoUiState by mutableStateOf(UserInfoUiState())
        private set

    var profileUiState by mutableStateOf(ProfileUiState())
        private set


    fun fetchProfile(userId: Int) {
        viewModelScope.launch {
            delay(2000)
            userInfoUiState = userInfoUiState.copy(
                isLoading = false,
                profile = sampleProfiles.find { it.id == userId }
            )
            profileUiState = profileUiState.copy(
                isLoading = false,
                posts = samplePosts
            )
        }
    }
}

data class UserInfoUiState(
    val isLoading: Boolean = true,
    val profile: Profile? = null,
    val errorMessage: String? = null
)

data class ProfileUiState(
    val isLoading: Boolean = true,
    val posts: List<Post> = listOf(),
    val errorMessage: String? = null
)