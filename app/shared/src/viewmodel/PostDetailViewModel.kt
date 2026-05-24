package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch
import model.Post
import model.PostComment
import usecase.AddPostCommentUseCase
import usecase.GetPostCommentsUserCase
import usecase.GetPostUseCase
import usecase.LikeOrUnLikePostUseCase
import usecase.RemovePostCommentUseCase
import util.Constants
import util.DefaultPagingManage
import util.PagingManage

private val logger = KotlinLogging.logger {}

class PostDetailViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getPostCommentsUserCase: GetPostCommentsUserCase,
    private val likeOrUnLikePostUseCase: LikeOrUnLikePostUseCase,
    private val addPostCommentUseCase: AddPostCommentUseCase,
    private val removePostCommentUseCase: RemovePostCommentUseCase,
) : ViewModel() {
    var postUiState by mutableStateOf(PostUiState())
        private set
    var commentsUiState by mutableStateOf(CommentsUiState())
        private set

    private lateinit var pagingManage: PagingManage<PostComment>
    private fun fetchData(postId: String) {
        viewModelScope.launch {
            val result = getPostUseCase(postId)
            when {
                result.isSuccess -> {
                    result.map {
                        postUiState = postUiState.copy(
                            isLoading = false,
                            post = it
                        )
                    }
                    fetchPostComments(postId)
                }

                result.isFailure -> {
                    postUiState = postUiState.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    private suspend fun fetchPostComments(postId: String) {
        if (commentsUiState.isLoading || commentsUiState.comments.isNotEmpty()) {
            return
        }
        if (!::pagingManage.isInitialized) {
            pagingManage = createPagingManage(postId)
        }
        pagingManage.loadItems()
    }

    private fun loadMoreComments() {
        if (commentsUiState.endReached) return
        viewModelScope.launch { pagingManage.loadItems() }
    }

    private fun createPagingManage(postId: String): PagingManage<PostComment> {
        return DefaultPagingManage(
            onRequest = { page ->
                getPostCommentsUserCase(
                    postId = postId,
                    page = page,
                    pageSize = Constants.DEFAULT_REQUEST_PAGE_SIZE
                )
            },
            onSuccess = { comments, _ ->
                commentsUiState = commentsUiState.copy(
                    comments = commentsUiState.comments + comments,
                    // 获取页面内容小于pageSize则是最后一页
                    endReached = comments.size < Constants.DEFAULT_REQUEST_PAGE_SIZE
                )
            },
            onError = { message, _ ->
                commentsUiState = commentsUiState.copy(
                    errorMessage = message
                )
            },
            onLoadStateChange = { isLoading ->
                commentsUiState = commentsUiState.copy(isLoading = isLoading)
            }
        )
    }

    private fun likeOrUnLikePost(post: Post) {
        viewModelScope.launch {
            logger.debug { "likeOrUnLikePost: ${post.isLiked}" }
            val count = if (post.isLiked) -1 else +1
            val updatePost = post.copy(
                isLiked = !post.isLiked,
                likesCount = post.likesCount.plus(count),
            )
            updatePost(updatePost)
            val result = likeOrUnLikePostUseCase(updatePost.postId, updatePost.isLiked)
            when {
                result.isSuccess -> {
                    updatePost(updatePost)
                }

                result.isFailure -> {
                    updatePost(post)
                }
            }
        }
    }

    private fun updatePost(post: Post) {
        postUiState = postUiState.copy(
            post = post,
        )
    }

    private fun addNewComment(commentText: String) {
        viewModelScope.launch {
            val post = postUiState.post ?: return@launch

            commentsUiState = commentsUiState.copy(isAddingNewComments = true)
            val result = addPostCommentUseCase(
                postId = post.postId,
                content = commentText
            )
            when {
                result.isFailure -> {
                    commentsUiState = commentsUiState.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        isAddingNewComments = false
                    )
                }

                result.isSuccess -> {
                    val newComment = result.getOrThrow()
                    val updatedComments = listOf(newComment) + commentsUiState.comments
                    commentsUiState = commentsUiState.copy(
                        comments = updatedComments,
                        isAddingNewComments = false
                    )
                    val updatedPost = post.copy(
                        commentsCount = post.commentsCount.plus(1),
                    )
                    updatePost(updatedPost)
                }
            }
        }
    }

    private fun removeComment(postComment: PostComment) {
        viewModelScope.launch {
            val post = postUiState.post ?: return@launch
            val comments = commentsUiState.comments
            commentsUiState = commentsUiState.copy(
                comments = comments.filter { it.commentId != postComment.commentId },
            )
            val result = removePostCommentUseCase(post.postId, postComment.commentId)
            when {
                result.isSuccess -> {
                    val updatePost = post.copy(
                        commentsCount = post.commentsCount.minus(1),
                    )
                    updatePost(updatePost)
                }

                result.isFailure -> {
                    commentsUiState = commentsUiState.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        comments = comments
                    )
                }
            }
        }
    }

    fun onUiAction(action: PostDetailUiAction) {
        when (action) {
            is PostDetailUiAction.FetchPostAction -> fetchData(action.postId)
            is PostDetailUiAction.LoadMoreCommentsAction -> loadMoreComments()
            is PostDetailUiAction.LikeOrUnLikePostAction -> likeOrUnLikePost(action.post)
            is PostDetailUiAction.AddPostCommentAction -> addNewComment(action.commentText)
            is PostDetailUiAction.RemovePostCommentAction -> removeComment(action.postComment)
        }
    }

    fun onAddCommentClick() {
        TODO("Not yet implemented")
    }
}

data class PostUiState(
    val isLoading: Boolean = true,
    val post: Post? = null,
    val errorMessage: String? = null
)

data class CommentsUiState(
    val isLoading: Boolean = false,
    val comments: List<PostComment> = listOf(),
    val errorMessage: String? = null,
    val endReached: Boolean = false,
    val isAddingNewComments: Boolean = false,
)

sealed interface PostDetailUiAction {
    data class FetchPostAction(val postId: String) : PostDetailUiAction
    data object LoadMoreCommentsAction : PostDetailUiAction
    data class LikeOrUnLikePostAction(val post: Post) : PostDetailUiAction
    data class AddPostCommentAction(val commentText: String) : PostDetailUiAction
    data class RemovePostCommentAction(val postComment: PostComment) : PostDetailUiAction
}