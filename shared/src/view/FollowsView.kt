package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import components.FollowsListItem
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.FollowsViewModel


@Composable
fun FollowsView(
    modifier: Modifier = Modifier,
    vm: FollowsViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit,
    userId: Int,
    followsType: Int
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = vm.uiState.followsUsers, key = { user -> user.id }) {
                FollowsListItem(name = it.name, bio = it.bio, imageUrl = it.profileUrl) {
                    onItemClick(it.id)
                }
            }
        }
        if (vm.uiState.isLoading && vm.uiState.followsUsers.isEmpty()) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.fetchFollowers(userId = userId, followsType = followsType)
    })

}