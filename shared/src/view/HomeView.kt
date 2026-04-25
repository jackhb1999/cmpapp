package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.LargeSpacing
import components.MediumSpacing
import components.PostListItem
import components.SmallSpacing
import components.onboarding.OnBoardingSection
import org.koin.compose.viewmodel.koinViewModel
import util.Constants
import viewmodel.HomeUiAction
import viewmodel.HomeViewModel

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = koinViewModel(),
    goPostDetail: (String) -> Unit,
    goProfileClick: (String) -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier.fillMaxSize().pullToRefresh(
            state = pullRefreshState,
            isRefreshing = vm.homeRefreshState.isRefreshing,
            onRefresh = { vm.onUiAction(HomeUiAction.RefreshAction) }
        )) {

        val listState = rememberLazyListState()
        val shouldFetchMore by remember {
            derivedStateOf {
                val layoutInfo = listState.layoutInfo
                if (layoutInfo.totalItemsCount == 0) {
                    false
                } else {
                    val visibleItemsInfo = layoutInfo.visibleItemsInfo
                    val lastVisibleItem = visibleItemsInfo.last()
                    (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
                }
            }
        }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState
        ) {
            if (vm.onBoardingUiState.shouldShowOnBoarding) {
                item(key = "followableUsers") {
                    OnBoardingSection(
                        users = vm.onBoardingUiState.followableUsers,
                        onUserClick = { vm.onProfileClick(it.id) },
                        onFollowButtonClick = { _, user ->
                            vm.onUiAction(HomeUiAction.FollowUserAction(user))
                        },
                        onBoardingFinish = { vm.onUiAction(HomeUiAction.RemoveOnboardingAction) }
                    )
                }
            }
            items(items = vm.postsFeedUiState.posts, key = { post -> post.postId }) { post ->
                PostListItem(
                    post = post,
                    onPostClick = {},
                    onProfileClick = {
                        goProfileClick(post.userId)
                    },
                    onLikeClick = {
                        vm.onUiAction(
                            HomeUiAction.PostLikeAction(post)
                        )
                    },
                    onCommentClick = { goPostDetail(post.postId) })
            }

            if (vm.postsFeedUiState.isLoading && vm.postsFeedUiState.posts.isNotEmpty()) {
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }else {
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    // 底部tab遮挡，需要垫起
                    Spacer(Modifier.height(LargeSpacing + SmallSpacing))
                }
            }
        }


//        PullRefreshIndicator(
//            refreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
//            state = pullRefreshState,
//            modifier = modifier.align(Alignment.TopCenter)
//        )

        LaunchedEffect(key1 = shouldFetchMore) {
            if (shouldFetchMore && !vm.postsFeedUiState.endReached) {
                vm.onUiAction(HomeUiAction.LoadMorePostsAction)
            }
        }
    }

}


