package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.PostListItem
import components.onboarding.OnBoardingSection
import org.koin.compose.viewmodel.koinViewModel
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
        LazyColumn(modifier = modifier.fillMaxSize()) {
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
                            HomeUiAction.PostLikeAction(post.postId)
                        )
                    },
                    onCommentClick = { goPostDetail(post.postId) })
            }
        }

//        PullRefreshIndicator(
//            refreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
//            state = pullRefreshState,
//            modifier = modifier.align(Alignment.TopCenter)
//        )
    }

}


