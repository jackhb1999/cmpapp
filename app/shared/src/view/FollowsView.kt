package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import components.FollowsListItem
import org.koin.compose.viewmodel.koinViewModel
import util.Constants
import util.loadingMoreItem
import viewmodel.FollowsUIAction
import viewmodel.FollowsViewModel


@Composable
fun FollowsView(
    modifier: Modifier = Modifier,
    vm: FollowsViewModel = koinViewModel(),
    onItemClick: (String) -> Unit,
    userId: String,
    followsType: Int,
) {
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
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            items(items = vm.uiState.followsUsers, key = { user -> user.id }) {
                FollowsListItem(name = it.name, bio = it.bio, imageUrl = it.imageUrl) {
                    onItemClick(it.id)
                }
            }
            if (vm.uiState.isLoading && vm.uiState.followsUsers.isNotEmpty()) {
                loadingMoreItem()
            }
        }
        if (vm.uiState.isLoading && vm.uiState.followsUsers.isEmpty()) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.onUiAction(FollowsUIAction.FetchFollowsAction(userId = userId, followsType = followsType))
    })

    LaunchedEffect(key1 = shouldFetchMore) {
        if (shouldFetchMore && !vm.uiState.endReached) {
            vm.onUiAction(FollowsUIAction.LoadMoreFollowsAction)
        }
    }

}