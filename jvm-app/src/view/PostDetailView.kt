package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.CommentListItem
import components.CommentsSectionHeader
import components.LargeSpacing
import components.PostListItem
import fake_data.sampleComments
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.PostDetailViewModel

@Preview
@Composable
fun PostDetailView(
    modifier: Modifier = Modifier,
    vm: PostDetailViewModel = koinViewModel(),
    postId: Int
) {
    if (vm.postUiState.isLoading && vm.commentsUiState.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (vm.postUiState.post != null) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item(key = "post_item") {
                PostListItem(
                    post = vm.postUiState.post!!,
                    onPostClick = { _ -> },
                    onProfileClick = { _ -> },
                    onLikeClick = {},
                    onCommentClick = {},
                    isDetailScreen = true
                )
            }
            item(key = "comments_header_section") {
                CommentsSectionHeader {
                    vm.onAddCommentClick()
                }
            }
            items(items = sampleComments, key = { comment -> comment.id }) {
                Divider()
                CommentListItem(
                    comment = it,
                    onProfileClick = { _ -> },
                    onMoreIconClick = {}
                )
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(LargeSpacing)) {
                Text(text = "点击", style = MaterialTheme.typography.caption)

                OutlinedButton(onClick = { vm.fetchData(postId) }) {
                    Text(text = "按钮")
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.fetchData(postId)
    })
}