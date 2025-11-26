package components.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.LargeSpacing
import fake_data.FollowsUser


@Composable
fun OnBoardingSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(text = "可以关注一下",modifier = modifier)

    }
}

@Composable
fun UsersRow(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing),
        modifier = modifier,
    ) {
        items(items = users, key = { followUser -> followUser.id }) {
            OnBoardingUserItem(
                followsUser = it,
                onUserClick = onUserClick,
                onFollowButtonClick = onFollowButtonClick,
            )
        }
    }
}
