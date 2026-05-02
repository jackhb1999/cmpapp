package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.CommentListItem
import components.CommentsSectionHeader
import components.LargeSpacing
import components.MediumSpacing
import components.PostListItem
import org.koin.compose.viewmodel.koinViewModel
import util.Constants
import viewmodel.PostDetailUiAction
import viewmodel.PostDetailViewModel

@Composable
fun PostDetailView(
    modifier: Modifier = Modifier,
    vm: PostDetailViewModel = koinViewModel(),
    postId: String
) {

    val listState = rememberLazyListState()

    val shouldFetchMoreComments by remember {

        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.totalItemsCount == 0) {
                return@derivedStateOf false
            } else {
                val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
            }
        }
    }

    if (vm.postUiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (vm.postUiState.post != null) {
        LazyColumn(
            modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
            state = listState,
        ) {
            item(key = "post_item") {
                PostListItem(
                    post = vm.postUiState.post!!,
                    onPostClick = { _ -> },
                    onProfileClick = { _ -> },
                    onLikeClick = { vm.onUiAction(PostDetailUiAction.LikeOrUnLikePostAction(vm.postUiState.post!!)) },
                    onCommentClick = {},
                    isDetailScreen = true
                )
            }
            item(key = "comments_header_section") {
                CommentsSectionHeader {
                    vm.onAddCommentClick()
                }
            }
            items(items = vm.commentsUiState.comments, key = { comment -> comment.commentId }) {
                HorizontalDivider()
                CommentListItem(
                    comment = it,
                    onProfileClick = { _ -> },
                    onMoreIconClick = {}
                )
            }
            if (vm.commentsUiState.isLoading) {
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    Box(
                        modifier = modifier.fillMaxWidth().padding(
                            vertical = MediumSpacing,
                            horizontal = LargeSpacing
                        ), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(LargeSpacing)) {
                Text(text = "点击", style = MaterialTheme.typography.bodyMedium)

                OutlinedButton(onClick = { vm.onUiAction(PostDetailUiAction.FetchPostAction(postId)) }) {
                    Text(text = "按钮")
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.onUiAction(PostDetailUiAction.FetchPostAction(postId))
    })

    LaunchedEffect(key1 = shouldFetchMoreComments) {
        if (shouldFetchMoreComments && !vm.commentsUiState.endReached) {
            vm.onUiAction(PostDetailUiAction.LoadMoreCommentsAction)
        }
    }
}