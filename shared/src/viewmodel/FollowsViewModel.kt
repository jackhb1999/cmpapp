package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.FollowsUser
import fake_data.sampleUsers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FollowsViewModel : ViewModel() {
    var uiState by mutableStateOf(FollowsUiState())
        private set

    fun fetchFollowers(userId:Int,followsType:Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            uiState = uiState.copy(isLoading = false, followsUsers = sampleUsers)
        }

    }

}


data class FollowsUiState(
    val isLoading: Boolean = false,
    val followsUsers: List<FollowsUser> = listOf(),
    val errorMessage: String? = null
)