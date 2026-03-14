package view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.*
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.ProfileViewModel


@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    vm: ProfileViewModel = koinViewModel(),
    userId: Int,
    onPostClick: (Int) -> Unit,
    onCommentClick: (Int) -> Unit,
    onButtonClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
) {
    if (vm.userInfoUiState.isLoading && vm.profileUiState.isLoading
    ) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            item(key = "header_section") {
                ProfileHeaderSection(
                    imageUrl = vm.userInfoUiState.profile?.profileUrl ?: "",
                    name = vm.userInfoUiState.profile?.name ?: "",
                    bio = vm.userInfoUiState.profile?.bio ?: "",
                    followingCount = vm.userInfoUiState.profile?.followingCount ?: 0,
                    followersCount = vm.userInfoUiState.profile?.followersCount ?: 0,
                    onButtonClick = { onButtonClick() },
                    onFollowersClick = { onFollowersClick() },
                    onFollowingClick = { onFollowingClick() },
                )
            }

            items(
                items = vm.profileUiState.posts,
                key = { post -> post.id }
            ) {
                val postId = it.id
                PostListItem(
                    post = it,
                    onPostClick = { onPostClick(postId) },
                    onProfileClick = {},
                    onLikeClick = { vm.onLikesClick(postId = postId) },
                    onCommentClick = onCommentClick
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.fetchProfile(userId)
    }
}


@Composable
fun ProfileHeaderSection(
    modifier: Modifier = Modifier,
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MediumSpacing)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = LargeSpacing)
    ) {
        CircleImage(modifier = modifier.size(90.dp), imageUrl = imageUrl, onClick = {})
        Spacer(Modifier.height(SmallSpacing))
        Text(text = name, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = bio, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(MediumSpacing))
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = modifier.weight(1f)) {
                FollowsText(count = followersCount, text = "关注人数", onClick = onFollowersClick)
                Spacer(Modifier.width(MediumSpacing))
                FollowsText(count = followingCount, text = "关注的人数", onClick = onFollowingClick)
                FollowsButton(
                    text = "修改", onClick = onButtonClick,
                    modifier = modifier.heightIn(30.dp).widthIn(100.dp), isOutline = isCurrentUser || isFollowing
                )
            }
        }
    }


}


@Composable
fun FollowsText(
    modifier: Modifier = Modifier,
    count: Int,
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            ) {
                append(text = "$count")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            ) {
                append(text = text)
            }
        },
        modifier = modifier.clickable { onClick() },
    )
}