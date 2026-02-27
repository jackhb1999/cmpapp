package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.PostListItem
import components.onboarding.OnBoardingSection
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.HomeViewModel

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = koinViewModel(),
    goPostDetail: (Int) -> Unit,
    goProfileClick: (Int) -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()
    Box(modifier = modifier.fillMaxSize().pullToRefresh(
        state = pullRefreshState,
        isRefreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
        onRefresh = { vm.fetchData() }
    )) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            if (vm.onBoardingUiState.shouldShowOnBoarding) {
                item(key = "onboardingsection") {
                    OnBoardingSection(
                        users = vm.onBoardingUiState.users,
                        onUserClick = { vm.onProfileClick(it.id) },
                        onFollowButtonClick = { _, _ -> }
                    ) {
                        vm.onBoardingFinish()
                    }
                }
            }
            items(items = vm.postsUiState.posts, key = { post -> post.id }) { post ->
                PostListItem(
                    post = post,
                    onPostClick = {},
                    onProfileClick = {
                        goProfileClick(post.authorId)
                    },
                    onLikeClick = {},
                    onCommentClick = { goPostDetail(post.id) })
            }
        }

//        PullRefreshIndicator(
//            refreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
//            state = pullRefreshState,
//            modifier = modifier.align(Alignment.TopCenter)
//        )
    }

}


