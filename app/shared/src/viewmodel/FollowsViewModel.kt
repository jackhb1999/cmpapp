package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.sampleUsers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.FollowUserData
import usecase.GetFollowsUseCase
import util.Constants
import util.DefaultPagingManage
import util.PagingManage
import kotlin.time.Duration.Companion.milliseconds


class FollowsViewModel(
    private val getFollowsUseCase: GetFollowsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(FollowsUiState())
        private set

    private lateinit var pagingManage: PagingManage<FollowUserData>

    private fun fetchFollowers(userId: String, followsType: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            if (!::pagingManage.isInitialized) {
                pagingManage = createPagingManage(userId, followsType)
                pagingManage.loadItems()
            }
        }
    }

    private fun createPagingManage(userId: String, followsType: Int): PagingManage<FollowUserData> {
        return DefaultPagingManage(
            onRequest = { page ->
                getFollowsUseCase(userId, page, Constants.DEFAULT_REQUEST_PAGE_SIZE, followsType)
            },
            onSuccess = { follows, _ ->
                uiState = uiState.copy(
                    isLoading = false,
                    followsUsers = follows
                )
            },
            onError = { message, _ ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = message,
                )
            },
            onLoadStateChange = {
                uiState = uiState.copy(
                    isLoading = it
                )
            }
        )
    }

    private fun loadMoreFollows() {
        if (uiState.endReached) return
        viewModelScope.launch {
            pagingManage.loadItems()
        }
    }

    fun onUiAction(action: FollowsUIAction) {
        when (action) {
            is FollowsUIAction.FetchFollowsAction -> {
                fetchFollowers(userId = action.userId, followsType = action.followsType)
            }

            is FollowsUIAction.LoadMoreFollowsAction -> {
                loadMoreFollows()
            }
        }
    }

}


data class FollowsUiState(
    val isLoading: Boolean = false,
    val followsUsers: List<FollowUserData> = listOf(),
    val errorMessage: String? = null,
    val endReached: Boolean = false
)


sealed interface FollowsUIAction {
    data class FetchFollowsAction(val userId: String, val followsType: Int) : FollowsUIAction
    data object LoadMoreFollowsAction : FollowsUIAction
}



























