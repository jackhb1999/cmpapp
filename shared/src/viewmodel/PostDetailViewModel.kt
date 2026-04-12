package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fake_data.Comment
import fake_data.sampleComments
import fake_data.samplePosts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Post

class PostDetailViewModel : ViewModel() {
    var postUiState by mutableStateOf(PostUiState())
        private set
    var commentsUiState by mutableStateOf(CommmentsUiState())
        private set

    fun fetchData(postId: String) {
        viewModelScope.launch {
            postUiState = postUiState.copy(
                isLoading = true
            )
            commentsUiState = commentsUiState.copy(
                isLoading = true
            )
            delay(2000)
            postUiState = postUiState.copy(
                isLoading = false,
                post = samplePosts.find { it.postId == postId }
            )

            commentsUiState = commentsUiState.copy(
                isLoading = false,
                comments = sampleComments
            )
        }
    }

    fun onAddCommentClick() {
        TODO("Not yet implemented")
    }
}

data class PostUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val errorMessage: String? = null
)

data class CommmentsUiState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = listOf(),
    val errorMessage: String? = null
)