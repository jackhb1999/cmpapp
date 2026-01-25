package view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fake_data.Profile

@Composable
fun ProfileHeaderSection(
    modifier: Modifier,
    imageUrl: String,
    name: String,
    bio: String,
    followersCount: Int,
    followingCount: Int,
    isCurrentUser: Boolean = false,
    isFollowing: Boolean = false,
    onButtonClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
) {
    Column(modifier = modifier) {

    }

}