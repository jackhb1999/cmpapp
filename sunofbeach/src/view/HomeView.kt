package view


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.PostListItem
import components.onboarding.OnBoardingSection
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = koinViewModel(),
    goPostDetail: (Int) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
        onRefresh = { vm.fetchData() }
    )
    Box(modifier = modifier.fillMaxSize().pullRefresh(state = pullRefreshState)) {
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
                    },
                    onLikeClick = {},
                    onCommentClick = { goPostDetail(post.id) })
            }
        }


        PullRefreshIndicator(
            refreshing = vm.onBoardingUiState.isLoading && vm.postsUiState.isLoading,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )
    }

}


