package view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import model.PostComment
import org.koin.compose.viewmodel.koinViewModel
import util.loadingMoreItem
import viewmodel.PostDetailUiAction
import viewmodel.PostDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailView(
    modifier: Modifier = Modifier,
    vm: PostDetailViewModel = koinViewModel(),
    postId: String,
    onProfileNavigation: (String) -> Unit,
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

    var commentText by rememberSaveable { mutableStateOf<String>("") }
    val keyboardController = LocalSoftwareKeyboardController.current

//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var selectedComment by rememberSaveable(stateSaver = postCommentSaver) {
        mutableStateOf<PostComment?>(null)
    }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            selectedComment?.let { comment ->
                CommentMoreActionsButtomSheetContent(
                    comment = comment,
                    canDeleteComment = comment.userId == vm.postUiState.post?.userId,
                    onDeleteCommentClick = { comment ->
                        scope.launch {
                            bottomSheetState.hide()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                vm.onUiAction(PostDetailUiAction.RemovePostCommentAction(comment))
                                selectedComment = null
                            }
                        }
                    },
                    onNavigateToProfile = { userId ->
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                selectedComment = null
                                onProfileNavigation(userId)
                            }
                        }
                    }
                )
            }
        }
    }

    if (vm.postUiState.isLoading) {
        ScreenLevelLoadingView()
    } else if (vm.postUiState.post != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).weight(1f),
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
                if (vm.commentsUiState.isAddingNewComments) {
                    loadingMoreItem()
                }
                items(items = vm.commentsUiState.comments, key = { comment -> comment.commentId }) {
                    HorizontalDivider()
                    CommentListItem(
                        comment = it,
                        onProfileClick = { _ -> },
                        onMoreIconClick = {
                            selectedComment = it
                            openBottomSheet = true
                            scope.launch { bottomSheetState.show()}
                        }
                    )
                }
                if (vm.commentsUiState.isLoading) {
                    loadingMoreItem()
                }
            }

            CommentInput(
                commentText = commentText,
                onCommentChange = { commentText = it },
                onSendClick = {
                    keyboardController?.hide()
                    vm.onUiAction(PostDetailUiAction.AddPostCommentAction(it))
                    commentText = ""
                })
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

            SendCommentButton(
                sendCommentEnable = commentText.isNotBlank(),
                onSendClick = { onSendClick(commentText) })
        }
    }
}

@Composable
private fun CommentMoreActionsButtomSheetContent(
    modifier: Modifier = Modifier,
    comment: PostComment,
    canDeleteComment: Boolean,
    onDeleteCommentClick: (PostComment) -> Unit,
    onNavigateToProfile: (iserId: String) -> Unit
) {
    Column {
        Text(
            text = "评论",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(all = LargeSpacing),
        )
        HorizontalDivider()
        ListItem(
            modifier = modifier.clickable(
                enabled = canDeleteComment,
                onClick = { onDeleteCommentClick(comment) }
            ),
            headlineContent = { Icon(Icons.Outlined.Delete, contentDescription = "Delete comment") },
            overlineContent = {
                Text(
                    text = "删除评论",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
        ListItem(
            modifier = modifier.clickable {
                onNavigateToProfile(comment.userId)
            },
            headlineContent = {
                CircleImage(
                    imageUrl = comment.userImageUrl,
                    modifier = modifier.size(25.dp),
                    onClick = {})
            },
            overlineContent = {
                Text(
                    text = "访问${comment.username}主页",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )

    }
}


@Composable
private fun SendCommentButton(
    modifier: Modifier = Modifier,
    sendCommentEnable: Boolean,
    onSendClick: () -> Unit,
) {
    val border = if (sendCommentEnable) {
        null
    } else {
        BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
    }
    Button(
        modifier = modifier.height(34.dp),
        enabled = sendCommentEnable,
        onClick = onSendClick,
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            disabledContainerColor = Color.Transparent,
        ),
        border = border,
        shape = RoundedCornerShape(percent = 50),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
        )
    ) {
        Text("评论", modifier = Modifier.padding(horizontal = LargeSpacing))
    }
}

//private val postCommentSaver = autoSaver<PostComment>()
private val postCommentSaver = Saver<PostComment?, Any>(
    save = { postComment ->
        if (postComment != null) {
            mapOf(
                "commentId" to postComment.commentId,
                "userId" to postComment.userId,
                "content" to postComment.content,
                "postId" to postComment.postId,
                "username" to postComment.username,
                "userImageUrl" to postComment.userImageUrl,
                "createdAt" to postComment.createdAt
            )
        }


    },
    restore = { savedValue ->
        val map = savedValue as Map<*, *>
        PostComment(
            commentId = map["commentId"] as String,
            userId = map["userId"] as String,
            content = map["content"] as String,
            postId = map["postId"] as String,
            username = map["username"] as String,
            userImageUrl = map["userImageUrl"] as String?,
            createdAt = map["createdAt"] as LocalDateTime,
        )
    }
)
