package components.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.CircleImage
import components.FollowsButton
import components.MediumSpacing
import components.ShortSpacing
import model.FollowUserData

@Composable
fun OnBoardingUserItem(
    followsUser: FollowUserData,
    onUserClick: (FollowUserData) -> Unit,
    onFollowButtonClick: (Boolean, FollowUserData) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .width(130.dp)
            .clickable { onUserClick(followsUser) }
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(MediumSpacing),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleImage(
                imageUrl = followsUser.imageUrl,
                modifier = modifier.size(50.dp)
            ) {
                onUserClick(followsUser)
            }
            Spacer(modifier = modifier.height(ShortSpacing))
            Text(
                text = followsUser.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(MediumSpacing))

            FollowsButton(
                modifier = modifier.fillMaxWidth().heightIn(30.dp),
                text = if (followsUser.isFollowing) "取消关注" else "关注",
                onClick = { onFollowButtonClick(!followsUser.isFollowing, followsUser) },
                isOutline = followsUser.isFollowing
            )

        }
    }
}