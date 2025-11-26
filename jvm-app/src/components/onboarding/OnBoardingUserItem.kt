package components.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
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
import fake_data.FollowsUser
import javax.swing.text.StyleContext

@Composable
fun OnBoardingUserItem(
    followsUser: FollowsUser,
    onUserClick: (FollowsUser) -> Unit,
    isFollowing: Boolean = false,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .width(130.dp)
            .clickable { onUserClick(followsUser) },
        elevation = 0.dp
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(MediumSpacing),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleImage(
                imageUrl = followsUser.profileUrl,
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
                text = 0,
                onClick = { onFollowButtonClick(!isFollowing, followsUser) },
                isOutline = isFollowing
            )

        }
    }
}