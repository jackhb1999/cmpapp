package ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import model.Post
import repository.PostPagingSource
import util.Constants.DEFAULT_REQUEST_PAGE_SIZE


private val logger = KotlinLogging.logger {}

class HomeViewModel(
    private val postPagingSource: PostPagingSource,
) : ViewModel() {

    val userPagingFlow: Flow<PagingData<Post>> = Pager<Int, Post>(
        config = PagingConfig(
            pageSize = DEFAULT_REQUEST_PAGE_SIZE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { postPagingSource }
    )
        .flow
        .cachedIn(viewModelScope)

}


data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null,
)

data class PostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val errorMessage: String? = null,
    val endReached: Boolean = false,
)


sealed interface HomeUiAction {
    data class PostLikeAction(val post: Post) : HomeUiAction
    data object RefreshAction : HomeUiAction

    data object LoadMorePostsAction : HomeUiAction
}