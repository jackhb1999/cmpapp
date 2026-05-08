package view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import components.CommentListItem
import components.CommentsSectionHeader
import components.LargeSpacing
import components.MediumSpacing
import components.PostListItem
import components.ScreenLevelLoadingView
import components.SmallSpacing
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import util.loadingMoreItem
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
        ScreenLevelLoadingView()
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
                loadingMoreItem()
            }
        }
    } else {
        ScreenLevelLoadingView(
            modifier = modifier,
            onRetry = { vm.onUiAction(PostDetailUiAction.FetchPostAction(postId)) })
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

@Composable
private fun CommentsHeaderSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(all = LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "评论",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CommentInput(
    modifier: Modifier = Modifier,
    commentText: String,
    onCommentChange: (String) -> Unit,
    onSendClick: (String) -> Unit,
) {
    Column(
        modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background)
            .animateContentSize(),
    ) {
        HorizontalDivider()
        Row(
            modifier = modifier.padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            Box(
                modifier.heightIn(min = 35.dp, max = 70.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(percent = 25)
                    ).padding(
                        horizontal = MediumSpacing,
                        vertical = SmallSpacing
                    ).weight(1f),
            ) {
                BasicTextField(
                    value = commentText,
                    onValueChange = onCommentChange,
                    modifier = modifier.fillMaxWidth().align(Alignment.CenterStart),
                    textStyle = LocalTextStyle.current.copy(
                        color = LocalContentColor.current,
                    ),
                    cursorBrush = SolidColor(LocalContentColor.current)
                )

                if (commentText.isEmpty()) {
                    Text(
                        modifier = modifier.align(Alignment.CenterStart)
                            .padding(SmallSpacing), text = "评论吧",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}


@Composable
private fun SendCommentButton(modifier: Modifier = Modifier) {
    
}
