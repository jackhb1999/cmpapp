package ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import model.Post
import org.koin.compose.viewmodel.koinViewModel
import ui.components.LargeSpacing
import ui.components.MediumSpacing
import ui.components.PostListItem
import ui.components.SmallSpacing
import ui.components.onboarding.OnBoardingSection
import ui.viewmodel.HomeUiAction
import ui.viewmodel.HomeViewModel
import util.Constants

@Composable
fun HomeView(
    vm: HomeViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {


//    val lazyPagingItems = vm.userPagingFlow.flow.collectAsLazyPagingItems()
    val lazyPagingItems = vm.userPagingFlow.collectAsLazyPagingItems()
    LazyColumn {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.postId }
        ) { index ->
            val post = lazyPagingItems[index]
            if (post != null) {
                PostListItem(
                    post = post,
                    onPostClick = {},
                    onProfileClick = {

                    },
                    onLikeClick = {},
                    onCommentClick = { })
            } else {
                CircularProgressIndicator()
            }
        }
    }


//    val pullRefreshState = rememberPullToRefreshState()
//    Box(
//        modifier = modifier.fillMaxSize().pullToRefresh(
//            state = pullRefreshState,
//            isRefreshing = vm.homeRefreshState.isRefreshing,
//            onRefresh = { vm.onUiAction(HomeUiAction.RefreshAction) }
//        )
//    ) {
//
//        val listState = rememberLazyListState()
//        val shouldFetchMore by remember {
//            derivedStateOf {
//                val layoutInfo = listState.layoutInfo
//                if (layoutInfo.totalItemsCount == 0) {
//                    false
//                } else {
//                    val visibleItemsInfo = layoutInfo.visibleItemsInfo
//                    val lastVisibleItem = visibleItemsInfo.last()
//                    (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
//                }
//            }
//        }
//
//        LazyColumn(
//            modifier = modifier.fillMaxSize(),
//            state = listState
//        ) {
//            items(items = vm.postsUiState.posts, key = { post -> post.postId }) { post ->
//                PostListItem(
//                    post = post,
//                    onPostClick = {},
//                    onProfileClick = {
//
//                    },
//                    onLikeClick = {
//                        vm.onUiAction(
//                            HomeUiAction.PostLikeAction(post)
//                        )
//                    },
//                    onCommentClick = { })
//            }
//
//            if (vm.postsUiState.isLoading && vm.postsUiState.posts.isNotEmpty()) {
//                item(key = Constants.LOADING_MORE_ITEM_KEY) {
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                            .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                }
//            } else {
//                item(key = Constants.LOADING_MORE_ITEM_KEY) {
//                    // 底部tab遮挡，需要垫起
//                    Spacer(Modifier.height(LargeSpacing + SmallSpacing))
//                }
//            }
//        }
//
//
//        LaunchedEffect(key1 = shouldFetchMore) {
//            if (shouldFetchMore && !vm.postsUiState.endReached) {
//                vm.onUiAction(HomeUiAction.LoadMorePostsAction)
//            }
//        }
//    }
}


